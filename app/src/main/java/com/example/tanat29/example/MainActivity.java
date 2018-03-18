package com.example.tanat29.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tanat29 on 17/2/2018 AD.
 */

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> CarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CarList = new ArrayList<>();
        spinner = findViewById(R.id.car_Name);
        loadSpinnerData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(), country, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    private void loadSpinnerData() {

        Ion.with(getApplicationContext())
                .load("https://myappcar-wash001.000webhostapp.com/web/test.php")
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        String aa = result.getResult();

                        try {
                            JSONObject jsonObject = new JSONObject(aa);
                            JSONArray jsonArray = jsonObject.getJSONArray("Android");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String car_name = jsonObject1.getString("car_name");

                                Log.i("zz", car_name);

                                CarList.add(car_name);
                            }
                            spinner.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, CarList));
                        } catch (JSONException e2) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}


