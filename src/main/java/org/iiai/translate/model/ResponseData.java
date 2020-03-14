package org.iiai.translate.model;

public class ResponseData {
    private String id;

    private String src;

    private String tgt;

    public ResponseData() {
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

    public String getTgt() {
        return tgt;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }
}
