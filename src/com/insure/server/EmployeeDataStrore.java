package com.insure.server;

import java.util.HashSet;

public class EmployeeDataStrore {

    public  static HashSet<Integer> EMPLOYEES;

    public HashSet<Integer> getEMPLOYEES() {
        return EMPLOYEES;
    }

    public EmployeeDataStrore() {
        EMPLOYEES = new HashSet<Integer>();
        EMPLOYEES.add(1);
        EMPLOYEES.add(2);
        EMPLOYEES.add(3);
        EMPLOYEES.add(4);
        EMPLOYEES.add(5);
    }
}
