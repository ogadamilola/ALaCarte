package project.a_la_carte.version2.serverSide.tableSystem;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;

import java.io.IOException;
import java.util.ArrayList;

public class OrderAdapter extends TypeAdapter<Order> {
    @Override
    public void write(JsonWriter out, Order order) throws IOException {
        out.beginObject();
        out.name("orderNum").value(order.getOrderNum());
        out.name("completed").value(order.isFinished());
        out.name("tableNum").value(order.getTableNum());


        out.name("menuItems").beginArray();
        for (MenuFoodItem item : order.getOrderList()) {
            out.value(item.toString());
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public Order read(JsonReader in) throws IOException {
        in.beginObject();
        Order order = new Order(new ArrayList<>(), 0, 0);
        ArrayList<MenuFoodItem> menuItems = new ArrayList<>();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "orderNum":
                    order.setOrderNum(in.nextInt());
                    break;
                case "completed":
                    order.setFinished(in.nextBoolean());
                    break;
                case "tableNum":
                    order.setTableNum(in.nextInt());
                    break;
                case "menuItems":
                    in.beginArray();
                    while (in.hasNext()) {
                        String itemString = in.nextString();
                        if(itemString.isEmpty()){
                            itemString = " ";
                        }
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        order.setMenuItems(menuItems);
        return order;
    }

    public MenuFoodItem convertStringToMenuFoodItem(String itemString) {
        try {
            String[] parts = itemString.split(", ");
            String name = parts[1].split("'")[1];
            String description = parts[2].split("'")[1];



            return new MenuFoodItem(new ArrayList<>(), name, description);
        } catch (Exception e) {
            return new MenuFoodItem(new ArrayList<>(), "Invalid", "Invalid");
        }
    }

    private ArrayList<MenuFoodItem> readMenuItems(JsonReader in) throws IOException {
        ArrayList<MenuFoodItem> items = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            String name = null;
            while (in.hasNext()) {
                String itemName = in.nextName();
                switch (itemName) {
                    case "name":
                        name = in.nextString();
                        break;

                }
            }
            items.add(new MenuFoodItem(null, name, null));
            in.endObject();
        }
        in.endArray();
        return items;
    }

}
