package com.learning.gacrta.anotherpopularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.gacrta.anotherpopularmovies.utilities.JsonMovieParser;
import com.learning.gacrta.anotherpopularmovies.utilities.Movie;
import com.learning.gacrta.anotherpopularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MoviesCatalog extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private RecyclerView mMoviesCatalog;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorTextView;
    private final String mKey = "";
    private final boolean SORT_BY_RATE = false;
    private final boolean SORT_BY_POPULARITY = true;
    private boolean SORT_BY = SORT_BY_RATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_catalog);

        mProgressBar = findViewById(R.id.pb_fetching_movies);
        mMoviesCatalog = findViewById(R.id.ll_movies_catalog);
        mErrorTextView = findViewById(R.id.tv_error_message);

        mMovieAdapter = new MovieAdapter();

        fetchMoviesByRate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.catalog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_sort_rate:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fetchMoviesByRate();
                    SORT_BY = SORT_BY_RATE;
                }
                return true;
            case R.id.mi_sort_popularity:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fetchMoviesByPopularity();
                    SORT_BY = SORT_BY_POPULARITY;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MovieFetcher extends AsyncTask<URL, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(URL ... urls) {
            URL url = urls[0];
            String httpResponse = null;
            try {
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Movie[] moviesFetched = null;
            if (httpResponse != null) {
                try {
                    moviesFetched = JsonMovieParser.getMoviesStringsFromJson(getApplicationContext(), httpResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return moviesFetched;
        }

        @Override
        protected void onPostExecute(Movie[] mMovies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (mMovies != null) {
                showMoviesCatalog();
                mMovieAdapter.setMoviesData(mMovies);
            }
            else {
                showErrorMessage();
            }
        }
    }

    private void showErrorMessage() {
        mMoviesCatalog.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showMoviesCatalog() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMoviesCatalog.setVisibility(View.VISIBLE);
    }

    private void fetchMoviesByRate() {
        URL url = NetworkUtils.buildUrlForTopRateMovies(mKey);
        new MovieFetcher().execute(url);
        Toast.makeText(this, "Sort by Rate", Toast.LENGTH_SHORT).show();
    }
    private void fetchMoviesByPopularity() {
        URL url = NetworkUtils.buildUrlForPopularMovies(mKey);
        new MovieFetcher().execute(url);
        Toast.makeText(this, "Sort by Pop", Toast.LENGTH_SHORT).show();
    }

}
