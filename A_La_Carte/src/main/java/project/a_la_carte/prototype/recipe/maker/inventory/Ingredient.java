package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.Objects;

public class Ingredient {

    private String name;

    public enum IngredientType{
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

public String measurementToString(){
        return(this.ingredientType.toString());
    }

    public void setCommonAllergen(boolean commonAllergen) {
        this.commonAllergen = commonAllergen;
    }
}

