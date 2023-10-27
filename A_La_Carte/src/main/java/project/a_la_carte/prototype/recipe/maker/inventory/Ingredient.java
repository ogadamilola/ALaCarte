package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Ingredient {
    private String name;
    private IngredientType ingredientType;
    private MeasurementUnit measurementUnit;
    private boolean commonAllergen;

    public enum IngredientType{
        Proteins("Proteins"),Dairy("Dairy"),Grains("Grains"),Vegetable("Vegetables"),Sauce("Sauce"),Other("Other");
        private String name;
        private IngredientType(String name){
            this.name =name;
        }
        public String getName(){
            return name;
        }
    }

    public enum MeasurementUnit{
        Pounds("Pounds"), Count("Count");
        private String name;
        MeasurementUnit(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
    /**
     * @param name of ingredient
     */
    public Ingredient(String name){
        this.name = name;

        //default to other
        this.ingredientType = IngredientType.Other;
        //default to count
        this.measurementUnit = MeasurementUnit.Count;
        //default to not allergen
        this.commonAllergen = false;
    }
    public String getName() {
        return name;
    }
    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setCommonAllergen(boolean commonAllergen) {
        this.commonAllergen = commonAllergen;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
    //deleted unnecessary method


    //property stuff for table


    @Override
    public boolean equals(Object o) { //needed for the hashtable handling duplicates
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(name, ingredient.name) && Objects.equals(ingredientType, ingredient.ingredientType);
    }

    @Override
    public int hashCode() { //also needed
        return Objects.hash(name, ingredientType);
    }





}

