package project.a_la_carte.prototype.server.side;

import project.a_la_carte.prototype.recipe.maker.inventory.Recipe;

import java.util.ArrayList;

public class Order {
    ArrayList<Recipe> recipeList;
    int orderNum;
    int totalItems;
    public Order(ArrayList<Recipe> recipes, int i){
        this.recipeList = recipes;
        this.orderNum = i;
        this.totalItems = 0;
    }
    //Maybe a select button on top of the menu view recipes that would select it then it would be customizable
    //When save is hit on the customize section, the order is put on send, and the selected is unselected
    //This way the customer can have different versions of the recipe
    public void addRecipe(Recipe recipe){
        this.recipeList.add(recipe);
        this.totalItems += 1;
    }
    public void deleteRecipe(Recipe recipe){
        if (!recipeList.isEmpty() && this.totalItems != 0){
            this.recipeList.remove(recipe);
            this.totalItems -= 1;
        }
    }
    public ArrayList<Recipe> getRecipeList(){
        return this.recipeList;
    }
    public void completedSingleOrder(){
        this.totalItems -= 1;
    }
    public int getOrderNum(){
        return this.orderNum;
    }
}
