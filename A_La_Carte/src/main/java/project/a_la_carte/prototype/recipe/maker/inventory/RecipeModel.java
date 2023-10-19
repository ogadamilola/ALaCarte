package project.a_la_carte.prototype.recipe.maker.inventory;

public class RecipeModel {
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;
    String name;
    String description;
    String prepInstruction;
    float price;
    float prepTime;
    public RecipeModel(){

    }
    public void setRecipeListView(RecipeListView newView){
        this.recipeListView = newView;
    }
    public void setRecipeMakerView(RecipeMakerView newView){
        this.recipeMakerView = newView;
    }
}
