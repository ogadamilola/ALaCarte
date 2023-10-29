package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.Map;

public interface RecipeInteractiveModelSubsciber {
    public void iModelChanged(Map<Ingredient,Double> tempIngredientList,Recipe loadedRecipe);
}
