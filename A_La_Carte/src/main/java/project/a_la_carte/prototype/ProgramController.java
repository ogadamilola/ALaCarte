package project.a_la_carte.prototype;

import javafx.event.ActionEvent;
import project.a_la_carte.prototype.recipe.maker.inventory.InventoryModel;
import project.a_la_carte.prototype.recipe.maker.inventory.InventoryView;
import project.a_la_carte.prototype.recipe.maker.inventory.RecipeModel;
import project.a_la_carte.prototype.server.side.ServerModel;

public class ProgramController {
    StartupMVC startupMVC;
    InventoryModel inventoryModel;
    RecipeModel recipeModel;
    ServerModel serverModel;

    public ProgramController(){

    }

    /**
     * These would be the action in StartupMVC
     */
    public void setStartupMVC(StartupMVC newModel){
        this.startupMVC = newModel;
    }
    public void openInventoryScreen(ActionEvent event){
        this.startupMVC.selectInventory();
        this.startupMVC.modelChanged();
    }
    public void openStartUpMVC(ActionEvent event){
        this.startupMVC.selectStartup();
        this.startupMVC.modelChanged();
    }
    public void openRecipeList(ActionEvent event){
        this.startupMVC.selectRecipeList();
        this.startupMVC.modelChanged();
    }
    public void openMenuView(ActionEvent event){
        this.startupMVC.selectMenuView();
        this.startupMVC.modelChanged();
    }

    /**
     * Here would be Inventory actions
     */
    public void setInventoryModel(InventoryModel newModel){this.inventoryModel = newModel;}


    /**
     * End of Inventory Actions
     */

    /**
     * Here would be Recipe actions
     */
    public void setRecipeModel(RecipeModel newModel){this.recipeModel = newModel;}
    public void openRecipeMakerScreen(ActionEvent event){
        this.startupMVC.selectRecipeMaker();
        this.startupMVC.modelChanged();
    }

    /**
     * Here would be the Server Actions
     */
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void openNoteView(ActionEvent event){
        this.startupMVC.selectNoteView();
        this.startupMVC.modelChanged();
    }
    public void openCustomizeView(ActionEvent event){
        this.startupMVC.selectCustomize();
        this.startupMVC.modelChanged();
    }
    public void openViewOrder(ActionEvent event){
        this.startupMVC.selectViewOrder();
        this.startupMVC.modelChanged();
    }

}
