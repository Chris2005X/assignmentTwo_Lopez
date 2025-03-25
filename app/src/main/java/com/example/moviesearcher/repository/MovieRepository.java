package com.example.moviesearcher.repository;

import android.util.Log;
import com.example.moviesearcher.models.Movie;
import androidx.annotation.NonNull;
import com.example.moviesearcher.api.MovieApiService;
import com.example.moviesearcher.api.RetrofitClient;
import com.example.moviesearcher.models.MovieResponse;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final MovieApiService apiService;

    public MovieRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(MovieApiService.class);
    }

    public void getMovies(String query, OnMoviesFetchedListener listener) {
        String apiKey = RetrofitClient.getApiKey();
        Call<MovieResponse> call = apiService.searchMovies(query, apiKey);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body().getMovies());
                } else {
                    listener.onFailure("Failed to fetch movies.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void getMovieDetails(String imdbId, OnMovieDetailsFetchedListener listener) {
        String apiKey = RetrofitClient.getApiKey();
        Call<Movie> call = apiService.getMovieDetails(imdbId, apiKey, "full");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Movie Details: " + new Gson().toJson(response.body()));
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Failed to fetch movie details.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public interface OnMoviesFetchedListener {
        void onSuccess(List<Movie> movieList);
        void onFailure(String error);
    }

    public interface OnMovieDetailsFetchedListener {
        void onSuccess(Movie movie);
        void onFailure(String error);
    }
}