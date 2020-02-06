package com.insure.server;

import java.sql.Timestamp;

public class Document {

    //Document identifier
    private final int did;

    //Document content
    private String content;

    //Document filename
    private String fileName;

    //Document timestamp
    private Timestamp timestamp;

    //UserId of the user that submits the document
    private int userId;

    //Digital signature
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
        //Calls the method getDid to get the value of the Atomic Integer of the document identifier on the claim
        this.did= claim.getDid();
        this.signature=signature;
        this.fileName=docName;
        this.content=content;
        this.timestamp=new Timestamp(System.currentTimeMillis());
        this.userId=userId;

        //Calls the method increment to increment the Atomic integer document identifier on the claim
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
