package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipie {

    private Map<Ingredient,Double> recipieIngredients;
    private String name;
    private String description;
    private String prepInstruction;
    private float price;
    private float prepTime;

    public Recipie(String name){
        this.name = name;
        this.recipieIngredients = new HashMap<>();

    }

    public void addRecipieIngredients(Ingredient ingredient,Double amount){

        //TODO make this function work
        this.recipieIngredients.put(ingredient, amount);
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
        return "Recipie{" +
                //"recipieIngredients=" + recipieIngredients +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prepInstruction='" + prepInstruction + '\'' +
                ", price=" + price +
                ", prepTime=" + prepTime +
                '}';
    }
}
