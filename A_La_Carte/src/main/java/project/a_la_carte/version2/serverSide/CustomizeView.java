package project.a_la_carte.version2.serverSide;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.managerSide.inventory.InventoryModel;
import project.a_la_carte.version2.serverSide.widgets.CustomizeButton;
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
    VBox itemsInVBox;
    Label title;
    Button discard;
    Button back;
    Button set;
    ArrayList<CustomizeSelectionButton> customizeSelectionButtonArrayList;
    ArrayList<IngredientsCustomize> ingredientsCustomizeArrayList;
    ArrayList<CustomizeButton> customizeButtons;
    String selectedIngredient = "";
    String selectedOption="";
    public CustomizeView(WorkerView view){
        this.workerView = view;
        customizeSelectionButtonArrayList = new ArrayList<>();
        ingredientsCustomizeArrayList = new ArrayList<>();
        customizeButtons = new ArrayList<>();

        this.setMaxSize(5000,2500);
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

        this.discard = new Button("DISCARD");
        this.discard.setPrefHeight(100);
        this.discard.setFont(new Font(15));
        this.discard.setPrefWidth(250);
        this.set = new Button("SET");
        this.set.setPrefHeight(100);
        this.set.setFont(new Font(15));
        this.set.setPrefWidth(250);

        HBox botButtons = new HBox(discard,set);
        botButtons.setPrefSize(600,100);
        botButtons.setPadding(new Insets(5));
        botButtons.setAlignment(Pos.BOTTOM_CENTER);
        botButtons.setSpacing(15);
        HBox.setHgrow(botButtons,Priority.ALWAYS);

        this.set.prefWidthProperty().bind(botButtons.widthProperty());
        this.discard.prefWidthProperty().bind(botButtons.widthProperty());

        this.ingredients = new FlowPane();
        this.ingredients.setPrefSize(600,500);
        this.ingredients.setPadding(new Insets(5));
        ingredients.prefWidthProperty().bind(this.widthProperty());
        ingredients.prefHeightProperty().bind(this.heightProperty());

        HBox ingredientsAlign = new HBox(ingredients);
        ingredientsAlign.setPrefSize(600,500);
        ingredientsAlign.setStyle("-fx-border-color: black;\n");
        VBox.setVgrow(ingredientsAlign,Priority.ALWAYS);
        HBox.setHgrow(ingredientsAlign,Priority.ALWAYS);

        VBox alignRight = new VBox(ingredientsAlign,botButtons);
        alignRight.setPrefSize(600,500);
        //VBox.setVgrow(alignRight,Priority.ALWAYS);
        HBox.setHgrow(alignRight,Priority.ALWAYS);

        Label itemTitle = new Label("SELECT AN ITEM");
        itemsInVBox = new VBox();
        itemsInVBox.setPrefSize(180,500);
        itemsInVBox.setSpacing(3);
        itemsInVBox.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(itemsInVBox,Priority.ALWAYS);

        ScrollPane itemsScroll = new ScrollPane(itemsInVBox);
        itemsScroll.setPrefSize(200,500);
        itemsScroll.prefHeightProperty().bind(this.heightProperty());

        VBox combineBox = new VBox(itemTitle,itemsScroll);
        combineBox.setPrefSize(200,500);
        VBox.setVgrow(combineBox, Priority.ALWAYS);

        HBox alignBody = new HBox(optionsVBox, alignRight, combineBox);
        VBox.setVgrow(alignBody,Priority.ALWAYS);
        HBox.setHgrow(alignBody,Priority.ALWAYS);
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
        this.set.setOnAction(event -> {
            controller.addCustomize(this.workerView);
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
    public Boolean containsItemCus(String foodName){
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
        this.itemsInVBox.getChildren().clear();

        if (workerView.getMenuView().currentOrder != null){
            workerView.getMenuView().currentOrder.getOrderList().forEach(items -> {
                CustomizeButton newB = new CustomizeButton(items.getName());
                items.getCustomize().forEach(newB::addEdit);
                newB.getSelect().setOnAction(event -> {
                    serverModel.setSelectedCustomizeItem(items);
                });
                itemsInVBox.getChildren().add(newB);
            });
        }
        if (this.serverModel.getSelectedCustomizeItem() != null){
            title.setText("Selected Menu Item: " + serverModel.getSelectedCustomizeItem().getName());
            for(Recipe recipe : this.serverModel.getSelectedCustomizeItem().getMenuItemRecipes()){
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
