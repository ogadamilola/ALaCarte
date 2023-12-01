package project.a_la_carte.version2.serverSide.tableSystem;



import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.classesObjects.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableAdapter extends TypeAdapter<Table> {
    private final TypeAdapter<Order> orderAdapter = new OrderAdapter();
    @Override
    public void write(JsonWriter out, Table table) throws IOException {
        out.beginObject();
        out.name("number").value(table.getNumber());
        out.name("status").value(table.getStatus());
        out.name("occupants").value(table.getOccupants());
        out.name("notes").value(table.getNotes());
        out.name("orders").value(table.getOrders());

        out.name("order");
        orderAdapter.write(out, table.getOrder());


        out.endObject();
    }

    @Override
    public Table read(JsonReader in) throws IOException {
        in.beginObject();
        Table table = new Table();

        while (in.hasNext()) {
            String key = in.nextName();
            switch (key) {
                case "number":
                    table.setNumber(in.nextInt());
                    break;
                case "notes":
                    table.setNotes(in.nextString());
                    break;
                case "orders":
                    table.setOrders(in.nextString());
                    break;
                case "status":
                    table.setStatus(in.nextBoolean());
                    break;
                case "occupants":
                    table.setOccupants(in.nextInt());
                    break;
                case "order":
                    Order order = new OrderAdapter().read(in);
                    table.setOrder(order);
                    break;
                case "orderList":
                    ArrayList<Order> orderList = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        Order orderItem = new OrderAdapter().read(in);
                        orderList.add(orderItem);
                    }
                    in.endArray();
                    table.setOrderList(orderList);
                    break;

                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();
        return table;
    }

    public Order deserializeOrder(JsonReader in) throws IOException {
        ArrayList<MenuFoodItem> menuItems = new ArrayList<>();
        int orderNum = 0;
        boolean completed = false;
        int tableNum = 0;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "menuItems":
                    in.beginArray();
                    while (in.hasNext()) {
                        MenuFoodItem menuItem = deserializeMenuItem(in);
                        menuItems.add(menuItem);
                    }
                    in.endArray();
                    break;
                case "orderNum":
                    orderNum = in.nextInt();
                    break;
                case "completed":
                    completed = in.nextBoolean();
                    break;
                case "tableNum":
                    tableNum = in.nextInt();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        Order order = new Order(menuItems, orderNum, tableNum);
        if (completed) {
            order.orderFinished();
        }
        return order;
    }


    public MenuFoodItem deserializeMenuItem(JsonReader in) throws IOException {
        ArrayList<Recipe> menuItemRecipes = new ArrayList<>();
        String name = null;
        String description = null;
        float price = 0.0f;

        in.beginObject();
        while (in.hasNext()) {
            String itemName = in.nextName();
            switch (itemName) {
                case "menuItemRecipes":
                    in.beginArray();
                    while (in.hasNext()) {
                        Recipe recipe = deserializeRecipe(in);
                        menuItemRecipes.add(recipe);
                    }
                    in.endArray();
                    break;
                case "name":
                    name = in.nextString();
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "price":
                    price = (float) in.nextDouble();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new MenuFoodItem(menuItemRecipes, name, description);
    }

    public Recipe deserializeRecipe(JsonReader in) throws IOException {
        String name = null;
        String description = null;
        String prepInstruction = null;
        float price = 0.0f;
        float prepTime = 0.0f;
        Map<String, Double> ingredientMap = new HashMap<>();

        in.beginObject();
        while (in.hasNext()) {
            String itemName = in.nextName();
            switch (itemName) {
                case "name":
                    name = in.nextString();
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "prepInstruction":
                    prepInstruction = in.nextString();
                    break;
                case "price":
                    price = (float) in.nextDouble();
                    break;
                case "prepTime":
                    prepTime = (float) in.nextDouble();
                    break;
                case "ingredientMap":
                    ingredientMap = readIngredientMap(in);
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        Recipe recipe = new Recipe(name);
        recipe.setDescription(description);
        recipe.setPrepInstruction(prepInstruction);
        recipe.setPrice(price);
        recipe.setPrepTime(prepTime);
        recipe.setRecipeIngredients(ingredientMap);

        return recipe;
    }

    public Map<String, Double> readIngredientMap(JsonReader in) throws IOException {
        Map<String, Double> ingredientMap = new HashMap<>();

        in.beginObject();
        while (in.hasNext()) {
            String ingredientName = in.nextName();
            double amount = in.nextDouble();
            ingredientMap.put(ingredientName, amount);
        }
        in.endObject();

        return ingredientMap;
    }

}
