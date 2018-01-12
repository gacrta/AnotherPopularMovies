package com.learning.gacrta.anotherpopularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learning.gacrta.anotherpopularmovies.utilities.JsonMovieParser;
import com.learning.gacrta.anotherpopularmovies.utilities.Movie;
import com.learning.gacrta.anotherpopularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class MovieDetail extends AppCompatActivity {
    private TextView mErrorDetail;
    private RelativeLayout mMovieDetail;

    private TextView mMovieTitle;
    private TextView mMovieOriginalTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieUserRate;
    private TextView mMovieOverview;
    private ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mErrorDetail = findViewById(R.id.tv_error_detail);
        mMovieDetail = findViewById(R.id.rl_movie_details);

        mMovieTitle = findViewById(R.id.tv_movie_title);
        mMovieOriginalTitle = findViewById(R.id.tv_movie_original_title);
        mMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        mMovieUserRate = findViewById(R.id.tv_movie_user_rating);
        mMovieOverview = findViewById(R.id.tv_movie_overview);
        mMoviePoster = findViewById(R.id.iv_movie_poster);

        Intent receivedIntent = getIntent();
        Movie movieToShow = null;

        if (receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);

            try {
                movieToShow = JsonMovieParser.getMoviesStringsFromJson(receivedText)[0];
                showMovieInfo(movieToShow);
            } catch (JSONException e) {
                displayErrorMessage();
                e.printStackTrace();
            }
        }
    }

    private void showMovieInfo(Movie movie){
        mErrorDetail.setVisibility(View.INVISIBLE);
        mMovieDetail.setVisibility(View.VISIBLE);

        Picasso.with(getApplicationContext())
                .load(NetworkUtils.buildUrlForMoviePoster(movie.getPosterPath(),
                        NetworkUtils.POSTER_SIZE).toString()).into(mMoviePoster);

        setBoldTextToTextView(getString(R.string.tv_movie_title), movie.getTitle(), mMovieTitle);
        setBoldTextToTextView(getString(R.string.tv_original_movie_title), movie.getOriginalTitle(), mMovieOriginalTitle);
        setBoldTextToTextView(getString(R.string.tv_movie_release_date), movie.getReleaseDate(), mMovieReleaseDate);
        setBoldTextToTextView(getString(R.string.tv_movie_user_rate), movie.getVoteAverageStr(), mMovieUserRate);
        setBoldTextToTextView(getString(R.string.tv_movie_overview), movie.getOverview(), mMovieOverview);
    }

    private void setBoldTextToTextView(String textInBold, String regularText, TextView textView) {
        // based on response from
        // https://stackoverflow.com/questions/14371092/how-to-make-a-specific-text-on-textview-bold
        String allText = textInBold.concat(regularText);
        SpannableStringBuilder str = new SpannableStringBuilder(allText);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0,
                textInBold.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(str);
    }

    private void displayErrorMessage() {
        mMovieDetail.setVisibility(View.INVISIBLE);
        mErrorDetail.setVisibility(View.VISIBLE);
    }
    // TODO 1 To fetch trailers you will want to make a request to the /movie/{id}/videos endpoint.
    // TODO 2 To fetch reviews you will want to make a request to the /movie/{id}/reviews endpoint
    // TODO 3 You should use an Intent to open a youtube link in either the native app or a web browser of choice.
}
