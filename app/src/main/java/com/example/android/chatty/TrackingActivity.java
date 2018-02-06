package com.example.android.chatty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;
import static android.R.id.input;

/**
 * Created by pia on 15.11.17.
 * with input from
 * http://www.shawnbe.com/index.php/tutorial/tutorial-3-a-simple-stopwatch-lets-add-the-code/
 */

public class TrackingActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private long workStarted;
    private long explainStarted;
    private long waterStarted;
    private long workTotal;
    private long explainTotal;
    private long waterTotal;
    private final int REFRESH_RATE = 100;
    private String hours,minutes,seconds,milliseconds;
    private long secs,mins,hrs,msecs;
    private boolean stopped = false;
    private boolean notStarted = true;
    private boolean isWork = false;
    private boolean isWater = false;
    private boolean isExplain = false;

    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this,REFRESH_RATE);
        }
    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tracking);

            //Find the Button
            Button endButton = (Button) findViewById(R.id.end);

            endButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            hideExplainButton();
                            hideWaterButton();
                            hideWorkButton();
                            Intent intent = new Intent(TrackingActivity.this, SummaryActivity.class);

                            //TODO: disable possibility to click buttons on tracking Activity screen

                            //end timer
                            mHandler.removeCallbacks(startTimer);
                            stopped = true;


                            //hand over result strings to summary activity
                            intent.putExtra("EXTRA_TOTAL",((TextView)findViewById(R.id.timer)).getText() );
                            intent.putExtra("EXTRA_WORK_TOTAL",timeToString(workTotal));
                            intent.putExtra("EXTRA_EXPLAIN_TOTAL",timeToString(explainTotal));
                            intent.putExtra("EXTRA_WATER_TOTAL",timeToString(waterTotal));

                            ((TextView)findViewById(R.id.timer)).setText(getResources().getString(R.string.zeroclock));

                           startActivity(intent);
                        }
                    });

            //Find the Button Work
            Button startButton = (Button) findViewById(R.id.work);
            startButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            if(notStarted){
                                startClick(view);
                            }
                            isWork = true;
                            workStarted = elapsedTime;
                            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            //set all other buttons to grey
                            hideExplainButton();
                            hideWaterButton();
                        }
                    });

            //Find the Button Explain
            Button explainButton = (Button) findViewById(R.id.explain);
            explainButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            if(notStarted){
                                startClick(view);
                            }
                            isExplain = true;
                            explainStarted = elapsedTime;
                            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            //set all other buttons to grey
                            hideWorkButton();
                            hideWaterButton();
                        }
                    });
            //Find the Button Water
            Button waterButton = (Button) findViewById(R.id.water);
            waterButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            if(notStarted){
                                startClick(view);
                            }
                            waterStarted = elapsedTime;
                            isWater = true;
                            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            //set all other buttons to grey
                            hideWorkButton();
                            hideExplainButton();
                        }
                    });
        }

    public void hideWorkButton(){
        //Find the Button
        Button button = (Button) findViewById(R.id.work);
        button.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
        //check if work button was active before and if so, add elapesed time since then to total work time
        if(isWork){
            workTotal += (elapsedTime - workStarted);
            isWork = false;
        }

    }
    public void hideExplainButton(){
        //Find the Button
        Button button = (Button) findViewById(R.id.explain);
        button.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
        if(isExplain){
            explainTotal += (elapsedTime - explainStarted);
            isExplain = false;
        }

    }
    public void hideWaterButton(){
        //Find the Button
        Button button = (Button) findViewById(R.id.water);
        button.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
        if(isWater){
            waterTotal += (elapsedTime - waterStarted);
            isWater = false;
        }
    }


    public void startClick (View view){
        //showStopButton();
        if(stopped){
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else{
            startTime = System.currentTimeMillis();
            notStarted = false;
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    public void stopClick (View view){
        //hideStopButton();
        mHandler.removeCallbacks(startTimer);
        stopped = true;
    }

    public void resetClick (View view){
        stopped = false;
        ((TextView)findViewById(R.id.timer)).setText("00:00:00");

    }

    private void updateTimer (float time){
        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);

		/* Convert the seconds to String
		 * and format to ensure it has
		 * a leading zero when required
		 */
        secs = secs % 60;
        seconds=String.valueOf(secs);
        if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes=String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+minutes;
        }

    	/* Convert the hours to String and format the String */

        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }


		/* Setting the timer text to the elapsed time */
        ((TextView)findViewById(R.id.timer)).setText(hours + ":" + minutes + ":" + seconds);

    }

    public String timeToString(long time) {
            secs = (long)(time/1000);
            mins = (long)((time/1000)/60);
            hrs = (long)(((time/1000)/60)/60);

		/* Convert the seconds to String
		 * and format to ensure it has
		 * a leading zero when required
		 */
            secs = secs % 60;
            seconds=String.valueOf(secs);
            if(secs == 0){
                seconds = "00";
            }
            if(secs <10 && secs > 0){
                seconds = "0"+seconds;
            }

		/* Convert the minutes to String and format the String */

            mins = mins % 60;
            minutes=String.valueOf(mins);
            if(mins == 0){
                minutes = "00";
            }
            if(mins <10 && mins > 0){
                minutes = "0"+minutes;
            }

    	/* Convert the hours to String and format the String */

            hours=String.valueOf(hrs);
            if(hrs == 0){
                hours = "00";
            }
            if(hrs <10 && hrs > 0){
                hours = "0"+hours;
            }
            return hours + ":" + minutes + ":" + seconds;
    }

    }
