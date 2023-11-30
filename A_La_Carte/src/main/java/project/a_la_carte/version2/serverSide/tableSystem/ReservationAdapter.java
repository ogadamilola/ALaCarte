package project.a_la_carte.version2.serverSide.tableSystem;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationAdapter extends TypeAdapter<Reservation> {
    @Override
    public void write(JsonWriter out, Reservation reservation) throws IOException {
        out.beginObject();
        out.name("name").value(reservation.getName());
        out.name("time").value(reservation.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        out.name("numberOfGuests").value(reservation.getNumberOfGuests());
        out.endObject();
    }

    @Override
    public Reservation read(JsonReader in) throws IOException {
        in.beginObject();
        Reservation reservation = new Reservation();

        while (in.hasNext()) {
            String key = in.nextName();
            switch (key) {
                case "name":
                    reservation.setName(in.nextString());
                    break;
                case "time":
                    String timeString = in.nextString();
                    reservation.setTime(LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                case "numberOfGuests":
                    reservation.setNumberOfGuests(in.nextInt());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();
        return reservation;
    }
}