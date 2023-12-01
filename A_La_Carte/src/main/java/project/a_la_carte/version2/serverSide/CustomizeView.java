package project.a_la_carte.version2.serverSide;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.managerSide.inventory.InventoryModel;
import project.a_la_carte.version2.serverSide.widgets.CustomizeSelectionButton;
import project.a_la_carte.version2.serverSide.widgets.IngredientsCustomize;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The view for adding temporary edits to Menu Item
 */
public class CustomizeView extends StackPane implements ServerViewInterface {
    WorkerView workerView;
    ServerModel serverModel;
    InventoryModel inventoryModel;
    FlowPane ingredients;
    VBox optionsVBox;
    Label title;
    Button discard;
    Button send;
    Button back;
    ArrayList<CustomizeSelectionButton> customizeSelectionButtonArrayList;
    ArrayList<IngredientsCustomize> ingredientsCustomizeArrayList;
    String selectedIngredient = "";
    String selectedOption="";
    public CustomizeView(WorkerView view){
        this.workerView = view;
        customizeSelectionButtonArrayList = new ArrayList<>();
        ingredientsCustomizeArrayList = new ArrayList<>();
        this.setPrefSize(1000,500);

        title = new Label("SELECT ITEM TO CUSTOMIZE");
        title.setFont(new Font(20));

        double r = 2;
        this.back = new Button("<");
        this.back.setShape(new Circle(r));
        this.back.setMinSize(2*r,2*r);
        this.back.setStyle("-fx-border-color: black;-fx-background-color: paleturquoise;\n");

        HBox backHBox = new HBox(back);
        backHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(backHBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setStyle("-fx-border-color: black;\n");
        topHBox.setPadding(new Insets(5));

        optionsVBox = new VBox();
        optionsVBox.setPrefSize(200,500);
        optionsVBox.setPadding(new Insets(5));
        optionsVBox.setSpacing(10);
        optionsVBox.setStyle("-fx-border-color: black;\n");

        this.send = new Button("SEND");
        this.send.setPrefHeight(150);
        this.send.setFont(new Font(15));
        this.discard = new Button("DISCARD");
        this.discard.setPrefHeight(150);
        this.discard.setFont(new Font(15));

        HBox botButtons = new HBox(discard,send);
        botButtons.setPrefSize(800,150);
        botButtons.setPadding(new Insets(5));
        botButtons.setAlignment(Pos.BOTTOM_CENTER);
        botButtons.setSpacing(15);

        this.ingredients = new FlowPane();
        this.ingredients.setPrefSize(800,500);
        this.ingredients.setPadding(new Insets(5));

        HBox ingredientsAlign = new HBox(ingredients);
        ingredientsAlign.setPrefSize(800,500);
        ingredientsAlign.setStyle("-fx-border-color: black;\n");

        VBox alignRight = new VBox(ingredientsAlign,botButtons);
        alignRight.setPrefSize(800,500);

        HBox alignBody = new HBox(optionsVBox, alignRight);

        VBox alignAll = new VBox(topHBox,alignBody);

        this.getChildren().add(alignAll);
    }

    /**
     * Set method for CustomizeView's ServerModel
     */
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }

    /**
     * Set method for CustomizeView's ServerModel
     */
    public void setInventoryModel(InventoryModel inventoryModel) {
        this.inventoryModel = inventoryModel;
    }

    /**
     * Connecting CustomizeView with the Program's controller
     */
    public void setController(ProgramController controller){
        this.back.setOnAction((event -> {
            controller.openMenuView(this.workerView);
        }));
        this.discard.setOnAction(event -> {
            controller.discardSelection(this.workerView);
        });
        this.send.setOnAction(event -> {
            controller.saveCustomize(this.workerView);
        });
    }

    /**
     * Method for checking if IngredientCustomize is already on the CustomizeView
     */
    public Boolean containsIngredientCustomize(String foodName){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        ingredientsCustomizeArrayList.forEach(ingredient -> {
            if (ingredient.getIngredientName().equals(foodName)) {
                check.set(true);
            }
        });
        return check.get();
    }
    /**
     * Method for checking if CustomizeSelectionButton is already on the CustomizeView
     */
    public Boolean containsButtonCustomize(String foodName){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        customizeSelectionButtonArrayList.forEach(button -> {
            if (button.getOptionName().equals(foodName)) {
                check.set(true);
            }
        });
        return check.get();
    }

    /**
     * Get method for the selected ingredient
     */
    public String getSelectedIngredient(){
        return this.selectedIngredient;
    }

    /**
     * Method for updating the display of CustomizeView objects
     */
    public void selectIngredient(String name) {
        this.selectedIngredient = name;

        ingredientsCustomizeArrayList.forEach((ingredient -> {
            if (ingredient.getIngredientName().equals(this.selectedIngredient)) {
                ingredient.select();
            } else {
                ingredient.unselect();
            }
        }));
        modelChanged();
    }

    /**
     * Get method for the selected option
     */
    public String getSelectedOption(){
        return this.selectedOption;
    }

    /**
     * Method for updating the display of CustomizeView objects
     */
    public void selectOption(String name){
        this.selectedOption = name;
        customizeSelectionButtonArrayList.forEach((option -> {
            if (option.getOptionName().equals(this.selectedOption)){
                option.select();
            }
            else{
                option.unselect();
            }
        }));
        modelChanged();
    }

    @Override
    public void modelChanged() {
        //ingredients.getChildren().clear();
        //optionsVBox.getChildren().clear();

        if (this.workerView.getMenuView().getSelectedItem() != null){
            title.setText("Selected Menu Item: "+ workerView.getMenuView().getSelectedItem().getName());
            for(Recipe recipe : this.workerView.getMenuView().getSelectedItem().getMenuItemRecipes()){
                for(Map.Entry<String,Double> entry : recipe.getRecipeIngredients().entrySet()){
                    serverModel.addIngredient(entry.getKey());
                }
            }

            serverModel.getIngredientList().forEach((recipe ->{
                IngredientsCustomize newCustomize = new IngredientsCustomize(recipe.getIngredientName());
                newCustomize.setOnAction((event -> {
                    this.selectIngredient(newCustomize.getIngredientName());
                }));
                if (!this.containsIngredientCustomize(newCustomize.getIngredientName())) {
                    this.ingredientsCustomizeArrayList.add(newCustomize);

                    ingredients.getChildren().add(newCustomize);
                }
            }));
            serverModel.getCustomizeButtons().forEach((button ->{
                CustomizeSelectionButton newButton = new CustomizeSelectionButton(button.getOptionName());
                newButton.setOnAction((event -> {
                    this.selectOption(button.getOptionName());
                }));
                if (!this.containsButtonCustomize(newButton.getOptionName())) {
                    this.customizeSelectionButtonArrayList.add(newButton);
                    optionsVBox.getChildren().add(newButton);
                }
            }));
        }
    }
}
