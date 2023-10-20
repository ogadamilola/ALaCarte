package project.a_la_carte.prototype.recipe.maker.inventory;

public class Ingredient {

    private String name;

    enum IngredientType{
        proteins("Proteins"),dairy("Dairy"),grains("Grains"),vegetable("Vegetables"),sauce("Sauce"),other("Other");
        private String name;
        private IngredientType(String name){
            this.name =name;
        }
        public String getName(){
            return name;
        }
    }

    IngredientType ingredientType;

    boolean commonAllergen;

    /**
     *
     * @param name of ingredient
     *
     */
    public Ingredient(String name){
        this.name = name;
        //default to not allergen
        this.commonAllergen = false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }

    public IngredientType stringToIngredientType(String type) {
        switch(type){
            case "Protiens":
                return IngredientType.proteins;
            case "Vegetables":
                return IngredientType.vegetable;
            case "Dairy":
                return IngredientType.dairy;
            default:
                return null;
        }
    }
    public String measurementToString(){
        return(this.ingredientType.toString());
    }

    public void setCommonAllergen(boolean commonAllergen) {
        this.commonAllergen = commonAllergen;
    }
}

