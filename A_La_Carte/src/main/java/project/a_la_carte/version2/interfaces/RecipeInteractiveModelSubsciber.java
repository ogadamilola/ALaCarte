package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.Ingredient;
import project.a_la_carte.version2.classesObjects.Recipe;

import java.util.Map;

public interface RecipeInteractiveModelSubsciber {
    public void iModelChanged(Map<Ingredient,Double> tempIngredientList, Recipe loadedRecipe, boolean isCreating);
}
