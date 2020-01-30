package com.insure.server;
import javax.xml.ws.Endpoint;
import java.util.Random;

public class Main {

    public static final int NUM_ITER = 1000;
    public static final int NUM_ELE = 50;

    static class MyThread extends java.lang.Thread {

        private ClaimDataStore db;
        private Random numGenerator;

        MyThread(ClaimDataStore dataStore) {
            this.db = dataStore;
            this.numGenerator = new Random();
        }

        public void run() {
            for (int i = 1; i <= NUM_ITER; i++) {
                db.createClaim("New claim");
            }

            for (int j = 1; j <= 2000; j++) {

                Claim claim = db.getClaim(j);
                db.addDocToClaim(j, "Teste", "Novo doc");

            }
        }

    }


    public static void main(String args[]) throws InterruptedException {

        System.out.println("Project template - server");
        ClaimDataStore insure=new ClaimDataStore();
        Endpoint.publish("http://localhost:8090/docstorage",insure);


//        ClaimDataStore DB = new ClaimDataStore();
//        Thread a = new MyThread(DB);
//        Thread b = new MyThread(DB);
//
//        a.start();
//        b.start();
//
//        a.join();
//        b.join();
//
//        // sum the elements in the map
//        for (int i = 1; i <= DB.size(); i++) {
//            Claim el = DB.getClaim(i);
//            System.out.println(el.toString());
//            for (int j = 1; j <= el.size(); j++) {
//                Document doc = el.getDocument(j);
//                System.out.println(doc.toString());
//                //System.out.println(el.size());
//            }
//        }
//
//        System.out.println(DB.size());
//        //for
//        System.out.println("done");
    }
}

