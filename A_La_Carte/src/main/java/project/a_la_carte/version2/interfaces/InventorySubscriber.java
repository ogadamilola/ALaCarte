package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.*;

import java.util.ArrayList;
import java.util.Map;

public interface InventorySubscriber {
    public void modelChanged(Map<String, Double> ingredientInventory, Ingredient loadedIngredient, ArrayList<Ingredient> ingredientList);
}
