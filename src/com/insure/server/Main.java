package com.insure.server;
import com.sun.xml.ws.api.model.wsdl.WSDLOutput;

import javax.xml.ws.Endpoint;
import java.sql.SQLOutput;
import java.util.Random;

public class Main {

    public static final int NUM_ITER = 10000;
    public static final int NUM_ELE = 50;

    static class MyThread extends java.lang.Thread {

        private ClaimDataStore db;

        MyThread(ClaimDataStore dataStore) {
            this.db = dataStore;
        }

        public void run() {
            for (int i = 1; i <= NUM_ITER; i++) {
                try {
                    db.createClaim("New claim", 7);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public static void main(String args[]) throws Exception {


        System.out.println("Project template - server");
        ClaimDataStore insure = new ClaimDataStore();
        Endpoint.publish("http://localhost:8090/docstorage", insure);

        ClaimDataStore DB = new ClaimDataStore();

        Thread a = new MyThread(DB);
        Thread b = new MyThread(DB);
        Thread c= new MyThread(DB);
        Thread d=new MyThread(DB);

        a.start();
        b.start();
        c.start();
        d.start();

        a.join();
        b.join();
        c.join();
        d.join();


        System.out.println(DB.size());

    }
}


