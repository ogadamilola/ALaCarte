package project.a_la_carte.version2.classesObjects;

import java.util.Objects;

public class Ingredient{
    private String name;
    private IngredientType ingredientType;
    private MeasurementUnit measurementUnit;
    private boolean commonAllergen;
    private float pricePerUnit;
    private double reorderPoint;

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

    public Ingredient(){
        this.name = "";
        //default to other
        this.ingredientType = IngredientType.Other;
        //default to count
        this.measurementUnit = MeasurementUnit.Count;
        //default to not allergen
        this.commonAllergen = false;
        //deafault orderPoint
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

    public boolean isCommonAllergen() {
        return commonAllergen;
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

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setReorderPoint(double reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public double getReorderPoint() {
        return reorderPoint;
    }

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


    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", ingredientType=" + ingredientType +
                ", measurementUnit=" + measurementUnit +
                ", commonAllergen=" + commonAllergen +
                ", pricePerUnit=" + pricePerUnit +
                ", reorderPoint=" + reorderPoint +
                '}';
    }
}

