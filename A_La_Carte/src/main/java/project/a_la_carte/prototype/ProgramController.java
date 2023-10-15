package project.a_la_carte.prototype;

import javafx.event.ActionEvent;
import project.a_la_carte.prototype.recipe.maker.inventory.InventoryModel;
import project.a_la_carte.prototype.recipe.maker.inventory.RecipeMakerModel;

public class ProgramController {
    StartupMVC startupMVC;
    InventoryModel inventoryModel;
    RecipeMakerModel recipeMakerModel;
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
    public void openRecipeMakerScreen(ActionEvent event){
        this.startupMVC.selectRecipeMaker();
        this.startupMVC.modelChanged();
    }
    public void openStartUpMVC(ActionEvent event){
        this.startupMVC.selectStartup();
        this.startupMVC.modelChanged();
    }

    /**
     * Here would be Inventory actions
     */
    public void setInventoryModel(InventoryModel newModel){this.inventoryModel = newModel;}

    /**
     * Here would be Recipe actions
     */
    public void setRecipeMakerModel(RecipeMakerModel newModel){
        this.recipeMakerModel = newModel;
    }

}
