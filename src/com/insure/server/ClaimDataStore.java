package com.insure.server;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {
    //Claim id
    private AtomicInteger uuid;
    private ConcurrentHashMap<Integer,Claim> dataStore;

    public ClaimDataStore(){
        uuid=new AtomicInteger(1);
        dataStore=new ConcurrentHashMap<Integer, Claim>();

    }

    public synchronized void createClaim(String description){
        Claim claim=new Claim(uuid.getAndIncrement(),description);
        dataStore.put(claim.getUuid(),claim);
        notifyAll();
    }

    public Claim getClaim(int i){
        return dataStore.get(i);
    }

    public synchronized void addDocToClaim(int i,String fileName, String content) {
        while (!dataStore.containsKey(i)){
            try{wait();}
            catch (InterruptedException e){

            }
        }
        Claim claim = dataStore.get(i);
        claim.addDocument(fileName,content);

    }

    public void removeClaim(int i){
        dataStore.remove(i);
    }

    public void updateClaim(int i,Claim claim){
        dataStore.replace(i,claim);
    }

    public boolean containsKey(int i){
        return dataStore.containsKey(i);
    }

    private void increment(){
        uuid.incrementAndGet();
    }

    public int size(){
        return dataStore.size();
    }
}
