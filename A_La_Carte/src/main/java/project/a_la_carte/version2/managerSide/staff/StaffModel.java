package project.a_la_carte.version2.managerSide.staff;

import project.a_la_carte.version2.classesObjects.Staff;
import project.a_la_carte.version2.interfaces.InventorySubscriber;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.util.ArrayList;

public class StaffModel {
    private ArrayList<Staff> staffList;
    private ArrayList<StaffModelSubscriber> subscriberList;
    private Staff loadedStaff;

    public StaffModel(){
        staffList = new ArrayList<>();
        subscriberList = new ArrayList<>();
        loadedStaff = null;
    }

    public void addSub(StaffModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }
    public void notifySubscribers(){
        for(StaffModelSubscriber sub : subscriberList){
            sub.modelChanged(staffList);
        }
    }
}
