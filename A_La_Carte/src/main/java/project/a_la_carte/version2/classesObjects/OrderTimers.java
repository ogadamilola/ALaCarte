package project.a_la_carte.version2.classesObjects;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

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

        //This code doesn't affect the stagger time
        prepTimesList.forEach(prepTime -> {
            prepTime.forEach(time -> {
                if (time >= expectedOrderTime){
                    expectedOrderTime = time;
                }
            });
        });
        //Un comment to test this way
        //this.expectedOrderTime += 1;
        System.out.println("---------------START OF ADD ONE METHOD------------------------");
        System.out.println("Expected Order Time: "+ expectedOrderTime);

        // increase all start times by 1 minute <- Not every situation for adding 1 min of recipe time should increase the StartTime of all
        //This Basically works as statTimes += 1 for each start time
        //Which is why some timers don't start --> Their start times is too large

        //Un comment test this way
//        startTimesList.forEach((list -> {
//            for (int z = 0; z < list.size(); z++) {
//                startTimes.set(z, startTimes.get(z) + 60000);
//            }
//        }));
        //Resetting startTimes in accord with a change of time
        // -> Since not every start time should increase, there are situations where start time of a timer shouldn't change
        startTimesList = new ArrayList<>();
        prepTimesList.forEach((list -> {
            startTimes = new ArrayList<>();
            list.forEach(prep -> {
                startTimes.add((long) ((expectedOrderTime - prep) * 60000));
            });
            startTimesList.add(startTimes);
        }));


        //For testing print on terminal
        AtomicInteger item = new AtomicInteger(1);
        startTimesList.forEach((list -> {
            System.out.println("---------------------------------------");
            System.out.println("Start Times for Item: " + item);
            for (int z = 0; z < list.size(); z++) {
                System.out.println("Recipe # " + (z+1));
                System.out.println("Start In: "+ list.get(z)/60000 + " Min"); //<- dividing by 60000 cuz its in long
            }
            item.addAndGet(1);
        }));
        System.out.println("------------------END OF ADD 1 METHOD--------------------------------");
    }
}