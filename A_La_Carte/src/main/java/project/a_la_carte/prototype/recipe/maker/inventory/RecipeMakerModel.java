package project.a_la_carte.prototype.recipe.maker.inventory;

public class RecipeMakerModel {
    RecipeMakerView recipeMakerView;
    /**
     * The model will send the variables to the Recipe class
     */
    String name;
    String description;
    String prepInstruction;
    float price;
    float prepTime;
    public RecipeMakerModel(){

    }
    public void setRecipeMakerView(RecipeMakerView newView){
        this.recipeMakerView = newView;
    }
}
