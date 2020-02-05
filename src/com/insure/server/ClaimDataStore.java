package com.insure.server;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {

    private AtomicInteger uuid;
    private ConcurrentHashMap<Integer,Claim> dataStore;
    private InsuredDataStore insured=new InsuredDataStore();
    private EmployeeDataStore employee=new EmployeeDataStore();

    public ClaimDataStore(){
        uuid=new AtomicInteger(1);
        dataStore=new ConcurrentHashMap<Integer, Claim>();
    }

    public int getUuid() {
        return uuid.intValue();
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

    public synchronized void addDocToClaim(int i,String docName, String content, int userId,String signature) throws Exception {
        if (!claimExistence(i)){
            throw new Exception("Claim does not exist");
        }else if (userValidation(userId,i)){
            if(signatureValidation(content,signature,userId)) {
                Claim claim = dataStore.get(i);
                claim.addDocument(docName, content, userId, signature);
            }else{
                throw new Exception("Invalid signature: Document has been tampered");
            }
        }else{
            throw  new Exception("Invalid UserId:Can't create the document");
        }
    }

    public boolean claimExistence(int i){
        return dataStore.containsKey(i);
    }

    public boolean signatureValidation(String content,String signature, int userId) throws Exception {
        String pathpublickey="keys\\server\\user"+userId+"PublicKey";
        Signature desencriptar=new Signature(signature);
        Signature encriptar=new Signature(content);

        String desencrypt=desencriptar.desencriptarMessage(pathpublickey,signature);
        String hashing=encriptar.makeHash(content);
        return hashing.equals(desencrypt);
    }

    public boolean userValidation(int userId, int claimId){
        return (EmployeeDataStore.EMPLOYEES.contains(userId) || userId==dataStore.get(claimId).getUserId());
    }

    public String getDocumentsByClaim(int i){
        Claim claim=dataStore.get(i);
        String docs=claim.returnDocuments();
        return docs;
    }


    public int size(){
        return dataStore.size();
    }

    public int getClaimSize(int cid){
        Claim claim=getClaim(cid);
        return claim.size();
    }

    public Document getDocumentbyId(int cid,int did){
        Claim claim=getClaim(cid);
        return claim.getDocument(did);
    }

    public int getDocUserId(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getUserId();
    }

    public String getDocSignature(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getSignature();
    }

    public String getDocContent(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getContent();
    }



}
