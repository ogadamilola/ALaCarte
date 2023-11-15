package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.Staff;

import java.util.ArrayList;

public interface StaffModelSubscriber {
    public void modelChanged(ArrayList<Staff> staffList,Staff loadedStaff);

}
