package project.a_la_carte.version2.menuItems;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.MenuItemModelSubscriber;

import java.util.List;

/**
 * The display for the lists of MenuItems in the database
 */
public class MenuItemListView extends StackPane implements MenuItemModelSubscriber {
    MenuItemModel menuItemModel;
    VBox menuItemListVBox;
    VBox recipeListVBox;
    HBox modifyButtons;
    Label selectedTitle;
    TextArea menuItemDescription;
    TextField menuItemPrice;
    Button mainMenu;
    Button createNewButton;
    Button deleteButton;
    Button editButton;

    public MenuItemListView(){
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);

        Label title = new Label("Menu Item List");
        title.setFont(new Font(20));
        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(titleHBox, Priority.ALWAYS);

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox();
        menuHBox.setPrefWidth(200);
        menuHBox.getChildren().add(mainMenu);
        //HBox.setHgrow(menuHBox, Priority.ALWAYS);

        HBox topAlign = new HBox(menuHBox, titleHBox);
        topAlign.setPrefWidth(1000);
        topAlign.setStyle("-fx-border-color: black;\n");
        topAlign.setPadding(new Insets(5));
        HBox.setHgrow(topAlign,Priority.ALWAYS);

        Label menuTitle = new Label("Menu Items");
        this.menuItemListVBox = new VBox();
        this.menuItemListVBox.setPrefSize(270,500);;
        this.menuItemListVBox.setPadding(new Insets(5));
        this.menuItemListVBox.setSpacing(2);
        ScrollPane menuItemScroll = new ScrollPane(menuItemListVBox);
        menuItemScroll.setPrefSize(300,500);
        menuItemScroll.setStyle("-fx-border-color: black;\n");
        menuItemScroll.prefHeightProperty().bind(this.heightProperty());

        VBox left = new VBox(menuTitle,menuItemScroll);
        left.setPrefSize(300,500);
        left.setPadding(new Insets(5));
        VBox.setVgrow(left,Priority.ALWAYS);

        this.selectedTitle = new Label("Selected Item: Select Item");

        Label descLabel = new Label("Description: ");
        descLabel.setFont(new Font(20));
        this.menuItemDescription = new TextArea();
        this.menuItemDescription.setPrefSize(400,300);
        this.menuItemDescription.setEditable(false);
        this.menuItemDescription.setStyle("-fx-border-color: black;\n");
        menuItemDescription.setWrapText(true);
        menuItemDescription.prefHeightProperty().bind(this.heightProperty());
        menuItemDescription.prefWidthProperty().bind(this.widthProperty());

        VBox descVBox = new VBox(descLabel,menuItemDescription);
        descVBox.setPrefSize(400,300);
        descVBox.setPadding(new Insets(5));
        VBox.setVgrow(descVBox,Priority.ALWAYS);
        HBox.setHgrow(descVBox, Priority.ALWAYS);

        Label priceLabel = new Label("Price: ");
        priceLabel.setFont(new Font(20));
        this.menuItemPrice = new TextField();
        menuItemPrice.setEditable(false);
        this.menuItemPrice.setPrefWidth(400);
        this.menuItemPrice.setStyle("-fx-border-color: black;\n");
        menuItemPrice.prefWidthProperty().bind(this.widthProperty());

        VBox priceHBox = new VBox(priceLabel,menuItemPrice);
        priceHBox.setPrefWidth(400);
        priceHBox.setPadding(new Insets(5));
        HBox.setHgrow(priceHBox,Priority.ALWAYS);

        this.editButton = new Button("Edit Recipe");
        this.deleteButton = new Button("Delete Recipe");
        modifyButtons = new HBox();
        modifyButtons.setPrefWidth(400);
        modifyButtons.setSpacing(3);
        modifyButtons.setAlignment(Pos.BOTTOM_CENTER);
        modifyButtons.setPadding(new Insets(2));

