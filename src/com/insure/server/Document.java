package com.insure.server;

import java.sql.Timestamp;

public class Document {

    private final int did;
    private String content;
    private String fileName;
    private Timestamp timestamp;
    private int userId;
    private String signature;

    public void setContent(String content) {
        this.content = content;
    }

    public int getDid() {
        return did;
    }

    public String getSignature() {
        return signature;
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

    public int getUserId() {
        return userId;
    }

    public Document(Claim claim,String docName, String content, int userId,String signature) throws Exception {
        this.did= claim.getDid();
        this.signature=signature;
        this.fileName=docName;
        this.content=content;
        this.timestamp=new Timestamp(System.currentTimeMillis());
        this.userId=userId;
        claim.increment();

    }

    @Override
    public String toString() {
        return "Document{" +
                "did=" + did +
                ", content='" + content + '\'' +
                ", fileName='" + fileName + '\'' +
                ", timestamp=" + timestamp +
                ", userId=" + userId +
                '}';
    }

    public String toString2(){
        return "Document{" +
                "did=" + did +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
