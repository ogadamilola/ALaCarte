package project.a_la_carte.version2.classesObjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaffData {
    private Staff staff;

    public StaffData(Staff staff){
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public StringProperty firstNameProperty(){
        return new SimpleStringProperty(staff.getFirstName());
    }
    public StringProperty lastNameProperty(){
        return new SimpleStringProperty(staff.getLastName());
    }
    public StringProperty positionProperty(){
        return new SimpleStringProperty(staff.getPosition().getName());
    }
    public StringProperty idProperty(){
        return new SimpleStringProperty(staff.getStaffID());
    }
    public IntegerProperty sinProperty(){
        return new SimpleIntegerProperty(staff.getSin());
    }


}
