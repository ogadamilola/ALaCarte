package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.Map;

public interface MenuItemInteractiveModelSubscriber {
    public void iModelChanged(Map<Recipe,Double> tempRecipeList);
}
