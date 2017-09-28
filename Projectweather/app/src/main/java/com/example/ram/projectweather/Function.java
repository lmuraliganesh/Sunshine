package com.example.ram.projectweather;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;



public class Function {
//    private static final String OPEN_WEATHER= "http://samples.openweathermap.org/data/2.5/weather?q=London,uk";


    private static final String OPEN_WEATHER_MAP_API = "d64d7a0b3a2da360fecf281d75b7c8b1";



    public interface AsyncResponse {

        void finish(String output1, String output2);

        class placeIdTask extends AsyncTask<String, Void, JSONObject> {


            private static final String OPEN_WEATHER="http://api.openweathermap.org/data/2.5/weather?q=%s"+"&appid="+OPEN_WEATHER_MAP_API;
            public Function.AsyncResponse delegate = null;//Call back interface

            public placeIdTask(Function.AsyncResponse asyncResponse) {
                delegate = asyncResponse;//Assigning call back interfacethrough constructor
            }

            @Override
            protected JSONObject doInBackground(String... params) {

                JSONObject jsonWeather = null;
                try {
                    jsonWeather = getWeatherJSON(params[0]/*params[0], params[1]*/);
                } catch (Exception e) {
                    Log.d("Error", "Cannot process JSON results", e);
                }
                return jsonWeather;
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                System.out.println("onPostExecute===>"+json);
                try {
                    if (json != null) {
                        JSONObject details = json.getJSONObject("main");

                        String city = json.getString("name").toUpperCase(Locale.UK) + ", " + json.getJSONObject("sys").getString("country");

                        String temperature = String.format("%.2f", details.getDouble("temp")) + "°";

                        delegate.finish(city, temperature);
                    }


                } catch (JSONException e) {

                }
            }

//            public static JSONObject getWeatherJSON(String lat, String lon){
              public static JSONObject getWeatherJSON(String city){
                try {
//                    URL url = new URL(String.format(OPEN_WEATHER, lat, lon));
                    URL url = new URL(String.format(OPEN_WEATHER,city));
                    System.out.println("URL:"+url.getPath());
                    HttpURLConnection connection =
                            (HttpURLConnection)url.openConnection();

                    connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp="";
                    while((tmp=reader.readLine())!=null)
                        json.append(tmp).append("\n");
                    reader.close();
                    JSONObject data = new JSONObject(json.toString());

                    // This value will be 404 if the request was not
                    // successful
                    if(data.getInt("cod") != 200){
                        return null;
                    }

                    return data;
                }catch(Exception e){
                    return null;
                }
            }
        }

    }
}

