package com.learning.gacrta.anotherpopularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonMovieParser {
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH = "poster_path";

    public static List<Movie> getMoviesStringsFromJson(String movieListJsonString)
    throws JSONException{
        ArrayList<Movie> parsedMoviesData = new ArrayList<>();
        JSONObject movieListJson = new JSONObject(movieListJsonString);

        JSONArray movieArray = movieListJson.getJSONArray(RESULTS);

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
            currentMovie.setPosterPath(movieJsonObject.getString(POSTER_PATH));
            parsedMoviesData.add(currentMovie);
        }
        return parsedMoviesData;
    }

    public static String getJsonStringFromMovie(Movie movie) throws JSONException{
        JSONObject movieJson = new JSONObject();
        movieJson.put(TITLE, movie.getTitle());
        movieJson.put(ORIGINAL_TITLE, movie.getOriginalTitle());
        movieJson.put(USER_RATING, movie.getVoteAverage());
        movieJson.put(RELEASE_DATE, movie.getReleaseDate());
        movieJson.put(OVERVIEW, movie.getOverview());
        movieJson.put(POSTER_PATH, movie.getPosterPath());

        JSONArray dummyArray = new JSONArray();
        dummyArray.put(movieJson);
        JSONObject finalJson = new JSONObject().put(RESULTS, dummyArray);

        return finalJson.toString();
    }
}
