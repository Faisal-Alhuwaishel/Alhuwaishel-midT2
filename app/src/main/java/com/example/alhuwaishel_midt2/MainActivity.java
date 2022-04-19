package com.example.alhuwaishel_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    TextView temperature,humidity;
    // JSON object that contains weather information
    JSONObject jsonObj;
    String weatherWebserviceURL ="http://api.openweathermap.org/data/2.5/weather?q=london&appid=c4e0425479a004ea8e0751e141be2152&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView result = (TextView) findViewById(R.id.txtDate);
        Button button = (Button) findViewById(R.id.btnDate);
        Button btnGo2 = (Button) findViewById(R.id.btnAct2);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance();
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                c.set(Calendar.YEAR, i);
                c.set(Calendar.MONTH, i1);
                c.set(Calendar.DAY_OF_MONTH, i2);
                result.setText("Your date is set for: "+dateFormat.format(c.getTime()));
            }
        } ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        temperature = (TextView) findViewById(R.id.txtTemp);
        humidity = (TextView) findViewById(R.id.txtHumid);
        weather(weatherWebserviceURL);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Log.d("Faisal option",text);
                weatherWebserviceURL =
                        "http://api.openweathermap.org/data/2.5/weather?q="+text+"&appid=c4e0425479a004ea8e0751e141be2152&units=metric";
                weather(weatherWebserviceURL);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
    }
    public void weather(String URL){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Faisal","Response received");
                        Log.d("Faisal",response.toString());
                        try {
                            String town = response.getString("main");
                            Log.d("Faisal's town",town);
                            JSONObject jsonMain = response.getJSONObject("main");
                            double temp = jsonMain.getDouble("temp");
                            double hum = jsonMain.getDouble("humidity");
                            Log.d("Faisal's temp",String.valueOf(temp));
                            temperature.setText(String.valueOf(temp)+"°C");
                            humidity.setText("Humidity: "+String.valueOf((int)hum)+"%");
                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("Faisal error",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("Faisal","Error retrieving the URL");
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}


