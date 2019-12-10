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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowNews extends AppCompatActivity {

    private String API_KEY = "820dc531-f4bc-4ae6-8a66-e1e8013b47d0";
    public String location;

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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void getNews(View view) {

        String[] splitLocation = location.split(" ");

        System.out.println("I GOT HERE " + splitLocation[0]);
        new ShowNews.downloadNews().execute("https://eventregistry.org/api/v1/event/getEvents?query=%7B%22%24query%22%3A%7B%22%24and%22%3A%5B%7B%22" +
                "locationUri%22%3A%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F" + splitLocation[0] + "%22%7D%2C%7B%22lang%22%3A%22eng%22%7D%5D%7D%7D&resultType=events&eventsSortBy=date&events" +
                "Count=50&eventImageCount=1&storyImageCount=1&" +
                "apiKey="+ API_KEY);
        //new downloadNews().execute("https://catfact.ninja/fact");
    }

    public void setNews(String new_news) {
        TextView tv = findViewById(R.id.textView2);
        tv.setText(new_news);
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
            if(result.length()==0) {
                setNews("ERROR :(");
                return;
            }

            String news;
                try {
                    JSONObject json = new JSONObject(result);
                    news = json.getString("events");
                }
                catch(JSONException e) {
                    news = e.getLocalizedMessage();
                }

            setNews(news);
        }
    }

}