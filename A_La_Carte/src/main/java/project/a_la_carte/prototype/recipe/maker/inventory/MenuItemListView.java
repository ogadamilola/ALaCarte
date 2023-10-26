package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

import java.util.List;

public class MenuItemListView extends StackPane implements MenuItemModelSubscriber {
    // This is a comment please delete
    MenuItemModel menuItemModel;
    VBox menuItemListVBox;
    Label menuItemName;
    Label menuItemDescription;
    Label menuItemPrepI;
    Label menuItemPrepT;
    Button mainMenu;
    Button createNewButton;
    Button deleteButton;
    Button editIngredients;

    public MenuItemListView(){
        this.setPrefSize(1000,500);

        Label title = new Label("Menu Item List");
        title.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox();
        menuHBox.getChildren().add(mainMenu);
    }
    /*
    //Commented out for so application can compile

    public void setController(ProgramController controller){
        this.createNewButton.setOnAction(controller::openMenuItemMakerScreen);
        this.editIngredients.setOnAction(controller::openMenuItemMakerScreen);
        this.mainMenu.setOnAction(controller::openStartUpMVC);
    }

     */
    @Override
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList) {
        for(MenuFoodItem menuItem : menuItemList){
            MenuItemWidget widget = new MenuItemWidget(menuItem);
            menuItemListVBox.getChildren().add(widget.getWidget());
        }
    }
}
