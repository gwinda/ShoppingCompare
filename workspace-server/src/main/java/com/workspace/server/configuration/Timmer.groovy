package com.workspace.server.configuration
/**
 * Created by CHENMA11 on 1/24/2018.
 */

public class Timmer {
//http://blog.csdn.net/sang1203/article/details/51286221
    public static void main(String[] args) {
        Date date = new Date();

        Timer t = new Timer("No1");  // Create a new timer No1  , Or you can directly create, don't name   Timer t = new Timer();

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {

                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
            }

        };

        t.schedule(tt, date, 10000000);    // Starting from the current time, per  1 Second to repeatedly perform a scheduled task, I only used here is the current time  , Time can according to their needs.  .
//             t.schedule(tt,date);               // Scheduled to perform a task
//             t.cancel();                              // Terminate this timer, discard all currently scheduled tasks  .
//             t.purge();                              // From this timer job queue to remove all the task has been canceled
//             tt.cancel();                            // Cancel this timer tasks  .
    }
}
