package com.learning.gacrta.anotherpopularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.learning.gacrta.anotherpopularmovies.utilities.JsonMovieParser;
import com.learning.gacrta.anotherpopularmovies.utilities.Movie;

import org.json.JSONException;

public class MovieDetail extends AppCompatActivity {
    private Movie movieToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        TextView textView = findViewById(R.id.tv_example);

        Intent receivedIntent = getIntent();
        movieToShow = null;

        if (receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            textView.setText(receivedText);

            try {
                movieToShow = JsonMovieParser.getMoviesStringsFromJson(receivedText)[0];
            } catch (JSONException e) {
                e.printStackTrace();
            }

            showMovieInfo(movieToShow);
        }
    }

    private void showMovieInfo(Movie movie){

    }
}
