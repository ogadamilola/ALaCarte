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

    public void addStaff(String fName, String lName, String id, Staff.position position, int sin ){
        try {
            if(getStaffById(id) != null){//staff already exists with this id
                throw new IllegalArgumentException();
            }
            Staff staff = new Staff(fName, lName, sin);
            staff.setStaffID(id);
            staff.setPosition(position);

            staffList.add(staff);
            notifySubscribers();
        } catch (IllegalArgumentException e){
            System.out.println("Staff ID already exists, use a different id");
        }
    }

    public void updateStaff(String fName, String lName, String id, Staff.position position, int sin ){
        try{
            Staff staff = getStaffById(id);
            staff.setFirstName(fName);
            staff.setLastName(lName);
            staff.setPosition(position);
            staff.setSin(sin);
            notifySubscribers();
        }catch (IllegalArgumentException e){
            System.out.println("Staff does not exist, can not update");
        }catch (NullPointerException e){
            System.out.println("Staff does not exist, can not update");
        }
    }

    public void deleteStaff(String id){
        try {
            if(getStaffById(id) == null){
                throw new IllegalArgumentException();
            }
            staffList.remove(getStaffById(id));
            notifySubscribers();
        }catch (IllegalArgumentException e){
            System.out.println("Staff does not exist, can not delete");
        }catch (NullPointerException e){
            System.out.println("Staff does not exist, can not delete");
        }
    }

    public Staff getStaffById(String id ){
        String actualID = ("EMP" + id);
        for(Staff s : staffList){
            if(s.getStaffID().equals(actualID)){
                return s;
            }
        }
        return null;
    }
    public void setLoadedStaff(Staff loadedStaff) {
        this.loadedStaff = loadedStaff;
        notifySubscribers();
    }

    public void addSub(StaffModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }
    public void notifySubscribers(){

        for(StaffModelSubscriber sub : subscriberList){
            sub.modelChanged(staffList,loadedStaff);
        }
    }
}
