package project.a_la_carte.version2.kitchen.widgets;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.managerSide.recipe.ShowRecipeInfoView;
import project.a_la_carte.version2.classesObjects.StopWatch;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Used to display the MenuItem and it's timers
 */
public class OrderItems extends HBox {
    MenuFoodItem menuFoodItem;
    ArrayList<Float> prepTimes;
    ArrayList<Label> recipesTimeElapsedLabel;
    ArrayList<Long> startTimes;
    int numberOfRecipes;
    StopWatch totalTimeElapsedStopWatch;
    Label totalTimeElapsedLabel;
    ArrayList<StopWatch> recipeStopWatches;
    OrderKitchenTab oTab;
    float expectedOrderTime;
    int recipeCounter;
    public OrderItems(OrderKitchenTab orderKitchenTab, MenuFoodItem item){
        oTab = orderKitchenTab;
        this.menuFoodItem = item;

        this.setPrefSize(600,70);
        this.setPadding(new Insets(5));

        // initialize prepTimes
        numberOfRecipes = menuFoodItem.getMenuItemRecipes().size();
        prepTimes = new ArrayList<>();
        expectedOrderTime = 0;
        menuFoodItem.getMenuItemRecipes().forEach((recipe -> {
            prepTimes.add(recipe.getPrepTime());
            if (recipe.getPrepTime() >= expectedOrderTime) {
                expectedOrderTime = recipe.getPrepTime();
            }
        }));

        // initialize prepTimes
        startTimes = new ArrayList<>();
        prepTimes.forEach(prep -> {
            startTimes.add((long) ((expectedOrderTime - prep) * 60000));
        });

        // Starting a StopWatch for the whole order
        totalTimeElapsedStopWatch = new StopWatch();
        totalTimeElapsedStopWatch.start();
        totalTimeElapsedLabel = new Label("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());

        // Creating an array of un-started StopWatches
        recipeStopWatches = new ArrayList<>();
        for (int i = 0; i < numberOfRecipes; i++) {
            StopWatch newStop = new StopWatch();
            recipeStopWatches.add(newStop);
        }
        Label nameLabel = new Label(menuFoodItem.getName());

        recipesTimeElapsedLabel = new ArrayList<>();
        for (int i = 0; i < numberOfRecipes; i++) {
            Label newL = new Label("Recipe "+ menuFoodItem.getMenuItemRecipes().get(i).getName() + " TIME " + " // " + prepTimes.get(i) + ":00");
            recipesTimeElapsedLabel.add(newL);
        }

        VBox stringDisplay = new VBox();
        stringDisplay.setStyle("-fx-background-color: cornflowerblue;\n");
        stringDisplay.setSpacing(3);
        stringDisplay.setPrefSize(600,70);

        if (menuFoodItem.isCustomized()){
            Label customize = new Label("   "+ menuFoodItem.getCustomize());
            stringDisplay.getChildren().addAll(nameLabel,customize);
        }
        else {
            stringDisplay.getChildren().add(nameLabel);
        }
        stringDisplay.getChildren().add(totalTimeElapsedLabel);

        recipeCounter = numberOfRecipes;
        for (int i = 0; i < numberOfRecipes; i++) {
            Button complete = new Button("Complete Recipe");
            Button addOne = new Button("Add 1 Min.");
            Button viewButton = new Button("View Recipe");

            HBox buttonsBox = new HBox(addOne, complete, viewButton);
            buttonsBox.setPrefWidth(300);
            buttonsBox.setSpacing(10);
            buttonsBox.setAlignment(Pos.BASELINE_RIGHT);

            HBox labelBox = new HBox(recipesTimeElapsedLabel.get(i));
            labelBox.setPrefWidth(300);

            HBox addBox = new HBox(labelBox, buttonsBox);
            addBox.setPrefWidth(600);

            int val = i;
            complete.setOnMouseClicked(event -> {
                labelBox.getChildren().clear();
                Label completeL = new Label("Recipe: "+ menuFoodItem.getMenuItemRecipes().get(val).getName() + " COMPLETED");
                menuFoodItem.getMenuItemRecipes().get(val).setFinished();
                recipeCounter -= 1;

                addBox.getChildren().remove(buttonsBox);
                labelBox.getChildren().add(completeL);

                //If there is no longer and recipes
                if ((recipeCounter == 0)){
                    orderKitchenTab.orderItems.deleteItem(this.menuFoodItem);
                    orderKitchenTab.removeItem(this);
                }
                if (orderKitchenTab.orderItems.getOrderList().isEmpty()){
                    orderKitchenTab.orderItems.orderFinished();
                }
            });
            addOne.setOnMouseClicked(event -> {
                // increase prepTime by 1 minute
                prepTimes.set(val,prepTimes.get(val) + 1);
                // Added by evan
                // increase all start times by 1 minute
                for (int j = 0; j < numberOfRecipes; j++) {
                    startTimes.set(j, startTimes.get(j) + 60000);
                }
                // update expectedOrderTime
                expectedOrderTime += 1;
            });
            stringDisplay.getChildren().add(addBox);
            viewButton.setOnAction(event ->{
                showRecipeInfo(menuFoodItem.getMenuItemRecipes().get(val));
            });
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (totalTimeElapsedStopWatch.is_watch_Running()){
                            totalTimeElapsedLabel.setText("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());
                        }
                        // Start StopWatch when correct time has passed
                        for (int i = 0; i < recipesTimeElapsedLabel.size(); i++) {
                            if ((totalTimeElapsedStopWatch.getElapsedTime() >= startTimes.get(i)) && (!recipeStopWatches.get(i).is_watch_Running()) && (!recipeStopWatches.get(i).is_watch_finished_Running())) {
                                recipeStopWatches.get(i).syncNewStopWatch(totalTimeElapsedStopWatch, startTimes.get(i));
                            }
                        }
                        // Print Recipe StopWatches
                        for (int i = 0; i < numberOfRecipes ; i++) {
                            recipesTimeElapsedLabel.get(i).setText(("Recipe " + menuFoodItem.getMenuItemRecipes().get(i).getName() + " TIME "
                                + recipeStopWatches.get(i).getElapsedTimeFormatted() + " // " + prepTimes.get(i) + "0"));

                            // Change Text color to red if recipe is late
                            // and yellow for a two-minute warning
                            if (recipeStopWatches.get(i).is_watch_Running()) {
                                if (totalTimeElapsedStopWatch.getElapsedTime() >= (expectedOrderTime * 60000L)) {
                                    stringDisplay.setStyle("-fx-background-color: red;\n");
                                } else if (totalTimeElapsedStopWatch.getElapsedTime() >= ((expectedOrderTime - 2) * 60000L)) {
                                    stringDisplay.setStyle("-fx-background-color: orange;\n");
                                }
                            }
                        }
                    }
                });
            }
        },0,1000);
        this.getChildren().add(stringDisplay);
    }
    public MenuFoodItem getMenuFoodItem(){
        return this.menuFoodItem;
    }
    public void showRecipeInfo(Recipe recipe){
        ShowRecipeInfoView infoView = new ShowRecipeInfoView(recipe);
    }
}
