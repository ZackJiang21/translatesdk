package org.iiai.translate.model;

public class RequestData {
    private String id;

    private String src;

    public RequestData() {
    }

    public RequestData(String id, String src) {
        this.id = id;
        this.src = src;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
