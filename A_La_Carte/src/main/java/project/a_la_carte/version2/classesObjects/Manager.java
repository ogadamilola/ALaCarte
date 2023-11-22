package project.a_la_carte.version2.classesObjects;

public class Manager extends Staff{

    String userName;
    String password;

    position pos = position.Manager;
    //also don't know how it could differ.
    public Manager(String firstName, String lastName, int sin,String id) {
        super(firstName, lastName, sin,id);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
