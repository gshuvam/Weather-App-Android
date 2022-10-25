package com.shuvam.WeatherMe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;

    @SuppressLint("StaticFieldLeak")
    public class DownloadWebContent extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            ;

            String result="";

            try {

                URL url=new URL(urls[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();

                while(data!=-1){
                    char charData=(char)data;
                    result+=charData;
                    data=reader.read();
                }
                return  result;

            }

            catch (FileNotFoundException e){
                e.printStackTrace();
                return "failed";
            }

            catch (Exception e) {
                e.printStackTrace();
                textView.setText("");
                Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {



            try {

                super.onPostExecute(result);
                System.out.println("magiii "+result);
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("data", result);
                startActivity(intent);
                String message="";

                JSONObject jsonObject=new JSONObject(result);
                String weatherInfo=jsonObject.getString("weather");
                JSONArray jsonArray=new JSONArray(weatherInfo);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonPart=jsonArray.getJSONObject(i);

                    String main="";
                    String description="";

                    main=jsonPart.getString("main");
                    description=jsonPart.getString("description");


                    if(main!=""&& description!=""){
                        message="Weather: " + main + "\n" + "Description: " + description;
                    }
                    else{
                        textView.setText("");
                        Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
                    }
                }

                if(message!=""){

                    textView.setText(message);
                }
                else{
                    textView.setText("");
                    Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();

                }

            }
            catch (JSONException e) {
                e.printStackTrace();
                textView.setText("");
                Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textview);
        editText=(EditText)findViewById(R.id.editText);

    }

    public void onClick(View view){

        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(),0);

        try {
            //define your own appId here
            String appId = "";
            String encodedCityName=URLEncoder.encode(editText.getText().toString(),"UTF-8");
            DownloadWebContent downloadWebContent=new DownloadWebContent();
            downloadWebContent.execute("http://api.openweathermap.org/data/2.5/weather?q="+encodedCityName+"&APPID=" + appId + "&units=metric");

        }

        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            textView.setText("");
            Toast.makeText(getApplicationContext(),"Weather not Found!",Toast.LENGTH_LONG).show();
        }

    }
}