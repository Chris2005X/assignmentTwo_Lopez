package com.example.moviesearcher.api;

import com.example.moviesearcher.models.Movie;
import com.example.moviesearcher.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("/")
    Call<MovieResponse> searchMovies(
            @Query("s") String query,
            @Query("apikey") String apiKey
    );

    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("i") String imdbId,
            @Query("apikey") String apiKey,
            @Query("plot") String plot
    );
}