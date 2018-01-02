package com.learning.gacrta.anotherpopularmovies.utilities;

import java.util.Locale;

public class Movie {

    private String releaseDate;
    private Double voteAverage;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;

    public Movie(){
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getVoteAverageStr() {
        String str = "%.2f";
        return String.format(Locale.getDefault(), str, voteAverage);
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
