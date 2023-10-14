package project.a_la_carte.prototype;

import javafx.scene.layout.StackPane;

/**
 * StartupMVC will be used as a Scene and will merge all MVC classes together
 * Can also work as the main startup screen
 */
public class StartupMVC extends StackPane {
    public StartupMVC(){
        this.setMaxSize(1000,500);

        InventoryModel inventoryModel = new InventoryModel();
        InventoryView inventoryView = new InventoryView();
        InventoryController inventoryController = new InventoryController();

        inventoryView.setController(inventoryController);
        inventoryView.setModel(inventoryModel);

        inventoryModel.setView(inventoryView);

        inventoryController.setModel(inventoryModel);

        this.getChildren().add(inventoryView);
    }
}
