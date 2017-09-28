package com.example.ram.projectweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView city, temp;
    private Spinner citySpinner;

    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citySpinner = (Spinner) findViewById(R.id.spinner1);
        list.add("San Diego, California, U.S");
        list.add("Malaga, Spain");
        list.add("Canary Islands, Spain");
        list.add("Sydney, Australia");
        list.add("Sao Paulo, Brazil");

        list.add("Kunming, China");
        list.add("Loja, Ecuador");
        list.add("Oahu, Hawaii");
        list.add("Nice, France");
        list.add("Medellin, Colombia");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(dataAdapter);

        // Spinner item selection Listener
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
//                Toast.makeText(view.getContext(),
//                        "On Item Select : \n" + adapterView.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_LONG).show();
                city.setText(new String());
                temp.setText(new String());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn = (Button) findViewById(R.id.getweather);
        city = (TextView) findViewById(R.id.city);
        temp = (TextView) findViewById(R.id.Temperature);




           btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Function.AsyncResponse.placeIdTask asyncTask =new Function.AsyncResponse.placeIdTask(new Function.AsyncResponse() {

                    @Override
                    public void finish(String cityname, String citytemp) {
//                        System.out.println(cityname+":"+citytemp);
                        city.setText(cityname);
                        temp.setText(citytemp.toString());
                    }
                });
                asyncTask.execute( (String ) citySpinner.getSelectedItem()/*"-0.13", "51.51"*/); //  asyncTask.execute("Latitude", "Longitude")


            }
        });
    }
}
