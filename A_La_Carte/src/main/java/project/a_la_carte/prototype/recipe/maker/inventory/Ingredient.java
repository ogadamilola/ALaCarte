package project.a_la_carte.prototype.recipe.maker.inventory;

public class Ingredient {

    private String name;
    private double quantity;
    private enum Measurement{
        oz,
        count
    }
    private Measurement measurement;

    boolean commonAllergen;

    //example Ingredient("Burger Buns","50", Ingredient.measurement.count,true)
    // 50 individual burger buns
    public Ingredient(String name, double quantity,Measurement measurement){
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
        //default not allergen
        this.commonAllergen = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void setCommonAllergen(boolean commonAllergen) {
        this.commonAllergen = commonAllergen;
    }

}
