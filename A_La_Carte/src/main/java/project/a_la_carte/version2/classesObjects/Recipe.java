package project.a_la_carte.version2.classesObjects;

import project.a_la_carte.version2.menuItems.widgets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe {


    private Map<String, Double> ingredientMap;
    private String name;
    private String description;
    private String prepInstruction;
    private float price;
    private float prepTime;
    private MenuItemRecipeButton menuItemRecipeButton;
    Boolean selectedStatus;

    public Recipe(String name){
        this.name = name;
        this.ingredientMap = new HashMap<>();

        //Menu Item side
        this.menuItemRecipeButton = new MenuItemRecipeButton(name);
        this.selectedStatus = false;
    }

    /**
     * This is just section for adding recipes to menu item
     */
    public void setButton() {this.menuItemRecipeButton = new MenuItemRecipeButton(this.name);}
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
        this.ingredientMap.put(ingredient.getName(), amount);
     }

    public Map<String, Double> getRecipeIngredients() {
        return ingredientMap;
    }

    public void setRecipeIngredients(Map<String, Double> recipeIngredients) {
        this.ingredientMap = recipeIngredients;
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
                "recipeIngredients=" + ingredientMap +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prepInstruction='" + prepInstruction + '\'' +
                ", price=" + price +
                ", prepTime=" + prepTime +
                '}';
    }
}
