package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.Recipe;

import java.util.List;

public interface RecipeModelSubscriber {
    public void RecipieModelChanged(List<Recipe> recipeList);
}
