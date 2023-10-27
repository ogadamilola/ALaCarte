package project.a_la_carte.prototype.menu.items;

import project.a_la_carte.prototype.recipe.maker.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MenuItemModel {
    ArrayList<MenuFoodItem> menuItemsList;
    List<MenuItemModelSubscriber> subscriberList;
    ArrayList<Recipe> recipeArrayList;
    ArrayList<Recipe> addedRecipes;
    MenuFoodItem selectedItem;
    Recipe selectedRecipe;
    Recipe selectedAddedRecipe;
    public MenuItemModel(){
        menuItemsList = new ArrayList<>();
        subscriberList = new ArrayList<>();
        addedRecipes = new ArrayList<>();
    }
    public void addRecipesToItem(Recipe recipe){
        Recipe newRecipe = new Recipe(recipe.getName());
        newRecipe.setDescription(recipe.getDescription());
        newRecipe.setPrice(recipe.getPrice());
        newRecipe.setPrepTime(recipe.getPrepTime());
        newRecipe.setPrepInstruction(recipe.getPrepInstruction());

        this.addedRecipes.add(newRecipe);
        notifySubscribers();
    }
    public void removeRecipeFromItem(Recipe recipe){
        this.addedRecipes.remove(recipe);
        notifySubscribers();
    }
    public void resetAddedRecipes(){
        this.addedRecipes = new ArrayList<>();
        notifySubscribers();
    }
    public ArrayList<Recipe> getAddedRecipes(){
        return this.addedRecipes;
    }
    public Recipe getSelectedAddedRecipe(){return this.selectedAddedRecipe;}
    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }
    public void selectAddedRecipe(Recipe newRecipe){
        this.getRecipeArrayList().forEach((Recipe::unselectRecipe));
        this.getAddedRecipes().forEach((Recipe::unselectRecipe));

        this.selectedRecipe = null;
        this.selectedAddedRecipe = newRecipe;
        newRecipe.selectedRecipe();
    }
    public void setSelectedAddedRecipe(ArrayList<Recipe> newList){
        this.addedRecipes = newList;
        notifySubscribers();
    }
    public void setRecipeArrayList(ArrayList<Recipe> newList){
        this.recipeArrayList = newList;
        notifySubscribers();
    }
    public ArrayList<Recipe> getRecipeArrayList(){
        return this.recipeArrayList;
    }
    public void selectRecipe(Recipe newRecipe){
        this.getRecipeArrayList().forEach((Recipe::unselectRecipe));
        this.getAddedRecipes().forEach((Recipe::unselectRecipe));

        this.selectedRecipe = newRecipe;
        this.selectedAddedRecipe = null;
        newRecipe.selectedRecipe();
    }
    public void addNewMenuItem(MenuFoodItem menuFoodItem) {
        this.menuItemsList.add(menuFoodItem);
        notifySubscribers();
    }
    public void selectMenuItem(MenuFoodItem newItem){
        menuItemsList.forEach((MenuFoodItem::unselectRecipe));

        this.selectedItem = newItem;
        this.addedRecipes = selectedItem.getMenuItemRecipes();
        notifySubscribers();
    }
    public MenuFoodItem getSelectedItem(){return this.selectedItem;}
    public void addSubscriber(MenuItemModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }

    public void notifySubscribers(){
        for(MenuItemModelSubscriber subscriber : subscriberList){
            subscriber.MenuItemModelChanged(menuItemsList);
        }
    }

    public ArrayList<MenuFoodItem> getMenuItemsList(){return this.menuItemsList;}
}
