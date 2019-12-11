package com.example.ellie.mobiledevassignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
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
    static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
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

    public void setNews(News new_news) {
        TextView summary = findViewById(R.id.textView2);
        TextView title = findViewById(R.id.textView7);
        TextView date = findViewById(R.id.textView9);
        TextView uri = findViewById(R.id.textView10);


        if (new_news != null) {

            if (count > new_news.getEvents().getCount() - 1) {
                title.setText("whoa there no more news");
                summary.setText("lol bye");

                count = 0;
            }
            else {
                title.setText(new_news.getEvents().getResults().get(ShowNews.count).getTitle().get(0).getEng());
                summary.setText(new_news.getEvents().getResults().get(ShowNews.count).getSummary().get(0).getEng());
                date.setText(new_news.getEvents().getResults().get(ShowNews.count).getEventDate());
                uri.setText(new_news.getEvents().getResults().get(ShowNews.count).getUri());

            }

        } else {
            title.setText("YIKES");
            date.setText("No date allowed");
            summary.setText("No news found :(");
        }

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

            Button fill = findViewById(R.id.button5);
            fill.setVisibility(View.INVISIBLE);

            TextView summary = findViewById(R.id.textView2);
            TextView title = findViewById(R.id.textView7);
            TextView date = findViewById(R.id.textView9);

            summary.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);

            setNews(news);

            count++;

        }



    }


}
