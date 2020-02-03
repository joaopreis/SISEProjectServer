package com.insure.server;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {

    public int getUuid() {
        return uuid.intValue();
    }

    //Claim id
    private AtomicInteger uuid;
    private ConcurrentHashMap<Integer,Claim> dataStore;
    private InsuredDataStore insured=new InsuredDataStore();
    private EmployeeDataStore employee=new EmployeeDataStore();

    public ClaimDataStore(){
        uuid=new AtomicInteger(1);
        dataStore=new ConcurrentHashMap<Integer, Claim>();

    }

    public synchronized int createClaim(String description, int userId) throws Exception {
        Claim claim=new Claim(uuid.getAndIncrement(),description,userId);
        dataStore.put(claim.getUuid(),claim);
        notifyAll();
        return claim.getUuid();
    }

    public Claim getClaim(int i){
        return dataStore.get(i);
    }

    public String claimToString(int i){
        return dataStore.get(i).toString();
    }

    public synchronized void addDocToClaim(int i,String fileName, String content, int userId) throws Exception {
        while (!dataStore.containsKey(i)){
            try{wait();}
            catch (InterruptedException e){

            }
        }
        Claim claim = dataStore.get(i);
        claim.addDocument(fileName,content,userId);

    }

    public String getDocumentsByClaim(int i){
        Claim claim=dataStore.get(i);
        String docs=claim.returnDocuments();
        return docs;
    }

    public void updateClaim(int i,Claim claim){
        dataStore.replace(i,claim);
    }


    public int size(){
        return dataStore.size();
    }
}
