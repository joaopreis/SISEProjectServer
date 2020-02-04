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
        if (claimValidation (userId)) {
            Claim claim = new Claim(uuid.getAndIncrement(), description, userId);
            dataStore.put(claim.getUuid(), claim);
            return claim.getUuid();
        }else{
            throw new Exception("The user can't create a claim.");
        }
    }

    public boolean claimValidation(int userId){
        return InsuredDataStore.INSURED.contains(userId);

    }

    public Claim getClaim(int i){
        return dataStore.get(i);
    }

    public String claimToString(int i){
        return dataStore.get(i).toString();
    }

    public synchronized void addDocToClaim(int i,String fileName, String content, int userId) throws Exception {
        if (!claimExistance(i)){
            throw new Exception("Claim does not exist");
        }else if (docValidation(userId,i)){
            Claim claim = dataStore.get(i);
            claim.addDocument(fileName, content, userId);
        }else{
            throw  new Exception("Can't create the document");
        }

    }
    public boolean docValidation(int userId, int claimId){
        return (EmployeeDataStore.EMPLOYEES.contains(userId) || userId==dataStore.get(claimId).getUserId());
    }

    public boolean claimExistance(int i){
        return dataStore.containsKey(i);
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
