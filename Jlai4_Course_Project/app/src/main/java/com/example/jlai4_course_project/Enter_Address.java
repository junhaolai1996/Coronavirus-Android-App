package com.example.jlai4_course_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class Enter_Address extends AppCompatActivity {

    Button enterB, referenceB, aboutB;
    ImageButton infoB;
    TextView enterAdd;

    double latitude,longitude;

    String state="", locality="", county="", countryName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__address);

        enterAdd = (TextView)findViewById(R.id.editText);
        enterB = (Button) findViewById(R.id.enter_location);
        referenceB = (Button) findViewById(R.id.reference_button);
        aboutB = (Button) findViewById(R.id.about_button);
        infoB = (ImageButton) findViewById(R.id.imageButton);


        enterB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(enterAdd.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Please enter a location", Toast.LENGTH_SHORT).show();
                        }else {
                            Geocoder coder = new Geocoder(getApplicationContext());
                            List<Address> address;
                            try {
                                address = coder.getFromLocationName(enterAdd.getText().toString(), 1);

                                if (!address.isEmpty()) {
                                    latitude = address.get(0).getLatitude();
                                    longitude = address.get(0).getLongitude();
                                    state = address.get(0).getAdminArea();
                                    locality = address.get(0).getLocality();
                                    county = address.get(0).getSubAdminArea();
                                    countryName = address.get(0).getCountryName();
                                }
                                //Toast.makeText(getApplicationContext(), String.format("The lat is %s and the long is %s", latitude, longitude), Toast.LENGTH_SHORT).show();

                                if(!(countryName.equals(""))){
                                    new getJSONData().execute();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        referenceB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reference_Activity.class);
                startActivity(intent);
            }
        });


        aboutB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), About_Activity.class);
                startActivity(intent);
            }
        });

        infoB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Information_Activity.class);
                startActivity(intent);
            }
        });
    }


//    private class getGEOData extends AsyncTask<Void, Void, Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            return null;
//        }
//    }



    private class getJSONData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                //URL theRequestURL = new URL(String.format("https://api.covid19api.com/live/country/%s",countryName.toLowerCase()));
                URL theRequestURL = new URL(String.format("https://corona.lmao.ninja/v2/states/%s?yesterday=",state.toLowerCase()));

                HttpURLConnection connection = (HttpURLConnection) theRequestURL.openConnection();

                connection.setRequestMethod("GET");

                connection.connect();

                if(connection.getResponseCode() != 200){
                    //Toast.makeText(getApplicationContext(), "Could not get data from API", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Could not get data from API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    String returnedJSON = "";

                    Scanner scan = new Scanner(theRequestURL.openStream());

                    scan.useDelimiter("\"");

                    while(scan.hasNext()){
                        returnedJSON += scan.nextLine();
                    }

                    scan.close();

                    String totalDeath = "";
                    String totalRecovered = "";
                    String numberInfected = "";

                    String allJSON[]  = returnedJSON.split(",");


                    numberInfected = allJSON[7].split(":")[1];
                    totalDeath = allJSON[4].split(":")[1];
                    totalRecovered = allJSON[6].split(":")[1];

                    Intent intent = new Intent(getApplicationContext(), Result_Activity.class);
                    intent.putExtra("state", state.toUpperCase());
                    intent.putExtra("infect", numberInfected);
                    intent.putExtra("death", totalDeath);
                    intent.putExtra("recover", totalRecovered);
                    startActivity(intent);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}