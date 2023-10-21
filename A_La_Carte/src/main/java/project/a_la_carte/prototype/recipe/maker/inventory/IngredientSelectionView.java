package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 * Wndow for when "Add ingredient button is selected in Recipie maker model"
 */
public class IngredientSelectionView extends StackPane implements InventorySubscriber {
    TextArea selectedIngredient;
    MenuBar menuBar;
    Menu[] typeMenus;

    public IngredientSelectionView(){
        VBox container = new VBox();
        this.setPrefSize(200,300);


        Label titleLabel = new Label("Select Ingredient");
        menuBar = new MenuBar();
    }

    public void modelChanged(Map<Ingredient, Double> ingredientInventory) {
        //for each ingredient type make new menu
        for(Ingredient.IngredientType type : Ingredient.IngredientType.values()){
            Menu typeMenu = new Menu(type.getName());
            //for each ingredient with the same type make it a menu item
            for(Map.Entry<Ingredient, Double> entry :ingredientInventory.entrySet() ){
                if(entry.getKey().getIngredientType() == type){
                    MenuItem menuItem = new MenuItem(entry.getKey().getName());

                    typeMenu.getItems().add(menuItem);
                }
            }
            menuBar.getMenus().add(typeMenu);
        }
    }


}
