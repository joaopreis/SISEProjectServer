package com.insure.server;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {

    private final int uuid;
    private AtomicInteger did;
    private String description;
    private HashMap<Integer,Document> documents;
    private int userId;


    public Claim(int id, String description, int userId) throws Exception {
        if (InsuredDataStore.INSURED.contains(userId)) {
            this.uuid = id;
            this.description = description;
            this.userId = userId;
            did = new AtomicInteger(1);
            documents = new HashMap<Integer, Document>();
        }else{
            throw new Exception("User "+userId+" is not an insured person");
        }
    }

    public void addDocument(String fileName, String content, int userId) throws Exception {
        if (EmployeeDataStrore.EMPLOYEES.contains(userId) || userId==this.userId) {
            Document document = new Document(did.getAndIncrement(), fileName, content, userId);
            documents.put(document.getDid(), document);
        }else{
            throw new Exception("User "+userId+" does not have access to this claim");
        }
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
                ", did=" + did +
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
