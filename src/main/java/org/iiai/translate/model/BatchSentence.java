package org.iiai.translate.model;

import java.util.List;
import java.util.stream.Collectors;

public class BatchSentence {
    private String modelId;

    private List<List<Sentence>> batchList;

    public BatchSentence() {
    }

    public BatchSentence(String modelId, List<List<Sentence>> batchList) {
        this.modelId = modelId;
        this.batchList = batchList;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<List<Sentence>> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<List<Sentence>> batchList) {
        this.batchList = batchList;
    }

    public List<List<RequestData>> getRequestBatch() {
        return batchList.stream()
                .map(sentenceList -> sentenceList.stream()
                        .map(sentence -> new RequestData(modelId, sentence.getSentContent()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

    }
}
