package project.a_la_carte.version2.managerSide.RestaurantInfo;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;

public class RestaurantDayAdapter extends TypeAdapter<RestaurantDay> {

    @Override
    public void write(JsonWriter out, RestaurantDay restaurantDay) throws IOException {
        out.beginObject();
        out.name("date").value(restaurantDay.getDate());
        out.name("menuItemMap").jsonValue(new Gson().toJson(restaurantDay.getMenuItemMap()));
        out.name("ingredientUsageMap").jsonValue(new Gson().toJson(restaurantDay.getIngredientUsageMap()));
        out.name("totalOrders").value(restaurantDay.getTotalOrders());
        out.name("incomeToday").value(restaurantDay.getIncomeToday());
        out.endObject();
    }

    @Override
    public RestaurantDay read(JsonReader in) throws IOException {
        in.beginObject();
        RestaurantDay restaurantDay = new RestaurantDay();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "date":
                    restaurantDay.setDate(in.nextString());
                    break;
                case "menuItemMap":
                    restaurantDay.setMenuItemMap(new Gson().fromJson(in, HashMap.class));
                    break;
                case "ingredientUsageMap":
                    restaurantDay.setIngredientUsageMap(new Gson().fromJson(in, HashMap.class));
                    break;
                case "totalOrders":
                    restaurantDay.setTotalOrders(in.nextInt());
                    break;
                case "incomeToday":
                    restaurantDay.setIncomeToday((float) in.nextDouble());
                    break;
            }
        }
        in.endObject();
        return restaurantDay;
    }
}
