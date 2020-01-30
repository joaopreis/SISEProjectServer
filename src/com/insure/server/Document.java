package com.insure.server;

import java.sql.Timestamp;

public class Document {

    private final int did;
    private String content;

    public int getDid() {
        return did;
    }

    public String getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private String fileName;
    private Timestamp timestamp;


    public Document(int i, String fileName, String content){
        this.did=i;
        this.fileName=fileName;
        this.content=content;
        this.timestamp=new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Document{" +
                "did=" + did +
                ", content='" + content + '\'' +
                ", fileName='" + fileName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
