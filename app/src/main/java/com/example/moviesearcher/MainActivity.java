package com.example.moviesearcher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearcher.adapters.MovieAdapter;
import com.example.moviesearcher.models.Movie;
import com.example.moviesearcher.viewmodel.MovieViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MovieViewModel viewModel;
    private MovieAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        Button btnSearch = findViewById(R.id.btnSearch);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(new ArrayList<>(), this::openMovieDetails);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        viewModel.getMovies().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                adapter.updateMovies(movies);
            } else {
                Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        btnSearch.setOnClickListener(v -> {
            String query = searchView.getQuery().toString().trim();
            if (!query.isEmpty()) {
                viewModel.searchMovies(query);
            } else {
                Toast.makeText(this, "Enter a movie name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("imdbID", movie.getImdbId());
        startActivity(intent);
    }
}