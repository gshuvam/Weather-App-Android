package com.shuvam.WeatherMe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    ImageView weatherImage;
    TextView temp;
    TextView weatherInfo;
    TextView weatherDescription;
    TextView feelsLike;
    TextView maxTemp;
    TextView minTemp;
    TextView pressure;
    TextView humidity;
    ConstraintLayout layout;
    TextView feelsLikeText;
    TextView maxTempText;
    TextView minTempText;
    TextView pressureText;
    TextView humidityText;
    TextView maxTempDeg;
    TextView minTempDeg;
    TextView tempDeg;




    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        temp=findViewById(R.id.temp);
        weatherImage=findViewById(R.id.weatherImage);
        weatherInfo=findViewById(R.id.weatherInfo);
        weatherDescription=findViewById(R.id.weatherDescription);
        feelsLike=findViewById(R.id.feelslikeval);
        maxTemp=findViewById(R.id.maxTempVal);
        minTemp=findViewById(R.id.minTempVal);
        pressure=findViewById(R.id.pressureVal);
        humidity=findViewById(R.id.humidityVal);
        layout=findViewById(R.id.layout);
        feelsLikeText=findViewById(R.id.feelsLike);
        maxTempText=findViewById(R.id.maxTemp);
        minTempText=findViewById(R.id.minTemp);
        pressureText=findViewById(R.id.pressure);
        humidityText=findViewById(R.id.humidity);
        maxTempDeg=findViewById(R.id.maxTempDeg);
        minTempDeg=findViewById(R.id.minTempDeg);
        tempDeg=findViewById(R.id.tempDeg);


        Intent intent = getIntent();
        String result = intent.getStringExtra("data");

        String main = "", description = "";
        String tem="",feels_like="",temp_min="",temp_max="",press="",hum="";
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);


        try {
            JSONObject jsonObject = new JSONObject(result);

            //Weather
            String weatherI = jsonObject.getString("weather");
            JSONArray jsonArray = new JSONArray(weatherI);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);

                main = jsonPart.getString("main");
                description = jsonPart.getString("description");
                if(!main.equals("") && !description.equals("")){
                    weatherInfo.setText(main);
                    weatherDescription.setText(description);

                }
                else{
                    weatherInfo.setText("");
                    weatherDescription.setText("");
                    Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
                }

            }

            String mainInfo = jsonObject.getString("main");
            JSONObject obj1=new JSONObject(mainInfo);

            tem=obj1.getString("temp");
            feels_like=obj1.getString("feels_like");
            temp_min=obj1.getString("temp_min");
            temp_max=obj1.getString("temp_max");
            press=obj1.getString("pressure");
            hum=obj1.getString("humidity");
            if(!tem.equals("")){
                temp.setText(String.valueOf((int) Double.parseDouble(tem)));
            }
            if(!feels_like.equals("")){
                feelsLike.setText(feels_like);
            }
            if(!temp_max.equals("")){
                maxTemp.setText(temp_max);
            }
            if(!temp_min.equals("")){
                minTemp.setText(temp_min);
            }
            if(!press.equals("")){
                pressure.setText(press);
            }
            if(!hum.equals("")){
                humidity.setText(hum);
            }
            if(tem.equals("") && feels_like.equals("") && temp_max.equals("") && temp_min.equals("") && press.equals("") && hum.equals("")){
                temp.setText("");
                humidity.setText("");
                pressure.setText("");
                minTemp.setText("");
                maxTemp.setText("");
                feelsLike.setText("");
                weatherDescription.setText("");
                weatherInfo.setText("");
                Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
            }

            boolean b = description.equals("few clouds") || description.equals("scattered clouds") || description.equals("broken clouds");
            if(hourOfDay > 6 && hourOfDay < 18) {
                layout.setBackgroundResource(R.drawable.day_background);
//                humidity.setTextColor(R.color.black);
//                pressure.setTextColor(R.color.black);
//                minTemp.setTextColor(R.color.black);
//                maxTemp.setTextColor(R.color.black);
//                weatherInfo.setTextColor(R.color.black);
//                weatherDescription.setTextColor(R.color.black);
//                feelsLike.setTextColor(R.color.black);
//                humidityText.setTextColor(R.color.black);
//                pressureText.setTextColor(R.color.black);
//                minTempText.setTextColor(R.color.black);
//                maxTempText.setTextColor(R.color.black);
//                feelsLikeText.setTextColor(R.color.black);
//                tempDeg.setTextColor(R.color.black);
//                minTempDeg.setTextColor(R.color.black);
//                maxTempDeg.setTextColor(R.color.black);


                if (!description.equals("")) {
                    if (description.equals("clear sky")) {
                        weatherImage.setImageResource(R.drawable.clear_sun);
                    } else if (b) {
                        weatherImage.setImageResource(R.drawable.cloudy_sun);
                    } else if (description.equals("rain") || description.equals("shower rain")) {
                        weatherImage.setImageResource(R.drawable.sour_rain);
                    } else if (description.equals("snow")) {
                        weatherImage.setImageResource(R.drawable.snow_flake);
                    } else if (description.equals("mist") || description.equals("haze")) {
                        weatherImage.setImageAlpha(0);
                    }
                }
            }
            else {
                layout.setBackgroundResource(R.mipmap.background_night);

                if (!description.equals("")) {
                    if (description.equals("clear sky")) {
                        weatherImage.setImageResource(R.drawable.clear_sky_moon);
                    } else if (b) {
                        weatherImage.setImageResource(R.drawable.cloudy_moon);
                    } else if (description.equals("rain") || description.equals("shower rain")) {
                        weatherImage.setImageResource(R.drawable.sour_rain);
                    } else if (description.equals("snow")) {
                        weatherImage.setImageResource(R.drawable.snow_flake);
                    } else if (description.equals("mist") || description.equals("haze")) {
                        weatherImage.setImageAlpha(0);
                    }
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
            temp.setText("");
            humidity.setText("");
            pressure.setText("");
            minTemp.setText("");
            maxTemp.setText("");
            feelsLike.setText("");
            weatherDescription.setText("");
            weatherInfo.setText("");
            Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
        }

    }
}