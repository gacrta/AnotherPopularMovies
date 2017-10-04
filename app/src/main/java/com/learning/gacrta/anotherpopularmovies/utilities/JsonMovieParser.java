package com.learning.gacrta.anotherpopularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gabrielc.almeida on 24/08/2017.
 */

public class JsonMovieParser {
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String OVERVIEW = "overview";

    public static Movie[] getMoviesStringsFromJson(Context context, String movieListJsonString)
    throws JSONException{
        Movie[] parsedMoviesData;
        JSONObject movieListJson = new JSONObject(movieListJsonString);

        JSONArray movieArray = movieListJson.getJSONArray(RESULTS);

        parsedMoviesData = new Movie[movieArray.length()];
        Movie currentMovie;
        JSONObject movieJsonObject;

        for (int i = 0; i < movieArray.length(); i++) {
            currentMovie = new Movie();
            movieJsonObject = movieArray.getJSONObject(i);
            currentMovie.setTitle(movieJsonObject.getString(TITLE));
            currentMovie.setOriginalTitle(movieJsonObject.getString(ORIGINAL_TITLE));
            currentMovie.setVoteAverage(movieJsonObject.getDouble(USER_RATING));
            currentMovie.setReleaseDate(movieJsonObject.getString(RELEASE_DATE));
            currentMovie.setOverview(movieJsonObject.getString(OVERVIEW));
            parsedMoviesData[i] = currentMovie;
        }
        return parsedMoviesData;
    }
}
