package project.a_la_carte.version2.classesObjects;

public class Cook extends Staff{
    position position = Staff.position.Cook;
    public Cook(String firstName, String lastName, int sin, String id) {
        super(firstName, lastName, sin, id);
    }
    //Dont really know how a cook would differ from
    //just staff in our system

}
