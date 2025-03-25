package com.example.moviesearcher.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.moviesearcher.models.Movie;
import com.example.moviesearcher.repository.MovieRepository;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MovieRepository repository;
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MovieViewModel() {
        repository = new MovieRepository();
    }

    public void searchMovies(String query) {
        repository.getMovies(query, new MovieRepository.OnMoviesFetchedListener() {
            @Override
            public void onSuccess(List<Movie> movieList) {
                movies.setValue(movieList);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    public void fetchMovieDetails(String imdbId) {
        repository.getMovieDetails(imdbId, new MovieRepository.OnMovieDetailsFetchedListener() {
            @Override
            public void onSuccess(Movie movie) {
                selectedMovie.setValue(movie);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}