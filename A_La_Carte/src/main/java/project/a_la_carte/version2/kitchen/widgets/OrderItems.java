package project.a_la_carte.version2.kitchen.widgets;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.classesObjects.OrderTimers;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.managerSide.recipe.ShowRecipeInfoView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;


public class OrderItems extends HBox {
    OrderKitchenTab oTab;
    OrderTimers currentOrder;
    Label totalTimeElapsedLabel;
    ArrayList<Label> recipesLabels;
    ArrayList<ArrayList<Label>> recipesLabelsList;
    int numMenuItems;

    public OrderItems(OrderKitchenTab orderKitchenTab, Order order){
        this.currentOrder = new OrderTimers(order);

        oTab = orderKitchenTab;

        this.setPrefSize(600,70);
        this.setPadding(new Insets(5));

        VBox stringDisplay = new VBox();

        stringDisplay.setSpacing(3);
        stringDisplay.setPrefSize(600,70);

        stringDisplay.setStyle(currentOrder.getBackgroundColor());

        totalTimeElapsedLabel = new Label("Total Time: " + currentOrder.getTotalTimeElapsedStopWatch().getElapsedTimeFormatted());
        stringDisplay.getChildren().add(totalTimeElapsedLabel);

        numMenuItems = currentOrder.getMenuItems().size();
        recipesLabelsList = new ArrayList<>();

        //currentOrder.getMenuItems().forEach((item -> {
        for (int i = 0; i < numMenuItems; i++) {

            MenuFoodItem currentItem = currentOrder.getMenuItems().get(i);

            // Show menuItem name
            Label nameLabel = new Label(currentOrder.getMenuItems().get(i).getName());
            if (currentItem.isCustomized()){
                Label customize = new Label("   "+ currentItem.getCustomize());
                stringDisplay.getChildren().addAll(nameLabel,customize);
            }
            else {
                stringDisplay.getChildren().add(nameLabel);
            }

            // Show recipeName + Time xx:xx // xx:xx Button Button Button
            int numRecipes = currentItem.getMenuItemRecipes().size();
            recipesLabels = new ArrayList<>();

            for (int j = 0; j < numRecipes; j++) {
                Button complete = new Button("Complete Recipe");
                Button addOne = new Button("Add 1 Min.");
                Button viewButton = new Button("View Recipe");

                HBox buttonsBox = new HBox(addOne, complete, viewButton);
                buttonsBox.setPrefWidth(300);
                buttonsBox.setSpacing(10);
                buttonsBox.setAlignment(Pos.BASELINE_RIGHT);

                Label newL = new Label(("Recipe "
                        + currentOrder.getMenuItems().get(i).getMenuItemRecipes().get(j).getName() + " TIME "
                        // + currentOrder.getRecipeStopWatchList().get(i).get(j).getElapsedTimeFormatted()
                        + " // " + currentOrder.getPrepTimesList().get(i).get(j) + ":00"));
                        // " // " + currentOrder.getPrepTimesList().get(i).get(j) + ":00");

                recipesLabels.add(newL);

                HBox labelBox = new HBox(recipesLabels.get(j));

                labelBox.setPrefWidth(300);

                HBox addBox = new HBox(labelBox, buttonsBox);
                addBox.setPrefWidth(600);

                int val = j;
                complete.setOnMouseClicked(event -> {
                    labelBox.getChildren().clear();
                    Label completeL = new Label("Recipe: "+ currentItem.getMenuItemRecipes().get(val).getName() + " COMPLETED");
                    currentItem.getMenuItemRecipes().get(val).setFinished();
                    addBox.getChildren().remove(buttonsBox);
                    labelBox.getChildren().add(completeL);

                    // *** figure this out
                    //recipeStopWatches.get(val).stop(); // Randall line --> Not sure how this while affect my changes - Evan
                    if (!isNotFinished()){
                        orderKitchenTab.orderItems.deleteItem(currentItem);
                    }
                });
                int val2 = i;
                addOne.setOnMouseClicked(event -> {
                    currentOrder.addOneExecute(val2, val);
                });
                stringDisplay.getChildren().add(addBox);
                viewButton.setOnAction(event ->{
                    showRecipeInfo(currentItem.getMenuItemRecipes().get(val));
                });
            }
            recipesLabelsList.add(recipesLabels);
        }


        // This is in the wrong location.
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // below -- not sure, this is ever false... NeverMind?
                        if (currentOrder.getTotalTimeElapsedStopWatch().is_watch_Running()) {
                            totalTimeElapsedLabel.setText("Total Time: " + currentOrder.getTotalTimeElapsedStopWatch().getElapsedTimeFormatted());
                        }
                        stringDisplay.setStyle(currentOrder.getBackgroundColor());

                        // Print Recipe StopWatches
                        for (int i = 0; i < numMenuItems; i++) {
                            int numRecipes = currentOrder.getMenuItems().get(i).getMenuItemRecipes().size();
                            for (int j = 0; j < numRecipes; j++) {
                                recipesLabelsList.get(i).get(j).setText(("Recipe "
                                        + currentOrder.getMenuItems().get(i).getMenuItemRecipes().get(j).getName() + " TIME "
                                        + currentOrder.getRecipeStopWatchList().get(i).get(j).getElapsedTimeFormatted()
                                        + " // " + currentOrder.getPrepTimesList().get(i).get(j) + ":00"));

                            }

                        }
                    }
                });
            }
        },0,250);
        this.getChildren().add(stringDisplay);
    }
    public boolean isNotFinished(){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        currentOrder.getMenuItems().forEach(item -> {
            item.getMenuItemRecipes().forEach(recipe -> {
                if (!recipe.getFinished()){
                    check.set(true);
                }
            });
        });
        return check.get();
    }

    public void showRecipeInfo(Recipe recipe){
        ShowRecipeInfoView infoView = new ShowRecipeInfoView(recipe);
    }
}
