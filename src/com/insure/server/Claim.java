package com.insure.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {

    private final int uuid;
    private AtomicInteger did;
    private String description;
    private HashMap<Integer,Document> documents;


    public Claim(int id, String description){
        this.uuid=id;
        this.description=description;
        did=new AtomicInteger(1);
        documents=new HashMap<Integer,Document>();
    }

    public void addDocument(String fileName, String content){
        Document document=new Document(did.getAndIncrement(),fileName,content);
        documents.put(document.getDid(),document);
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
                '}';
    }


}
