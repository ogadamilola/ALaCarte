package project.a_la_carte.version2.kitchen.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.timerSystemPrototype.StopWatch;

import java.util.ArrayList;



//Over here would be the changing of order time on kitchen side <-- don't need this yet, not in proto todo
//Also the prep timer
public class OrderItems extends HBox {
    Button complete;
    Button viewRecipe;
    Button addOneMin;
    MenuFoodItem menuFoodItem;
    private ArrayList<Float> prepTimes;
    private ArrayList<Long> startTimes;
    private float expectedOrderTime = 0;
    private ArrayList<Label> recipesTimeElapsedLabel;
    private int numberOfRecipes;
    private StopWatch totalTimeElapsedStopWatch;
    private Label totalTimeElapsedLabel;
    private ArrayList<StopWatch> recipeStopWatches;

    public OrderItems(MenuFoodItem item){
        this.menuFoodItem = item;
        complete = new Button("Complete");
        viewRecipe = new Button("Recipe");
        addOneMin = new Button("Add an Extra Minute");

        VBox buttonBox = new VBox(viewRecipe, complete);
        buttonBox.setPrefSize(250,70);
        buttonBox.setPadding(new Insets(5));
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        this.setPrefSize(700,70);
        this.setPadding(new Insets(5));
        this.setStyle("-fx-border-color: black;-fx-background-color: cornflowerblue;\n");

        // initialize prepTimes
        numberOfRecipes = menuFoodItem.getMenuItemRecipes().size();
        prepTimes = new ArrayList<>(numberOfRecipes);
        menuFoodItem.getMenuItemRecipes().forEach((recipe -> {
            prepTimes.add(recipe.getPrepTime());
        }));

        expectedOrderTime = menuFoodItem.getPrepTime();

        // initialize prepTimes
        startTimes = new ArrayList<>(numberOfRecipes);
        prepTimes.forEach((prepTime -> {
            startTimes.add((long) (expectedOrderTime - prepTime) * 60000);
        }));

        // Starting a StopWatch for the whole order
        totalTimeElapsedStopWatch = new StopWatch();
        totalTimeElapsedStopWatch.start();
        totalTimeElapsedLabel = new Label("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());

        // Creating an array of un-started StopWatches
        recipeStopWatches = new ArrayList<>(numberOfRecipes);
        for (int i = 0; i < numberOfRecipes; i++) {
            recipeStopWatches.add(new StopWatch());
        }

        Label nameLabel = new Label(menuFoodItem.getName());
        Label prepLabel = new Label(prepTimes.toString());
        Label startLabel = new Label(startTimes.toString());

        recipesTimeElapsedLabel = new ArrayList<>(numberOfRecipes);
        for (int i = 0; i < numberOfRecipes; i++) {
            recipesTimeElapsedLabel.add(new Label("Recipe "+ menuFoodItem.getMenuItemRecipes().get(i).getName() + " TIME " + " // " + prepTimes.get(i) + ":00"));
        }

        VBox stringDisplay = new VBox();
        stringDisplay.setSpacing(3);
        stringDisplay.setPrefSize(800,70);

        if (menuFoodItem.isCustomized()){
            Label customize = new Label("   "+ menuFoodItem.getCustomize());
            stringDisplay.getChildren().addAll(nameLabel,customize);
        }
        else {
            stringDisplay.getChildren().add(nameLabel);
        }

        stringDisplay.getChildren().add(prepLabel);
        stringDisplay.getChildren().add(startLabel);
        stringDisplay.getChildren().add(totalTimeElapsedLabel);

        for (int i = 0; i < numberOfRecipes; i++) {
            stringDisplay.getChildren().add(recipesTimeElapsedLabel.get(i));
        }


        this.getChildren().addAll(stringDisplay,buttonBox);

//        // This is in the wrong location.
//        ActionListener listener = new TimerListener();
//
//        final int DELAY = 1000; // Milliseconds between timer ticks
//        Timer t = new Timer(DELAY, listener);
//        t.start();
    }

    // This might be in the wrong location.
//    class TimerListener implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//            // Print the Order StopWatch
//            totalTimeElapsedLabel.setText("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());
//
//            // Start StopWatch when correct time has passed
//            for (int i = 0; i < numberOfRecipes; i++) {
//                if ((totalTimeElapsedStopWatch.getElapsedTime() >= startTimes.get(i)) && (!recipeStopWatches.get(i).is_watch_Running()) && (!recipeStopWatches.get(i).is_watch_finished_Running())) {
//                    recipeStopWatches.get(i).syncNewStopWatch(totalTimeElapsedStopWatch, startTimes.get(i));
//                }
//            }
//
//            // Print Recipe StopWatches
//            for (int i = 0; i < numberOfRecipes; i++) {
//                recipesTimeElapsedLabel.get(i).setText("Recipe " + i + " TIME " + recipeStopWatches.get(i).getElapsedTimeFormatted() + " // " + prepTimes.get(i) + ":00");
//
//                // Change Text color to red if recipe is late
//                // and yellow for a two-minute warning
//                if (recipeStopWatches.get(i).is_watch_Running()) {
//                    if (totalTimeElapsedStopWatch.getElapsedTime() >= (expectedOrderTime * 60000L)) {
//                        recipesTimeElapsedLabel.get(i).setStyle("-fx-fill: red;\n");
//                    } else if (totalTimeElapsedStopWatch.getElapsedTime() >= ((expectedOrderTime - 2) * 60000L)) {
//                        recipesTimeElapsedLabel.get(i).setStyle("-fx-fill: orange;\n");
//                    }
//                }
//            }
//        }
//    }
    public MenuFoodItem getMenuFoodItem(){
        return this.menuFoodItem;
    }
    public Button getCompleteButton(){
        return this.complete;
    }
    public Button getViewRecipeButton(){
        return this.viewRecipe;
    }

}
