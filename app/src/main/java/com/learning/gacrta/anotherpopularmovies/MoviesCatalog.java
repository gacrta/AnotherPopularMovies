package com.learning.gacrta.anotherpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.gacrta.anotherpopularmovies.utilities.JsonMovieParser;
import com.learning.gacrta.anotherpopularmovies.utilities.Movie;
import com.learning.gacrta.anotherpopularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MoviesCatalog extends AppCompatActivity implements MovieAdapter.MovieGridClickListener{
    private ProgressBar mProgressBar;
    private LinearLayout mMoviesCatalog;
    private RecyclerView mMoviesGrid;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorTextView;
    private TextView mSortInfoTextView;
    private final boolean SORT_BY_RATE = true;
    private final boolean SORT_BY_POPULARITY = false;
    private String SORT_MODE = "SORT_MODE";

    // -- INSERT YOUR API KEY HERE --
    private final String mKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_catalog);

        int mNumberOfColumns = getNumberOfColumns();

        mProgressBar = findViewById(R.id.pb_fetching_movies);
        mMoviesCatalog = findViewById(R.id.ll_movies_catalog);
        mMoviesGrid = findViewById(R.id.rv_movies_grid);
        mErrorTextView = findViewById(R.id.tv_error_message);
        mSortInfoTextView = findViewById(R.id.tv_display_sort_type);

        mMoviesGrid.setLayoutManager(new GridLayoutManager(this, mNumberOfColumns));
        mMovieAdapter = new MovieAdapter(this);
        mMoviesGrid.setAdapter(mMovieAdapter);

        fetchMovies();
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
                    setSortTypeToPreferences(SORT_BY_RATE);
                }
                return true;
            case R.id.mi_sort_popularity:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    fetchMoviesByPopularity();
                    setSortTypeToPreferences(SORT_BY_POPULARITY);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // method to evaluate best column number based on model display
    private int getNumberOfColumns() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        return (int) (dpWidth / scalingFactor);
    }

    private class MovieFetcher extends AsyncTask<URL, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideCatalogViews();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL ... urls) {
            URL url = urls[0];
            String httpResponse = null;
            try {
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Movie> moviesFetched = null;
            if (httpResponse != null) {
                try {
                    moviesFetched = JsonMovieParser.getMoviesStringsFromJson(httpResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return moviesFetched;
        }

        @Override
        protected void onPostExecute(List<Movie> mMovies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (mMovies != null) {
                boolean sortTye = getSortTypeFromPreferences();
                if (sortTye) {
                    mSortInfoTextView.setText(R.string.sort_by_rate);
                }
                else {
                    mSortInfoTextView.setText(R.string.sort_by_popularity);
                }
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

    private void hideCatalogViews() {
        mMoviesCatalog.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void setSortTypeToPreferences(boolean sortType) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SORT_MODE, sortType);
        editor.apply();
    }

    private boolean getSortTypeFromPreferences() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SORT_MODE, SORT_BY_RATE);
    }

    private void fetchMovies() {
        boolean sortMode = getSortTypeFromPreferences();
        if (sortMode) {
            fetchMoviesByRate();
        }
        else {
            fetchMoviesByPopularity();
        }
    }

    private void fetchMoviesByRate() {
        URL url = NetworkUtils.buildUrlForTopRateMovies(mKey);
        new MovieFetcher().execute(url);
        //Toast.makeText(this, "Sort by Rate", Toast.LENGTH_SHORT).show();
    }

    private void fetchMoviesByPopularity() {
        URL url = NetworkUtils.buildUrlForPopularMovies(mKey);
        new MovieFetcher().execute(url);
        //Toast.makeText(this, "Sort by Popularity", Toast.LENGTH_SHORT).show();
    }

    public void onMovieGridClick(int position){
        //Toast.makeText(this, "Movie " + Integer.toString(position) + " clicked.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetail.class);
        Movie selectedMovie = mMovieAdapter.getMovieData(position);
        String movieString = null;

        try {
            movieString = JsonMovieParser.getJsonStringFromMovie(selectedMovie);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtra(Intent.EXTRA_TEXT, movieString);
        startActivity(intent);
    }
}
