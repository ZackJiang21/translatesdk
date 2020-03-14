package org.iiai.translate.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.iiai.translate.constant.ErrorCode;
import org.iiai.translate.constant.SentenceType;
import org.iiai.translate.exception.TranslatorException;
import org.iiai.translate.model.Document;
import org.iiai.translate.model.RequestData;
import org.iiai.translate.model.ResponseData;
import org.iiai.translate.model.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class TranslateUtil {
    private static final int BATCH_SIZE = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateUtil.class);

    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String getTranslation(Document document, String modelId, String url) {
        List<Sentence> transSentences = document.getSentenceByType(SentenceType.SENTENCE);
        List<List<Sentence>> batchList = getBatchList(transSentences);
        List<String> transList = asyncGetTranslation(batchList, modelId, url);
        return document.getTranslation(transList);
    }

    private static List<String> asyncGetTranslation(List<List<Sentence>> batchList, String modelId, String url) {
        int batchSize = batchList.size();

        CountDownLatch countDownLatch = new CountDownLatch(batchSize);
        List<List<ResponseData>> batchResult = getInitList(batchSize);
        LOGGER.info("Translate batch size: {}", batchSize);

        try {
            for (int i = 0; i < batchSize; i++) {
                List<Sentence> sentenceList = batchList.get(i);
                final int index = i;
                executor.submit(() -> {
                    sendRequest(sentenceList, modelId, url, index, batchResult);
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted during send request", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR);
        }

        List<String> result = batchResult.stream()
                .flatMap(batchData -> batchData.stream())
                .map(responseData -> responseData.getTgt())
                .collect(Collectors.toList());

        return result;
    }

    private static void sendRequest(List<Sentence> sentenceList, String modelId, String url, int idx, List<List<ResponseData>> batchResult) {
        HttpPost request = new HttpPost(url);


        List<RequestData> payLoad = new ArrayList<>();
        sentenceList.forEach(sentence -> payLoad.add(new RequestData(modelId, sentence.getSentContent())));
        request.setEntity(new StringEntity(JSON.toJSONString(payLoad), StandardCharsets.UTF_8));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            LOGGER.info("Batch {} get result", idx);
            String result = EntityUtils.toString(response.getEntity());
            List<ResponseData> responseData = JSON.parseArray(result, ResponseData.class);
            batchResult.set(idx, responseData);
        } catch (IOException e) {
            LOGGER.error("IOException during send request to server. ", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR);
        }
        catch (Exception e) {
            LOGGER.error("Exception", e);
        }

    }

    private static List<List<ResponseData>> getInitList(int batchSize) {
        List<List<ResponseData>> result = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            result.add(null);
        }
        return Collections.synchronizedList(result);
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
