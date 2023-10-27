package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public DoubleProperty quantityProperty(){
        return new SimpleDoubleProperty(quantity);
    }
    public StringProperty nameProperty(){
        return new SimpleStringProperty(ingredient.getName());
    }
    public StringProperty typeProperty(){
        return new SimpleStringProperty(ingredient.getIngredientType().getName());
    }
    public StringProperty measurementProperty(){
        return new SimpleStringProperty(ingredient.getMeasurementUnit().getName());
    }
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
