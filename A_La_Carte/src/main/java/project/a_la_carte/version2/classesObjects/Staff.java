package project.a_la_carte.version2.classesObjects;

import java.util.Objects;

public class Staff {
    private String firstName;
    private String lastName;
    private String staffID;
    private int sin;
    private int tips;
    private String username;
    private String password;

    public enum position {
        Manager("Manager"), Server("Server"), Cook("Cook");
        private String name;

        position(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private position position;

    /**
     * Staff class no params because apparently gson works better like that
     */
    public Staff() {

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

    public int getTips() {
        return tips;
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
    public void setTips(int tips) {
        this.tips = tips;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    } //could also do different tags depending on position

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    @Override
    public String toString() {
        return "Staff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", staffID='" + staffID + '\'' +
                ", sin=" + sin +
                ", position=" + position +
                '}';
    }
}


