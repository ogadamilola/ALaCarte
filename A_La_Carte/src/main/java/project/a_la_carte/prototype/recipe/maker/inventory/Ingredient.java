package project.a_la_carte.prototype.recipe.maker.inventory;

public class Ingredient {

    private String name;
    private double quantity;

    private enum Measurement{
        //changed from oz to pounds, makes more sense for inventory
        //enum is overkill for 2 options but it'll be easier to add more
        pounds("Pounds"),count("Count");
        private String name;
        private Measurement(String name){
            this.name =name;
        }
        public String getName(){
            return name;
        }
    }
    private Measurement measurement;

    boolean commonAllergen;

    /**
     *
     * @param name of ingredient
     * @param quantity starting quantity
     */
    public Ingredient(String name, double quantity){
        this.name = name;
        this.quantity = quantity;

        this.measurement = Measurement.count;
        //default to not allergen
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
    public Measurement stringToMeasurement(String measurement) {
        switch(measurement){
            case "Pounds":
                return Measurement.pounds;
            case "Count":
                return Measurement.count;
            default:
                return null;
        }
    }
    public String measurementToString(){
        return(this.measurement.toString());
    }

    public void setCommonAllergen(boolean commonAllergen) {
        this.commonAllergen = commonAllergen;
    }

}
