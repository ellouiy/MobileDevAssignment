package com.example.ellie.mobiledevassignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowNews extends AppCompatActivity {

    private String API_KEY = "820dc531-f4bc-4ae6-8a66-e1e8013b47d0";
    public String location;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_LOCATION = "location";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            location = extras.getString("key");
        }
    }

    public void goBack(View view) {
        //This method takes the user back to the first screen of the app.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void getNews(View view) {

        String splitLocation = location.replace(" ", "_");

        System.out.println("I GOT HERE " + splitLocation);
        new ShowNews.downloadNews().execute(" https://eventregistry.org/api/v1/event/getEvents?query=%7B%22%24query%22%3A%7B%22%24and%22%3A%5B%7B%22" +
                        "conceptUri%22%3A%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F" +
                        splitLocation +
                        "%22%7D%2C%7B%22lang%22%3A%22eng%22%7D%5D%7D%7D&resultType=events&eventsSortBy=date&eventsCount=50" +
                        "&eventImageCount=1&storyImageCount=1&" +
                        "apiKey=" + API_KEY);
    }



    private class downloadNews extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url;

            try {
                url = new URL(urls[0]);
            }
            catch (MalformedURLException e) {
                return "";
            }

            StringBuilder sb = new StringBuilder();

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = bf.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                bf.close();
                connection.getInputStream().close();

                return(sb.toString());
            } catch (IOException e) {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            News news = null;
            try {
                news = mapper.readValue(result, News.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            setNews(news);
        }

        public void setNews(News new_news) {
            TextView tv = findViewById(R.id.textView2);
            if (new_news != null) {

                for(int i = 0; i < new_news.getEvents().getResults().size(); i ++) {
                    tv.setText(new_news.getEvents().getResults().get(0).getSummary().get(0).getEng());
                }
            } else {
                tv.setText("No news found :(");
            }
        }
    }
}
