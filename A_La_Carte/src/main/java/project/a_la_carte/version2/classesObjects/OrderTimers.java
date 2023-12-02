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
    ArrayList<ArrayList<StopWatch>> recipeStopWatchList;
    float expectedOrderTime;
    String backgroundColor;
    public OrderTimers(Order realOrder){
        this.menuItems = realOrder.getOrderList();
        this.orderNum = realOrder.getOrderNum();

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
                recipe.setNotFinished();
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
        recipeStopWatchList = new ArrayList<>();
        menuItems.forEach((item -> {
            recipeStopWatches = new ArrayList<>();
            item.getMenuItemRecipes().forEach((recipe -> {
                StopWatch newStop = new StopWatch();
                recipeStopWatches.add(newStop);
            }));
            recipeStopWatchList.add(recipeStopWatches);
        }));

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < menuItems.size(); i++) {
                            for (int j = 0; j < menuItems.get(i).getMenuItemRecipes().size(); j++) {
                                // Start StopWatch When it is passed it's startTime
                                if ((totalTimeElapsedStopWatch.getElapsedTime() >= startTimesList.get(i).get(j))
                                        && (!recipeStopWatchList.get(i).get(j).is_watch_Running())
                                        && (!recipeStopWatchList.get(i).get(j).is_watch_finished_Running())) {
                                    recipeStopWatchList.get(i).get(j).syncNewStopWatch(totalTimeElapsedStopWatch, startTimesList.get(i).get(j));
                                }
                                // Change BackGround Color. // Red = Late // Yellow = Two minute warning
                                if (recipeStopWatchList.get(i).get(j).is_watch_Running()) {
                                    if (totalTimeElapsedStopWatch.getElapsedTime() >= (expectedOrderTime * 60000L)) {
                                        backgroundColor = "-fx-background-color: red;\n";
                                    } else if (totalTimeElapsedStopWatch.getElapsedTime() >= ((expectedOrderTime - 2) * 60000L)) {
                                        backgroundColor = "-fx-background-color: orange;\n";
                                    }
                                }
                            }
                        }
                    }
                });
            }
        },0,250);
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
    public ArrayList<ArrayList<StopWatch>> getRecipeStopWatchList() {
        return recipeStopWatchList;
    }

    public void addOneExecute(int i, int j) {
        // increase prepTime by 1 minute
        prepTimesList.get(i).set(j, prepTimesList.get(i).get(j) + 1);
        // update expectedOrderTime
//        prepTimesList.forEach(prepTime -> {
//            prepTime.forEach(time -> {
//                if (time >= expectedOrderTime){
//                    expectedOrderTime = time;
//                }
//            });
//        });
        //
        this.expectedOrderTime += 1;
        // increase all start times by 1 minute
        startTimesList.forEach((list -> {
            for (int z = 0; z < list.size(); z++) {
                startTimes.set(z, startTimes.get(z) + 60000);
            }
        }));
    }
}