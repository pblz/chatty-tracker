package com.example.android.chatty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;
import static com.example.android.chatty.R.id.total;

/**
 * Created by pia on 15.11.17.
 */

public class SummaryActivity extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            //get Strings
            String total = getIntent().getExtras().getString("EXTRA_TOTAL");
            String workTotal = getIntent().getExtras().getString("EXTRA_WORK_TOTAL");
            String explainTotal = getIntent().getExtras().getString("EXTRA_EXPLAIN_TOTAL");
            String waterTotal = getIntent().getExtras().getString("EXTRA_WATER_TOTAL");


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_summary);

            //fill textfields
            TextView totalTextView = (TextView) findViewById(R.id.total);
            totalTextView.setText(total);

            //fill textfields
            TextView work = (TextView) findViewById(R.id.work);
            work.setText(workTotal);

            //fill textfields
            TextView explain = (TextView) findViewById(R.id.explain);
            explain.setText(explainTotal);

            //fill textfields
            TextView waterView = (TextView) findViewById(R.id.water);
            waterView.setText(waterTotal);


            //Find the Button that starts the search
            Button done = (Button) findViewById(R.id.done);

            done.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {

                            Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                            resetAll();
                            startActivity(intent);
                        }
                    });

        }

    public void resetAll(){
        //fill textfields
        TextView totalTextView = (TextView) findViewById(R.id.total);
        totalTextView.setText(getResources().getString(R.string.zeroclock));

        //fill textfields
        TextView work = (TextView) findViewById(R.id.work);
        work.setText(getResources().getString(R.string.zeroclock));

        //fill textfields
        TextView explain = (TextView) findViewById(R.id.explain);
        explain.setText(getResources().getString(R.string.zeroclock));

        //fill textfields
        TextView water = (TextView) findViewById(R.id.water);
        water.setText(getResources().getString(R.string.zeroclock));
    }
    }
