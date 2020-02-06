package com.insure.server;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {

    //Claim unique identifier
    private final int uuid;

    public int getDid() {
        return did.intValue();
    }

    //Document identifier
    public AtomicInteger did;

    //Claim description
    private String description;

    //Data structure to store the documents of a claim
    private HashMap<Integer,Document> documents;

    public int getUserId() {
        return userId;
    }

    private int userId;

    //Method that increments de document identifier
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

    //Add a document to the HashMap documents
    public void addDocument(String docName, String content, int userId,String signature) throws Exception {
            Document document = new Document(this, docName, content, userId,signature);
            documents.put(document.getDid(), document);
    }

    //Returns the size of the HashMap documents
    public int size(){
        return documents.size();
    }

    //Returns a specific document
    public Document getDocument(int i){
        return documents.get(i);
    }

    public boolean containsDoc(int did){
        return documents.containsKey(did);
    }

    //Returns the claim identifier
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

    //Returns a string with the documents of a claim
    public String returnDocuments(){
        String doc="";
        for (int i=1;i<=this.size();i++){
            doc=doc+ documents.get(i).toString2()+"\n";
        }
        return doc;
    }
}
