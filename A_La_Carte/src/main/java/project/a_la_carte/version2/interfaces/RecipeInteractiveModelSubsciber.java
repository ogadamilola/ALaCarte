package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.*;

import java.util.Map;

public interface RecipeInteractiveModelSubsciber {
    public void iModelChanged(Map<String,Double> tempIngredientList, Recipe loadedRecipe, boolean isCreating);
}
