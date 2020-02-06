package com.insure.server;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {

    public int getUuid() {
        return uuid.intValue();
    }

    //Claim identifier
    private AtomicInteger uuid;

    //Data Structure to store the claims
    private ConcurrentHashMap<Integer,Claim> dataStore;

    //Class used to store insured clients
    private InsuredDataStore insured=new InsuredDataStore();

    //Class used to store insure employees
    private EmployeeDataStore employee=new EmployeeDataStore();

    public ClaimDataStore(){
        uuid=new AtomicInteger(1);
        dataStore=new ConcurrentHashMap<Integer, Claim>();
    }

    public int createClaim(String description, int userId) throws Exception {
        if (claimValidation (userId)) {
            Claim claim = new Claim(uuid.getAndIncrement(), description, userId);
            dataStore.put(claim.getUuid(), claim);
            return claim.getUuid();
        }else{
            throw new Exception("This user can not create a claim.");
        }
    }

    //Validates if the user creating the claim is an insured client
    public boolean claimValidation(int userId){
        return InsuredDataStore.INSURED.contains(userId);

    }

    public Claim getClaim(int i){
        return dataStore.get(i);
    }

    public String claimToString(int i){
        return dataStore.get(i).toString();
    }

    //Add a document to a claim
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

    //Method to test the tampering of the document
    public synchronized void addTamperedDocToClaim(int i,String docName, String content, int userId,String signature) throws Exception {
        if (!claimExistence(i)){
            throw new Exception("Claim does not exist");
        }else if (userValidation(userId,i)){
            content="Your document has been tampered";
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

    //Validates the claim existence
    public boolean claimExistence(int i){
        return dataStore.containsKey(i);
    }

    //Validates the document digital signature
    public boolean signatureValidation(String content,String signature, int userId) throws Exception {
        String pathpublickey="keys\\server\\user"+userId+"PublicKey";
        Signature desencriptar=new Signature(signature);
        Signature encriptar=new Signature(content);

        String desencrypt=desencriptar.desencriptarMessage(pathpublickey,signature);
        String hashing=encriptar.makeHash(content);
        return hashing.equals(desencrypt);
    }

    //Validates if the user adding a document is a employee or the claim creator
    public boolean userValidation(int userId, int claimId){
        return (EmployeeDataStore.EMPLOYEES.contains(userId) || userId==dataStore.get(claimId).getUserId());
    }

    public String getDocumentsByClaim(int i){
        Claim claim=dataStore.get(i);
        String docs=claim.returnDocuments();
        return docs;
    }

    //Returns a string of a specific document of a specific claim
    public String readDocument(int cid,int did){
        Claim claim=getClaim(cid);
        Document doc =claim.getDocument(did);
        return doc.toString();
    }

    //Returns the size of the claim data store
    public int size(){
        return dataStore.size();
    }

    //Returns the size of documents storage of a claim
    public int getClaimSize(int cid){
        Claim claim=getClaim(cid);
        return claim.size();
    }

    //Returns a specific document of a specific claim
    public Document getDocumentbyId(int cid,int did){
        Claim claim=getClaim(cid);
        return claim.getDocument(did);
    }

    //Returns the userId that submitted a specific claim
    public int getDocUserId(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getUserId();
    }

    //Returns the digital signature of a specific document
    public String getDocSignature(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getSignature();
    }

    //Returns the content of a specific document
    public String getDocContent(int cid,int did){
        Document doc=getDocumentbyId(cid,did);
        return doc.getContent();
    }

    //Updates the document (For testing reasons)
    public void updateDocument(int cid, int did,String content){
        Document doc=getDocumentbyId(cid,did);
        doc.setContent(content);
    }

    //Returns True when the user is a employee
    public boolean isEmployee(int userId){
        return employee.getEMPLOYEES().contains(userId);
    }



}
