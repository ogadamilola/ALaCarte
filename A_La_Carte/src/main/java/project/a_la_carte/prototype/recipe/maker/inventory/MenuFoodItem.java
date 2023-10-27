package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Float.max;

/** NOTE ON CLASS NAME
 * This class could NOT be called "MenuItem" because there is already a class with this name.
 * in the Maven library
 * Maven: org.openjfx:javafx-controls:win:18.0.1 (javafx-controls-18.0.1-win.jar) javafx.scene.control MenuItem
 */
public class MenuFoodItem {
    private Map<Recipe, Double> menuItemRecipes;
    private String name;
    private String description;
    private float price;

    private float prepTime;


    public MenuFoodItem(String name) {
        this.name = name;
        this.menuItemRecipes = new HashMap<>();

    }

    public void addMenuItemRecipes(Recipe recipe, Double amount){
        this.menuItemRecipes.put(recipe, amount);

        // This is untested, and I'm not sure if it works - Oct 25 Evan
        this.price += recipe.getPrice();
        this.prepTime = max(this.prepTime, recipe.getPrepTime());
    }

    public Map<Recipe, Double> getMenuItemRecipes() {
        return menuItemRecipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrepTime() {
        return prepTime;
    }

    public String toString() {
        return "MenuItem{" +
                "menuItemRecipes" + menuItemRecipes +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", prepTime=" + prepTime +
                '}';
    }
}
