package com.insure.server;

import java.util.HashSet;

public class InsuredDataStore {
    public HashSet<Integer> getINSURED() {
        return INSURED;
    }

    public static HashSet<Integer> INSURED;

    public InsuredDataStore() {
        INSURED=new HashSet<Integer>();
        INSURED.add(6);
        INSURED.add(7);
        INSURED.add(8);
        INSURED.add(9);
        INSURED.add(10);
    }


}

