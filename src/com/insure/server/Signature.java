package com.insure.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Signature {

    private String message;
    private String hash;
    private String encryptedHash;
    private PrivateKey privatekey;
    private PublicKey publickey;

    public String getMessage(){
        return message;
    }

    public String getHash(){
        return hash;
    }
    public String getEncryptedHash() {
        return this.encryptedHash;
    }


    public String getHashstring() {
        return this.hash;
    }

    public Signature(String message,String filename) throws Exception {
        this.message=message;
        this.hash= makeHash(message);
        this.encryptedHash=encriptarMessage(hash,filename);


    }
    //este metodo pega na mensagem que queremos enviar e cria o seu hash baseado no sha-256
    private String makeHash(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest=MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(message.getBytes("UTF-8")));
    }

    public String encriptarMessage(String hashmessage,String filename) throws Exception {
        AsymEncryptPriv PrivateEncrypt= new AsymEncryptPriv();
        this.privatekey=PrivateEncrypt.getPrivate(filename);
        return PrivateEncrypt.encryptText(hashmessage,privatekey);

    }
    public String desencriptarMessage(String filename, String encryptedHash) throws Exception {
        AsymDecryptPub privateDesencrypt= new AsymDecryptPub();
        publickey=privateDesencrypt.getPublic(filename);
        return privateDesencrypt.decryptText(encryptedHash,publickey);
    }

    public boolean validateSignature(String message,String encryptedHash,String filename) throws Exception {
        String stringHash =this.desencriptarMessage(filename,encryptedHash);
        if (stringHash.equals(encryptedHash)){
            return true;
        }else{
            throw new Exception("This Document was tampered");
        }
    }

    public static void main(String[] args) throws Exception {
        Signature signature=new Signature("https://www.tutorialspoint.com/java_cryptography/java_cryptography_creating_signature.htm","keys\\Insured1\\Insured1PrivateKey");
        System.out.println(signature.getMessage()+"\n");
        System.out.println(signature.getHash()+"\n");
        System.out.println(signature.getEncryptedHash()+"\n");
        System.out.println(signature.desencriptarMessage("keys\\Insured1\\Insured1PublicKey - Copy",signature.getEncryptedHash()));
        signature.desencriptarMessage("keys\\Insured1\\Insured2PublicKey - Copy",signature.getEncryptedHash());




    }
}
