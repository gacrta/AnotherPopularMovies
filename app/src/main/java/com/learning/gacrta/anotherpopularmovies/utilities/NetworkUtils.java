package com.learning.gacrta.anotherpopularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String MOVIEDB_POSTER_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIEDB_REVIEWS = "reviews";
    private static final String MOVIEDB_VIDEOS = "videos";
    private static final String SORT_BY_POPULARITY = "popular";
    private static final String SORT_BY_RATE = "top_rated";
    private static final String KEY_PARAM = "api_key";

    public static final String THUMBNAIL_SIZE = "w185";
    public static final String POSTER_SIZE = "w500";

    public static URL buildUrlForPopularMovies(String key) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(SORT_BY_POPULARITY)
                .appendQueryParameter(KEY_PARAM, key)
                .build();
        return getUrlFromUri(builtUri);
    }

    public static URL buildUrlForTopRateMovies(String key) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(SORT_BY_RATE)
                .appendQueryParameter(KEY_PARAM, key)
                .build();
        return getUrlFromUri(builtUri);
    }

    public static URL buildUrlForMovieVideos(String key, String movieId) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MOVIEDB_VIDEOS)
                .appendQueryParameter(KEY_PARAM, key)
                .build();
        return getUrlFromUri(builtUri);
    }

    public static URL buildUrlForMovieReviews(String key, String movieId) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MOVIEDB_REVIEWS)
                .appendQueryParameter(KEY_PARAM, key)
                .build();
        return getUrlFromUri(builtUri);
    }

    public static URL buildUrlForMoviePoster(String posterUrl, String size) {
        Uri builtUri = Uri.parse(MOVIEDB_POSTER_URL).buildUpon()
                .appendPath(size)
                .appendPath(posterUrl.substring(1)) // removing '/' from path
                .build();
        return getUrlFromUri(builtUri);
    }

    private static URL getUrlFromUri(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
