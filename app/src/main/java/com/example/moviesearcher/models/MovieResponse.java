package com.example.moviesearcher.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {
    @SerializedName("Search")
    @SuppressWarnings("unused")
    private List<Movie> movies;

    public MovieResponse() {}

    public List<Movie> getMovies() {
        return movies;
    }
}