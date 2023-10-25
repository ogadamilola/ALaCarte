package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModel {
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;

    List<Recipe> recipeList;

    public RecipeModel(){

        recipeList = new ArrayList<>();
    }
    public void addNewRecipe(String name, double price, String desc, String intruction, double prepTime, HashMap<Ingredient, Double> ingredientMap){

        Recipe recipe = new Recipe(name);
        recipe.setPrice((float) price);
        recipe.setDescription(desc);
        recipe.setPrepInstruction(intruction);
        recipe.setPrepTime((float) prepTime);

        for(Map.Entry<Ingredient,Double> entry : ingredientMap.entrySet()){
            recipe.addRecipeIngredients(entry.getKey(), entry.getValue());
        }
        recipeList.add(recipe);
        System.out.println(recipe);
    }



    public void setRecipeListView(RecipeListView newView){
        this.recipeListView = newView;
    }
    public void setRecipeMakerView(RecipeMakerView newView){
        this.recipeMakerView = newView;
    }


}
