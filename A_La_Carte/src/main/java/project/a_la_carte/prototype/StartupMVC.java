package project.a_la_carte.prototype;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.recipe.maker.inventory.*;

/**
 * StartupMVC will be used as a Scene and will merge all MVC classes together
 * Can also work as the main startup screen
 */
public class StartupMVC extends StackPane {
    StackPane startUpView;
    InventoryView inventoryView;
    RecipeMakerView recipeMakerView;
    RecipeListView recipeListView;
    Button inventoryButton;
    Button recipeListButton;
    boolean startUp = true;
    boolean inventory = false;
    boolean recipeMaker = false;
    boolean recipeList = false;
    public StartupMVC(){
        this.setMaxSize(1000,500);
        ProgramController programController = new ProgramController();

        InventoryModel inventoryModel = new InventoryModel();
        inventoryView = new InventoryView();

        inventoryView.setController(programController);
        inventoryView.setModel(inventoryModel);

        inventoryModel.setView(inventoryView);

        programController.setInventoryModel(inventoryModel);

        recipeMakerView = new RecipeMakerView();
        RecipeMakerModel recipeMakerModel = new RecipeMakerModel();

        recipeMakerView.setRecipeMakerModel(recipeMakerModel);
        recipeMakerView.setRecipeMakerController(programController);

        recipeMakerModel.setRecipeMakerView(recipeMakerView);

        programController.setRecipeMakerModel(recipeMakerModel);

        recipeListView = new RecipeListView();
        RecipeListModel recipeListModel = new RecipeListModel();

        recipeListView.setRecipeListModel(recipeListModel);
        recipeListView.setController(programController);

        recipeListModel.setRecipeListView(recipeListView);

        programController.setRecipeListModel(recipeListModel);

        startUpView = new StackPane();
        startUpView.setMaxSize(1000,500);
        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));

        inventoryButton = new Button("Go To Inventory");
        inventoryButton.setFont(new Font(30));
        recipeListButton = new Button("Go To Recipe List");
        recipeListButton.setFont(new Font(30));

        VBox startUpAlign = new VBox(welcomeLabel,inventoryButton,recipeListButton);
        startUpAlign.setPrefSize(1000,500);
        startUpAlign.setPadding(new Insets(20,20,20,20));
        startUpAlign.setAlignment(Pos.CENTER);
        startUpView.getChildren().add(startUpAlign);

        programController.setStartupMVC(this);
        this.setController(programController);
        this.getChildren().add(startUpView);
        //this.getChildren().add(inventoryView);
        //To see the inventory just get rid of the comment
    }
    public void setController(ProgramController controller){
        inventoryButton.setOnAction(controller::openInventoryScreen);
        recipeListButton.setOnAction(controller::openRecipeList);
    }
    public void selectStartup(){
        this.startUp = true;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
    }
    public void selectInventory(){
        this.startUp = false;
        this.inventory = true;
        this.recipeMaker = false;
        this.recipeList = false;
    }
    public void selectRecipeList(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = true;
    }
    public void selectRecipeMaker(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = true;
        this.recipeList = false;
    }
    //Decided to do this for now since multiple screens is not in our todo for the prototype
    public void modelChanged() {
        this.getChildren().clear();
        if (startUp){
            this.getChildren().add(startUpView);
        }
        else if (recipeMaker) {
            this.getChildren().add(recipeMakerView);
        }
        else if (inventory) {
            this.getChildren().add(inventoryView);
        }
        else if(recipeList){
            this.getChildren().add(recipeListView);
        }
    }
}
