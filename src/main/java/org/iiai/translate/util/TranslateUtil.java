package org.iiai.translate.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.iiai.translate.constant.ErrorCode;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.exception.TranslatorException;
import org.iiai.translate.http.HttpConnectionPoolUtil;
import org.iiai.translate.model.*;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.processor.mergeprocessor.FormatProcessor;
import org.iiai.translate.processor.mergeprocessor.HtmlProcessor;
import org.iiai.translate.processor.mergeprocessor.MergeProcessor;
import org.iiai.translate.processor.mergeprocessor.PuncProcessor;
import org.iiai.translate.processor.postprocessor.PostProcessor;
import org.iiai.translate.processor.postprocessor.ReplaceProcessor;
import org.iiai.translate.processor.postprocessor.SingleSentProcessor;
import org.iiai.translate.processor.preprocessor.AndSplitProcessor;
import org.iiai.translate.processor.preprocessor.CommaSplitProcessor;
import org.iiai.translate.processor.preprocessor.PreProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class TranslateUtil {
    private static final int BATCH_SIZE = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateUtil.class);

    private static final ThreadPoolExecutor EXECUTOR = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static final CloseableHttpClient HTTP_CLIENT = HttpConnectionPoolUtil.getHttpClient();

    private static final List<PostProcessor> POST_PROCESSORS = Arrays.asList(new SingleSentProcessor(),
            new ReplaceProcessor());

    private static final List<PreProcessor> PRE_PROCESSORS = Arrays.asList(new CommaSplitProcessor(),
            new AndSplitProcessor());

    private static final List<MergeProcessor> MERGE_PROCESSORS = Arrays.asList(new HtmlProcessor(),
            new PuncProcessor(),
            new FormatProcessor());

    private TranslateUtil() {

    }

    public static String getTranslation(Document document, String modelId, String url, String token) {
        preProcess(document, modelId);

        List<Sentence> transSentences = document.getSentenceByType(SingleSentType.class);
        List<BatchSentence> batchSentenceList = getModelBatchList(modelId, transSentences);
        List<String> transList = asyncGetTranslation(batchSentenceList, modelId, url, token);
        document.setTransList(transList);

        postProcess(document, modelId);

        String result = document.getTranslation();
        result = mergeProcess(result, modelId);
        return result;
    }

    private static void preProcess(Document document, String modelId) {
        for (PreProcessor processor : PRE_PROCESSORS) {
            processor.process(document, modelId);
        }
    }

    private static void postProcess(Document document, String modelId) {
        for (PostProcessor processor : POST_PROCESSORS) {
            processor.process(document, modelId);
        }
    }

    private static String mergeProcess(String input, String modelId) {
        for (MergeProcessor processor : MERGE_PROCESSORS) {
            input = processor.process(input, modelId);
        }
        return input;
    }

    private static List<String> asyncGetTranslation(List<BatchSentence> batchSentenceList, String modelId, String url, String token) {

        List<List<RequestData>> batchRequest = new ArrayList<>();
        batchSentenceList.forEach(batchSentence -> batchRequest.addAll(batchSentence.getRequestBatch()));

        int batchSize = batchRequest.size();
        CountDownLatch countDownLatch = new CountDownLatch(batchSize);
        List<List<ResponseData>> batchResult = getInitList(batchSize);
        LOGGER.info("Translate batch size: {}", batchSize);
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < batchSize; i++) {
                List<RequestData> requestData = batchRequest.get(i);
                final int index = i;
                EXECUTOR.submit(() -> {
                    try {
                        long requestStart = System.currentTimeMillis();
                        int len = requestData.stream().mapToInt(request -> request.getSrc().length()).sum();
                        sendRequest(requestData, url, token, index, batchResult);
                        LOGGER.debug("Request cost: {}ms, content length: {}", System.currentTimeMillis() - requestStart, len);
                    } catch (TranslatorException e) {
                        LOGGER.warn("Retry sendRequest", e);
                        sendRequest(requestData, url, token, index, batchResult);
                    } catch (Exception e) {
                        LOGGER.error("Exception during send request", e);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted during send request", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR);
        }

        LOGGER.info("Translator cost {}ms to finish.", System.currentTimeMillis() - start);
        isValidResult(batchResult);
        return getTransList(modelId, batchResult);
    }

    private static void isValidResult(List<List<ResponseData>> batchResultList) {
        for (List<ResponseData> batchResult : batchResultList) {
            if (null == batchResult) {
                LOGGER.error("Result is null");
                throw new TranslatorException(ErrorCode.INTERNAL_ERROR);
            }
        }
    }

    private static List<String> getTransList(String modelId, List<List<ResponseData>> batchResult) {
        if (!TranslateConst.AR2EN_ID.equals(modelId)) {
            return batchResult.stream()
                    .flatMap(batchData -> batchData.stream())
                    .map(responseData -> responseData.getTgt())
                    .collect(Collectors.toList());
        }
        // Seeing as ar2en using 2 models, here need to merge the result
        List<String> transList = new ArrayList<>();

        List<List<ResponseData>> casedResponseList = batchResult.subList(0, batchResult.size() / 2);
        List<List<ResponseData>> uncasedResponseList = batchResult.subList(batchResult.size() / 2, batchResult.size());
        for (int i = 0; i < casedResponseList.size(); i++) {
            List<ResponseData> casedBatch = casedResponseList.get(i);
            List<ResponseData> uncasedBatch = uncasedResponseList.get(i);
            for (int j = 0; j < casedBatch.size(); j++) {
                String casedResp = casedBatch.get(j).getTgt();
                String uncasedResp = uncasedBatch.get(j).getTgt();
                transList.add(getAr2EnTrans(casedResp, uncasedResp));
            }
        }
        return transList;
    }

    private static String getAr2EnTrans(String casedResp, String uncasedResp) {
        List<String> casedTokens = Arrays.asList(casedResp.split(" "));
        List<String> uncasedTokens = Arrays.asList(uncasedResp.split(" "));

        int uncasedTokenSize = uncasedTokens.size();
        int lastMatch = -1;
        for (int c = 1; c < casedTokens.size(); c++) {
            String casedToken = casedTokens.get(c);
            if (StringUtils.isAllLowerCase(casedToken)) {
                continue;
            }
            int j = lastMatch + 1;
            while (j < uncasedTokenSize) {
                String uncasedToken = uncasedTokens.get(j);
                if (StringUtils.isAllLowerCase(uncasedToken) && casedToken.toLowerCase().equals(uncasedToken)) {
                    LOGGER.info("Replace cased token: {}", casedToken);
                    uncasedTokens.set(j, casedToken);
                    lastMatch = j;
                    break;
                }
                j++;
            }
        }
        return String.join(" ", uncasedTokens);
    }

    private static void sendRequest(List<RequestData> batchRequest, String url, String token, int idx, List<List<ResponseData>> batchResult) {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(JSON.toJSONString(batchRequest), StandardCharsets.UTF_8));
        request.setHeader(TranslateConst.TOKEN_HEADER, token);

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            String result = EntityUtils.toString(response.getEntity());
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                LOGGER.error("Get result error: {}", response.getStatusLine().getStatusCode());
                LOGGER.error("Response: {}", result);
                throw new TranslatorException(ErrorCode.API_ERROR);
            }
            List<ResponseData> responseData = JSON.parseArray(result, ResponseData.class);
            batchResult.set(idx, responseData);
        } catch (IOException e) {
            LOGGER.error("IOException during send request to server. ", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR);
        }
    }

    private static List<List<ResponseData>> getInitList(int batchSize) {
        List<List<ResponseData>> result = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            result.add(null);
        }
        return Collections.synchronizedList(result);
    }

    private static List<BatchSentence> getModelBatchList(String modelId, List<Sentence> sentenceList) {
        List<BatchSentence> batchSentenceList = new ArrayList<>();
        // If ar2en, use 2 models ar2en_cased, ar2en_uncased
        List<List<Sentence>> batchList = getBatchList(sentenceList);
        if (TranslateConst.AR2EN_ID.equals(modelId)) {
            batchSentenceList.add(new BatchSentence(TranslateConst.AR2EN_CASED, batchList));
            batchSentenceList.add(new BatchSentence(TranslateConst.AR2EN_UNCASED, batchList));
        } else {
            batchSentenceList.add(new BatchSentence(modelId, batchList));
        }
        return batchSentenceList;
    }

    private static List<List<Sentence>> getBatchList(List<Sentence> sentenceList) {
        List<List<Sentence>> batchList = new ArrayList<>();
        int listIndex = 0;
        while (listIndex + BATCH_SIZE < sentenceList.size()) {
            List<Sentence> batch = sentenceList.subList(listIndex, listIndex + BATCH_SIZE);
            listIndex += BATCH_SIZE;
            batchList.add(batch);
        }
        List<Sentence> lastBatch = sentenceList.subList(listIndex, sentenceList.size());
        batchList.add(lastBatch);
        return batchList;
    }
}