        VBox informationVBox = new VBox(descVBox,priceHBox, modifyButtons);
        informationVBox.setPrefSize(400,500);
        informationVBox.setStyle("-fx-border-color: black;\n");
        VBox.setVgrow(informationVBox,Priority.ALWAYS);
        VBox center = new VBox(selectedTitle,informationVBox);
        center.setPrefSize(400,500);
        center.setPadding(new Insets(5));
        VBox.setVgrow(center,Priority.ALWAYS);
        HBox.setHgrow(center, Priority.ALWAYS);

        Label recipeLabel = new Label("Recipes In Menu Item");
        this.recipeListVBox = new VBox();
        this.recipeListVBox.setPrefSize(270,400);
        this.recipeListVBox.setPadding(new Insets(5));
        this.recipeListVBox.setSpacing(5);

        ScrollPane recipeScroll = new ScrollPane(recipeListVBox);
        recipeScroll.setPrefSize(300,400);
        recipeScroll.setStyle("-fx-border-color: black;\n");
        recipeScroll.prefHeightProperty().bind(this.heightProperty());

        this.createNewButton = new Button("Create New Menu Item");

        HBox createBox = new HBox(createNewButton);
        createBox.setPrefWidth(300);
        createBox.setAlignment(Pos.BOTTOM_RIGHT);
        createBox.setPadding(new Insets(5));
        HBox.setHgrow(createBox,Priority.ALWAYS);

        VBox right = new VBox(recipeLabel,recipeScroll,createBox);
        right.setPrefSize(300,500);
        right.setPadding(new Insets(5));
        VBox.setVgrow(right,Priority.ALWAYS);

        HBox alignBody = new HBox(left,center,right);
        alignBody.setPrefSize(1000,500);
        HBox.setHgrow(alignBody,Priority.ALWAYS);
        VBox.setVgrow(alignBody, Priority.ALWAYS);

        VBox alignAll = new VBox(topAlign,alignBody);
        alignAll.setPrefSize(1000,500);
        VBox.setVgrow(alignAll, Priority.ALWAYS);
        this.getChildren().add(alignAll);
    }

    /**
     * Set method for MenuItemListView's MenuItemModel
     */
    public void setMenuItemModel(MenuItemModel newModel){
        this.menuItemModel = newModel;
    }

    /**
     * Connecting MenuItemListView to the Program's controller
     */
    public void setController(ProgramController controller){
        this.createNewButton.setOnAction(controller::openMenuMakerView);
        this.editButton.setOnAction(controller::editMenuMakerView);
        this.mainMenu.setOnAction(controller::openManagerMainView);
        this.deleteButton.setOnAction(controller::deleteMenuItem);
    }

    @Override
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList) {
        menuItemListVBox.getChildren().clear();
        recipeListVBox.getChildren().clear();
        modifyButtons.getChildren().clear();

        if (menuItemModel.getMenuItemsList() != null){
            menuItemModel.getMenuItemsList().forEach((item ->{
                item.getButton().setText(item.getName());
                item.getButton().setOnAction((event -> {
                    menuItemModel.selectMenuItem(item);
                }));
                item.getButton().setPrefSize(300,25);
                menuItemListVBox.getChildren().add(item.getButton());
            }));
        }
        if (menuItemModel.getSelectedItem() != null){
            this.selectedTitle.setText("Selected Item: "+ menuItemModel.getSelectedItem().getName());
            this.menuItemDescription.setText(menuItemModel.getSelectedItem().getDescription());
            this.menuItemPrice.setText(String.valueOf(menuItemModel.getSelectedItem().getPrice()));
            this.modifyButtons.getChildren().addAll(editButton,deleteButton);

            if (menuItemModel.selectedItem.getMenuItemRecipes() != null) {
                menuItemModel.selectedItem.getMenuItemRecipes().forEach((recipe -> {
                    Label newLabel = new Label(recipe.getName());
                    this.recipeListVBox.getChildren().add(newLabel);
                }));
            }
        }
        else{
            this.selectedTitle.setText("Selected Item: Select Item");
            this.menuItemDescription.setText("");
            this.menuItemPrice.setText("");
        }
    }
}
