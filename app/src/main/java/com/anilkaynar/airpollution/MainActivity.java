package com.anilkaynar.airpollution;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.anilkaynar.airpollution.WeatherDto.CityWeather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText cityEditText;
    private VolleyService service;
    String city="";
    String url="http://api.openweathermap.org/data/2.5/weather?q=berlin&appid=750c0658847858f483fdbf7234dc6296";
String json=
        "{\"coord\":{\"lon\":13.39,\"lat\":52.52},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":275.64,\"pressure\":1027,\"humidity\":59,\"temp_min\":275.15,\"temp_max\":276.15},\"visibility\":10000,\"wind\":{\"speed\":2.6,\"deg\":190},\"clouds\":{\"all\":75},\"dt\":1518985200,\"sys\":{\"type\":1,\"id\":4892,\"message\":0.0039,\"country\":\"DE\",\"sunrise\":1518934506,\"sunset\":1518971178},\"id\":2950159,\"name\":\"Berlin\",\"cod\":200}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityEditText=findViewById(R.id.edtTextSehir);
        Log.e("Gson", Gson.class.toString());//com.google.gson.Gson
        Gson gson=new GsonBuilder().create();
        //34.052235, 118.243683
       // 41.0151,28.9795.
        try {
         //   JSONObject obj = new JSONObject(json);
          CityWeather cityWeather= gson.fromJson(json, CityWeather.class);
           Log.e("City some",cityWeather.toString());
           Log.e("Gson:",gson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cityEditText.addTextChangedListener(watcher);
    }
    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String city= charSequence.toString();
            //Toast.makeText(getApplicationContext(),city,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
               String city= editable.toString();
           // Toast.makeText(getApplicationContext(),city,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        service=new VolleyService();
        service.singleton(this);
        service.addToRequestQueue(request(url),"t");
    }
    public JsonObjectRequest request(String url2) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url2, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Gson gson=new GsonBuilder().create();
                       CityWeather weather= gson.fromJson(response.toString(),CityWeather.class);
                       String main=weather.weather.get(0).main;
                       String aciklama=weather.weather.get(0).description;
                      TextView aciklamaTextView= findViewById(R.id.aciklama);
                      aciklamaTextView.setText(aciklama);
                        TextView ozetextView= findViewById(R.id.ozetbilgi);
                        ozetextView.setText(main);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Böyle Bir şehir bulunmamaktadır",Toast.LENGTH_SHORT)
                        .show();
                Log.e("Bir iki", "Error: " + error.getMessage());
                // hide the progress dialog

            }
        });
        return jsonObjReq;
    }
    public void cityFunction(View v){
       city= cityEditText.getText().toString();

       url= String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=Api_Key",city);
       Log.e("url",url);
       service.addToRequestQueue(request(url),"");
    }
}

