package project.a_la_carte.version2.timerSystemPrototype;

import project.a_la_carte.version2.classesObjects.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    // **** This is a code artifact / prototype
    // The logic demonstrated was implemented into Kitchen/widgets/OrderItems.Java
public class TimerMainFrame extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;
    private JButton oneMinSubButton;
    private JButton recipeFinishedButton;
    private final JLabel totalTimeElapsedLabel;
    private final StopWatch totalTimeElapsedStopWatch;
    private final int[] prepTimes; // In minutes (int)
    private final long[] startTimes; // In milliseconds (long) because must be comparable to the StopWatch.getElapsedTime()
    private final StopWatch[] recipeStopWatches;
    private final JLabel[] recipesTimeElapsedLabel;
    private JTextField recipeAddTimeTargetAnswerField;
    private int expectedOrderTime;

    public TimerMainFrame() {
        // initialize prepTimes
        // Must Have Largest prepTime at Index 0
        prepTimes = new int[]{4, 3, 2, 1};

        // initialize starTimes subtracting recipe prepTime from Order prepTime (prepTime[0])
        startTimes = new long[prepTimes.length];
        for (int i = 0; i < prepTimes.length; i++) {
            int timeInMIn = prepTimes[0] - prepTimes[i];
            startTimes[i] = (long) timeInMIn * 60000;
        }

        // Starting a StopWatch for the whole order
        totalTimeElapsedStopWatch = new StopWatch();
        totalTimeElapsedStopWatch.start();
        totalTimeElapsedLabel = new JLabel("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());

        // Creating an array of un-started StopWatches
        recipeStopWatches = new StopWatch[prepTimes.length];
        for (int i = 0; i < prepTimes.length; i++) {
            recipeStopWatches[i] = new StopWatch();
        }


        expectedOrderTime = prepTimes[0];


        recipesTimeElapsedLabel = new JLabel[prepTimes.length];
        for (int i = 0; i < prepTimes.length; i++) {
            recipesTimeElapsedLabel[i] = new JLabel("Recipe "+ i + " TIME " + recipeStopWatches[i].getElapsedTimeFormatted() + " // " + prepTimes[i] + ":00");
        }

        createOneMinSubtractButton();
        createTextField();
        createRecipeFinishedButton();
        createPanel();


        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        ActionListener listener = new TimerListener();

        final int DELAY = 60; // Milliseconds between timer ticks
        Timer t = new Timer(DELAY, listener);
        t.start();
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // Print the Order StopWatch
            totalTimeElapsedLabel.setText("Total Time: " + totalTimeElapsedStopWatch.getElapsedTimeFormatted());

            // Start StopWatch when correct time has passed
            for (int i = 0; i < prepTimes.length; i++) {
                if ((totalTimeElapsedStopWatch.getElapsedTime() >= startTimes[i]) && (!recipeStopWatches[i].is_watch_Running()) && (!recipeStopWatches[i].is_watch_finished_Running())) {
                    recipeStopWatches[i].syncNewStopWatch(totalTimeElapsedStopWatch, startTimes[i]);
                }
            }

            // Print Recipe StopWatches
            for (int i = 0; i < prepTimes.length; i++) {
                recipesTimeElapsedLabel[i].setText("Recipe " + i + " TIME " + recipeStopWatches[i].getElapsedTimeFormatted() + " // " + prepTimes[i] + ":00");

                // Change Text color to red if recipe is late
                // and yellow for a two-minute warning
                if (recipeStopWatches[i].is_watch_Running()) {
                    if (totalTimeElapsedStopWatch.getElapsedTime() >= (expectedOrderTime * 60000L)) {
                        recipesTimeElapsedLabel[i].setForeground(Color.red);
                    }
                    else if (totalTimeElapsedStopWatch.getElapsedTime() >= ((expectedOrderTime - 2) * 60000L)) {
                        recipesTimeElapsedLabel[i].setForeground(Color.orange);
                    }
                }
            }
        }
    }

    private void createTextField() {
        final int FIELD_WIDTH = 10;
        recipeAddTimeTargetAnswerField = new JTextField(FIELD_WIDTH);
        recipeAddTimeTargetAnswerField.setText("" + 1);
    }

    private void createOneMinSubtractButton() {
        oneMinSubButton = new JButton("Need an extra minute for recipe at index: ");
        oneMinSubButton.addActionListener(new OneMinSubListener());
    }

    class OneMinSubListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int targetRecipeIndex = Integer.parseInt(recipeAddTimeTargetAnswerField.getText());
            // increase prepTime by 1 minute
            prepTimes[targetRecipeIndex] += 1;
            // increase all start times by 1 minute
            for (int i = 0; i < prepTimes.length; i++) {
                startTimes[i] += 60000;
            }
            // update expectedOrderTime
            if (prepTimes[targetRecipeIndex] >= expectedOrderTime) {
                expectedOrderTime = prepTimes[targetRecipeIndex];
            }
        }
    }

    private void createRecipeFinishedButton(){
        recipeFinishedButton = new JButton("Finished Recipe at index: ");
        recipeFinishedButton.addActionListener(new RecipeFinishedListener());
    }

    class RecipeFinishedListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int targetRecipeIndex = Integer.parseInt(recipeAddTimeTargetAnswerField.getText());
            recipeStopWatches[targetRecipeIndex].stop();
        }
    }

    private void createPanel() {
        JPanel panel = new JPanel();
        panel.add(totalTimeElapsedLabel);
        panel.add(oneMinSubButton);
        panel.add(recipeFinishedButton);
        panel.add(recipeAddTimeTargetAnswerField);

        for (int i = 0; i < prepTimes.length; i++) {
            panel.add(recipesTimeElapsedLabel[i]);
        }

        add(panel);
    }

}
