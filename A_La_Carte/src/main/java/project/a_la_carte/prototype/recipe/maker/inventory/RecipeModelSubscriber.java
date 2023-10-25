package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.List;

public interface RecipeModelSubscriber {
    public void RecipieModelChanged(List<Recipe> recipeList);
}
