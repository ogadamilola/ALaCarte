package project.a_la_carte.version2.classesObjects;

import javafx.beans.property.*;

public class IngredientData {
    private Ingredient ingredient;
    private Double quantity;

    /**
     * new class for displaying ingredient in table, replaces ingredient widget
     * @param ingredient
     * @param quantity
     */
    public IngredientData(Ingredient ingredient, double quantity){
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

    public FloatProperty priceProperty(){
        return new SimpleFloatProperty(ingredient.getPricePerUnit());
    }

    public StringProperty statusProperty(){
        StringProperty status;
        int range;

        if (ingredient.getMeasurementUnit() == Ingredient.MeasurementUnit.Pounds) {
            range = 5;
        } else {
            range = 15;
        }


        if(Math.abs(quantity - ingredient.getReorderPoint()) <= range){
            //if quantity is within range
            status = new SimpleStringProperty("Limited Quantity");
        } else if(quantity > (ingredient.getReorderPoint()) + range){
            //if quantity is greater than reoderpoint + range
            status = new SimpleStringProperty("Plentiful");
        } else {
            //if quantity is less than reorderPoint - range
            status = new SimpleStringProperty("Running Low");
        }
        return status;
    }
}
