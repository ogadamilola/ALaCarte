package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.beans.property.*;

public class IngredientData {
    private Ingredient ingredient;
    private Double quantity;

    /**
     * new class for displaying ingredient in table, replaces ingredient widget
     * @param ingredient
     * @param quantity
     */
    public IngredientData(Ingredient ingredient,double quantity){
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient(){
        return this.ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public DoubleProperty inventoryQuantityProperty(){
        return new SimpleDoubleProperty(quantity);
    }

    public DoubleProperty recipeQuantityProperty(){//use to display ounces in recipe tables
        switch(this.ingredient.getMeasurementUnit()){
            case Count -> {
                return new SimpleDoubleProperty(quantity);
            }
            case Pounds -> {return new SimpleDoubleProperty(quantity*16);}


            default -> {return new SimpleDoubleProperty(-1);}
        }

    }
    public StringProperty nameProperty(){
        return new SimpleStringProperty(ingredient.getName());
    }
    public StringProperty typeProperty(){
        return new SimpleStringProperty(ingredient.getIngredientType().getName());
    }

    public StringProperty inventoryMeasurementProperty(){ //use to display pounds in inventory
        return new SimpleStringProperty(ingredient.getMeasurementUnit().getName());
    }

    //use to display ounces recipe
    public StringProperty recipeMeasurementProperty(){
        StringProperty measurementUnit;
        switch (this.ingredient.getMeasurementUnit()){
            case Pounds -> measurementUnit = new SimpleStringProperty("Oz");

            case Count ->  measurementUnit = new SimpleStringProperty(ingredient.getMeasurementUnit().getName());

            default -> measurementUnit = new SimpleStringProperty((ingredient.getMeasurementUnit().getName()));
        }
        return  measurementUnit;
    }
    public BooleanProperty allergenProperty(){
        return new SimpleBooleanProperty(ingredient.isCommonAllergen());
    }

    //TODO is this a good way for reorderpoints?
    public StringProperty statusProperty(){
        StringProperty status;
        switch (this.ingredient.getMeasurementUnit()){
            case Pounds:
                if(quantity<5){
                    status = new SimpleStringProperty("RUNNING OUT");

                } else if (quantity >6 && quantity < 10) {
                    status = new SimpleStringProperty("LIMITED QUANTITY");

                } else {
                    status = new SimpleStringProperty("PLENTIFUL");
                }

                return status;
            case Count:
                if(quantity < 40){
                    status = new SimpleStringProperty("RUNNING OUT");
                } else if (quantity > 41 && quantity < 70) {
                    status = new SimpleStringProperty("LIMITED QUANTITY");
                } else {
                    status = new SimpleStringProperty("PLENTIFUL");
                }
                return status;

            default:
                status = new SimpleStringProperty("ERROR");
                return status;
        }

    }
}
