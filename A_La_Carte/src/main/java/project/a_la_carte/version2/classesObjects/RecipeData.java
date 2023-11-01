package project.a_la_carte.version2.classesObjects;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecipeData {
    private Recipe recipe;

    /**
     * new class for displaying recipe, replaces recipe widget
     * @param recipe
     */
    public RecipeData(Recipe recipe){
        this.recipe = recipe;
    }
    public Recipe getRecipe() {
        return recipe;
    }

    public StringProperty nameProperty(){
        return new SimpleStringProperty(recipe.getName());
    }
    public StringProperty descProperty(){
        return new SimpleStringProperty(recipe.getDescription());
    }
    public FloatProperty priceProperty(){
        return new SimpleFloatProperty(recipe.getPrice());
    }


}
