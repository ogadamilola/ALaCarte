package project.a_la_carte.version2.classesObjects;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OrderTimers {
    ArrayList<MenuFoodItem> menuItems;
    int orderNum;

    // ---------------------------
    // Migrated from OrderItems
    // ---------------------------
    ArrayList<Float> prepTimes;
    ArrayList<ArrayList<Float>> prepTimesList;
    ArrayList<Long> startTimes;
    ArrayList<ArrayList<Long>> startTimesList;
    StopWatch totalTimeElapsedStopWatch;
    ArrayList<StopWatch> recipeStopWatches;
    float expectedOrderTime;
    String backgroundColor;


    public OrderTimers(Order realOrder){
        this.menuItems = realOrder.menuItems;
        this.orderNum = realOrder.getOrderNum();

        // New stuff starts here
        backgroundColor = "-fx-background-color: cornflowerblue;\n";

        // initialize prepTimes
        prepTimesList = new ArrayList<>();
        menuItems.forEach((item -> {
            prepTimes = new ArrayList<>();
            item.getMenuItemRecipes().forEach((recipe -> {
                prepTimes.add(recipe.getPrepTime());
                if (recipe.getPrepTime() >= expectedOrderTime) {
                    expectedOrderTime = recipe.getPrepTime();
                }
            }));
            prepTimesList.add(prepTimes);
        }));

        // initialize startTimes
        startTimesList = new ArrayList<>();
        prepTimesList.forEach((list -> {
            startTimes = new ArrayList<>();
            list.forEach(prep -> {
                startTimes.add((long) ((expectedOrderTime - prep) * 60000));
            });
            startTimesList.add(startTimes);
        }));

        // Starting a StopWatch for the whole order
        totalTimeElapsedStopWatch = new StopWatch();
        totalTimeElapsedStopWatch.start();

        // Creating an array of un-started StopWatches
        recipeStopWatches = new ArrayList<>();

        menuItems.forEach((item -> {
            item.getMenuItemRecipes().forEach((recipe -> {
                StopWatch newStop = new StopWatch();
                recipeStopWatches.add(newStop);
            }));
        }));

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        menuItems.forEach((item -> {
                            for (int i = 0; i < item.getMenuItemRecipes().size(); i++) {
                                // Start StopWatch When it is passed it's startTime
                                if ((totalTimeElapsedStopWatch.getElapsedTime() >= startTimes.get(i)) && (!recipeStopWatches.get(i).is_watch_Running()) && (!recipeStopWatches.get(i).is_watch_finished_Running())) {
                                    recipeStopWatches.get(i).syncNewStopWatch(totalTimeElapsedStopWatch, startTimes.get(i));
                                }
                                // Change BackGround Color. // Red = Late // Yellow = Two minute warning
                                if (recipeStopWatches.get(i).is_watch_Running()) {
                                    if (totalTimeElapsedStopWatch.getElapsedTime() >= (expectedOrderTime * 60000L)) {
                                        backgroundColor = "-fx-background-color: red;\n";
                                    } else if (totalTimeElapsedStopWatch.getElapsedTime() >= ((expectedOrderTime - 2) * 60000L)) {
                                        backgroundColor = "-fx-background-color: orange;\n";
                                    }
                                }
                            }
                        }));
                    }
                });
            }
        },0,1000);
    }

    public StopWatch getTotalTimeElapsedStopWatch() {
        return totalTimeElapsedStopWatch;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public ArrayList<MenuFoodItem> getMenuItems() {
        return menuItems;
    }

    public ArrayList<ArrayList<Float>> getPrepTimesList() {
        return prepTimesList;
    }

    public void addOneExecute(int i, int j) {
        // increase prepTime by 1 minute
        prepTimesList.get(i).set(j, prepTimesList.get(i).get(i) + 1);
        // update expectedOrderTime
        expectedOrderTime += 1;

        // increase all start times by 1 minute
        startTimesList.forEach((list -> {
            for (int z = 0; z < list.size(); z++) {
                startTimes.set(z, startTimes.get(z) + 60000);
            }
        }));
    }
}