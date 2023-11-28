package project.a_la_carte.version2.serverSide.tableSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private String name;
    private LocalDateTime time;
    private int numberOfGuests;


    public Reservation(String name, LocalDateTime time, int numberOfGuests) {
        this.name = name;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm");
        return String.format("Reservation for '%s' on %s for %d guests",
                name,
                time.format(formatter),
                numberOfGuests);
    }
}
