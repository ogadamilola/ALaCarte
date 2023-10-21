package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.Map;

public interface InventorySubscriber {
    public void modelChanged(Map<Ingredient,Double> ingredientInventory);
}
