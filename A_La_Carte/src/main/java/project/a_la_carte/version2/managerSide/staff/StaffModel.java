package project.a_la_carte.version2.managerSide.staff;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import project.a_la_carte.version2.classesObjects.*;
import com.google.gson.Gson;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StaffModel { //TODO LOGIN INFORMATION, REFULAR STAFF = PIN, MANAGERS = username & password
    private static  final String FILE_PATH = "staffList.json";
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
            setLoadedStaff(null);
        }catch (IllegalArgumentException e){
            System.out.println("Staff does not exist, can not delete");
        }catch (NullPointerException e){
            System.out.println("Staff does not exist, can not delete");
        }
    }

    public Staff getStaffById(String id ){
        String actualID = (id);
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

    public void loadList(){
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            //dont know whats going on here, found a solution tho-> https://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class
            Type arrayListType = new TypeToken<ArrayList<Staff>>(){}.getType();
            staffList = gson.fromJson(reader, arrayListType);
            notifySubscribers();
        } catch (IOException e) {
            // File doesn't exist or other IO exception
            System.out.println("FAIL");
        }
    }

    public void saveList(){
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(staffList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
