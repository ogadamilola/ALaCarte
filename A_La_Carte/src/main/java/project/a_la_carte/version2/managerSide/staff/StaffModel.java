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
import java.util.List;

public class StaffModel {
    private static  final String FILE_PATH = "staffList.json";
    private ArrayList<Staff> staffList;
    private ArrayList<StaffModelSubscriber> subscriberList;
    private Staff loadedStaff;

    public StaffModel(){
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            //dont know whats going on here, found a solution tho-> https://stackoverflow.com/questions/27253555/com-google-gson-internal-linkedtreemap-cannot-be-cast-to-my-class
            Type arrayListType = new TypeToken<ArrayList<Staff>>(){}.getType();
            staffList = gson.fromJson(reader, arrayListType);

            if(staffList == null){
                throw new IOException();
            }
        } catch (IOException e) {
            staffList = new ArrayList<>();
        }

        subscriberList = new ArrayList<>();
        loadedStaff = null;
        notifySubscribers();
    }

    public void addStaff(String fName, String lName, String id, Staff.position position, int sin ){

            if(getStaffById(id) != null){//staff already exists with this id
                throw new IllegalArgumentException();
            }
            if(position != Staff.position.Manager) {
                Staff staff = new Staff();
                staff.setFirstName(fName);
                staff.setLastName(lName);
                staff.setStaffID(id);
                staff.setPosition(position);
                staff.setSin(sin);
                staffList.add(staff);
                staff.setUsername(null);//not manager
                staff.setPassword(null);//not manager
            }

            saveList();
            notifySubscribers();

    }

    public void addManager(String fName, String lName, String id, Staff.position position,int sin,String username, String password){
        //TODO possible exception handling here
            Staff staff = new Staff();
            staff.setFirstName(fName);
            staff.setLastName(lName);
            staff.setStaffID(id);
            staff.setPosition(position);
            staff.setSin(sin);
            staff.setUsername(username);
            staff.setPassword(password);

            staffList.add(staff);

            saveList();
            notifySubscribers();
    }

    public void updateStaff(String fName, String lName, String id, Staff.position position, int sin,String username,String password){
        //TODO possible exception handling here
            Staff staff = getStaffById(id);
            staff.setFirstName(fName);
            staff.setLastName(lName);
            staff.setPosition(position);
            staff.setSin(sin);

            if(position == Staff.position.Manager){
                staff.setPassword(password);
                staff.setUsername(username);
            }
            notifySubscribers();
            saveList();
    }

    public void deleteStaff(String id){
        try {
            if(getStaffById(id) == null){
                throw new IllegalArgumentException();
            }
            staffList.remove(getStaffById(id));
            notifySubscribers();
            setLoadedStaff(null);
            saveList();
        }catch (IllegalArgumentException e){
            System.out.println("Staff does not exist, can not delete");
        }catch (NullPointerException e){
            System.out.println("Staff does not exist, can not delete");
        }
    }


    public boolean verifyLogIn(String username, String password){
        for(Staff staff: staffList){
            if(staff.getPosition() == Staff.position.Manager){
                if (staff.getUsername().equals(username) && staff.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyServerLogIn(String pin){
        for (Staff staff : staffList){
            if(staff.getPosition() == Staff.position.Server || staff.getPosition() == Staff.position.Manager){
                if(staff.getStaffID().equals(pin)){
                    return true;
                }
            }
        }
        return false;
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
