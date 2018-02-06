package com.example.android.chatty;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {

        public static final String LOG_TAG = MainActivity.class.getName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Set the content of the activity to use the activity_main.xml layout file
            setContentView(R.layout.activity_main);

            //Find the Button that starts the search
            Button button = (Button) findViewById(R.id.Start);

            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, TrackingActivity.class);
                            startActivity(intent);
                        }
                    });

        }

    }
