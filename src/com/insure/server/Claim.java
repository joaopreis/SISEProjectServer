package com.insure.server;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {

    private final int uuid;

    public int getDid() {
        return did.intValue();
    }

    public AtomicInteger did;
    private String description;
    private HashMap<Integer,Document> documents;

    public int getUserId() {
        return userId;
    }

    private int userId;

    public void increment(){
        did.incrementAndGet();
    }


    public Claim(int id, String description, int userId) throws Exception {
            this.uuid = id;
            this.description = description;
            this.userId = userId;
            did = new AtomicInteger(1);
            documents = new HashMap<Integer, Document>();
    }


    public void addDocument(String docName, String content, int userId, String fileName) throws Exception {
            Document document = new Document(this,docName, content, userId, fileName);
            documents.put(document.getDid(), document);
    }

    public int size(){
        return documents.size();
    }

    public Document getDocument(int i){
        return documents.get(i);
    }

    public int getUuid(){
        return uuid;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "uuid=" + uuid +
                ", description='" + description + '\'' +
                ", documents=" + documents +
                ", userId=" + userId +
                '}';
    }


    public String returnDocuments(){
        String doc="";
        for (int i=1;i<=this.size();i++){
            doc=doc+ documents.get(i).toString()+"\n";
        }
        return doc;
    }
}
