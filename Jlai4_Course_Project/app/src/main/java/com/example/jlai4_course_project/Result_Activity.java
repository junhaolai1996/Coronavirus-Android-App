package com.example.jlai4_course_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result_Activity extends AppCompatActivity {

    Button homeB, webB;

    TextView title, death, infected,recovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_);

        homeB = (Button) findViewById(R.id.home_button);
        webB = (Button) findViewById(R.id.web_button);

        title = (TextView) findViewById(R.id.title_text);
        death = (TextView) findViewById(R.id.death_text);
        infected = (TextView) findViewById(R.id.infect_text);
        recovered = (TextView) findViewById(R.id.recover_text);


        Intent intent = getIntent();

        title.setText("Statistics for the location: \n" + intent.getStringExtra("state"));
        death.setText("Total Death: " + intent.getStringExtra("death"));
        infected.setText("Active Infection: "+ intent.getStringExtra("infect"));
        recovered.setText("Total Recovered: "+ intent.getStringExtra("recover"));

        homeB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Enter_Address.class);
                startActivity(intent);
            }
        });

        webB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Web_Activity.class);
                startActivity(intent);
            }
        });
    }
}