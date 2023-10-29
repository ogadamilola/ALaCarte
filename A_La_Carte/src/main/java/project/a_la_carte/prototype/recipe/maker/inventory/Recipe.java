package project.a_la_carte.prototype.recipe.maker.inventory;

import project.a_la_carte.prototype.menu.items.MenuItemRecipeButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {


    private Map<Ingredient,Double> recipeIngredients;
    private String name;
    private String description;
    private String prepInstruction;
    private float price;
    private float prepTime;
    private MenuItemRecipeButton menuItemRecipeButton;
    Boolean selectedStatus;

    public Recipe(String name){
        this.name = name;
        this.recipeIngredients = new HashMap<>();

        //Menu Item side
        this.menuItemRecipeButton = new MenuItemRecipeButton(name);
        this.selectedStatus = false;
    }

    /**
     * This is just section for adding recipes to menu item
     */
    public MenuItemRecipeButton getButton(){return this.menuItemRecipeButton;}
    public Boolean getSelectedStatus(){return this.selectedStatus;}
    public void selectedRecipe(){
        this.selectedStatus = true;
        this.getButton().select();
    }
    public void unselectRecipe(){
        this.selectedStatus = false;
        this.getButton().unselect();
    }
    /**
        * Ends here
     */

    public void addRecipeIngredients(Ingredient ingredient,Double amount){
        this.recipeIngredients.put(ingredient, amount);
     }

    public Map<Ingredient, Double> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Map<Ingredient, Double> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrepInstruction(String prepInstruction) {
        this.prepInstruction = prepInstruction;
    }

    public String getPrepInstruction() {
        return prepInstruction;
    }

    public void setPrepTime(float prepTime) {
        this.prepTime = prepTime;
    }

    public float getPrepTime() {
        return prepTime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeIngredients=" + recipeIngredients +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prepInstruction='" + prepInstruction + '\'' +
                ", price=" + price +
                ", prepTime=" + prepTime +
                '}';
    }
}
