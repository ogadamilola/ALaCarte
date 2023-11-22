package project.a_la_carte.version2.classesObjects;

import project.a_la_carte.version2.serverSide.tableSystem.Bill;

import java.util.ArrayList;

public class Server extends Staff {
    //use server class to track bills
    ArrayList<Bill> currentBills;
    float totalTips;
    public Server(String firstName, String lastName, int sin,String id) {
        super(firstName, lastName, sin,id);
    }
}
