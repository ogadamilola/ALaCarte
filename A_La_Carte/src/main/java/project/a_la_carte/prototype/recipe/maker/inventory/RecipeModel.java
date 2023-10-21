package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;

    List<Recipie> recipeList;

    public RecipeModel(){

        recipeList = new ArrayList<>();
    }
    public void addNewRecipe(String name,double price, String desc, String intruction, double prepTime){
        //TODO work on adding ingredients
        Recipie recipe = new Recipie(name);
        recipe.setPrice((float) price);
        recipe.setDescription(desc);
        recipe.setPrepInstruction(intruction);
        recipe.setPrepTime((float) prepTime);

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
