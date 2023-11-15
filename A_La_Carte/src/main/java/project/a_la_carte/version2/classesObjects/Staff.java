package project.a_la_carte.version2.classesObjects;

import java.util.Objects;

public class Staff {
    private String firstName;
    private String lastName;
    private String staffID;
    private int sin;
    public enum position{
        Manager("Manager"), Server("Server"), Cook("Cook");
        private String name;
        position(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
    }

    private position position;

    public Staff(String firstName, String lastName, int sin){
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        position = null;
        staffID = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStaffID() {
        return staffID;
    }

    public int getSin() {
        return sin;
    }

    public position getPosition() {
        return position;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(Staff.position position) {
        this.position = position;
    }

    public void setSin(int sin) {
        this.sin = sin;
    }
    public void setStaffID(String staffID) {
        this.staffID = "EMP"+staffID;
    } //could also do different tags depending on position

    //intellJ generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return sin == staff.sin && Objects.equals(firstName, staff.firstName) && Objects.equals(lastName, staff.lastName) && Objects.equals(staffID, staff.staffID) && position == staff.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, staffID, sin, position);
    }
}
