package project.a_la_carte.version2.timerSystemPrototype;

public class StopWatch {
    private long elapsedTime;
    private long startTime;
    private boolean isRunning;
    private boolean finishedRunning;


    // Constructs a stopwatch that is in the stopped state
    // and has no time accumulated
    public StopWatch() {
        reset();
    }

    // Sync A StopWatch with a previous StopWatch
    // Makes a StopWatch equal to a previous StopWatch minus the specified turn back time in milliseconds
    // This is need to sync stopWatches to the millisecond
    public void syncNewStopWatch(StopWatch prev, long turnBack) {
        if (isRunning) { return; }
        isRunning = true;
        finishedRunning = false;
        startTime = prev.getStartTime() + turnBack;
    }

    // Starts the stopwatch. Time starts accumulating now
    public void start() {
         if (isRunning) { return; }
         isRunning = true;
         startTime = System.currentTimeMillis();
         }

    // Stops the stopwatch. Time stops accumulating and is added to the elapsed time.
    public void stop() {
         if (!isRunning) { return; }
         isRunning = false;
         finishedRunning = true;
         long endTime = System.currentTimeMillis();
         elapsedTime = elapsedTime + endTime - startTime;
         }

    public long getElapsedTime() {
         if (isRunning)
             {
             long endTime = System.currentTimeMillis();
             return elapsedTime + endTime - startTime;}
         else
         {
             return elapsedTime;
             }
         }

    public long getStartTime() {
        return startTime;
    }

    public void reset() {
         elapsedTime = 0;
         isRunning = false;
    }

    public boolean is_watch_Running() {
        return isRunning;
    }

    public boolean is_watch_finished_Running() {
        return finishedRunning;
    }

    public String getElapsedTimeFormatted() {
        long min = getElapsedTime() / 60000;
        long sec = (getElapsedTime() / 1000) % 60;
        long millSec = getElapsedTime() % 1000;
        String zeroSec;

        if (sec < 10) {
            zeroSec = "0" + sec;
        }
        else {
            zeroSec = String.valueOf(sec);
        }

        String zeroMillSec;
        if (millSec < 10) {
            zeroMillSec = "00" + millSec;
        }
        else if (millSec < 100) {
            zeroMillSec = "0" + millSec;
        }
        else {
            zeroMillSec = String.valueOf(millSec);
        }

        return min + ":" + zeroSec + ":" + zeroMillSec;

    }
}
