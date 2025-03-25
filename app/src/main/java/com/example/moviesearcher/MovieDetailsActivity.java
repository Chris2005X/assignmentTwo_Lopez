package com.example.moviesearcher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import androidx.lifecycle.ViewModelProvider;
import com.example.moviesearcher.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView moviePoster = findViewById(R.id.movie_poster);
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieYear = findViewById(R.id.movie_year);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView moviePlot = findViewById(R.id.movie_plot);
        TextView movieStudio = findViewById(R.id.movie_studio);
        Button btnBack = findViewById(R.id.btn_back);

        MovieViewModel viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("imdbID")) {
            String imdbId = intent.getStringExtra("imdbID");

            viewModel.fetchMovieDetails(imdbId);

            viewModel.getSelectedMovie().observe(this, movie -> {
                if (movie != null) {
                    movieTitle.setText(movie.getTitle());
                    movieYear.setText(getString(R.string.year_placeholder, movie.getYear()));
                    movieRating.setText(getString(R.string.rating_placeholder, movie.getRating()));
                    moviePlot.setText(getString(R.string.plot_placeholder, movie.getPlot()));
                    movieStudio.setText(getString(R.string.studio_placeholder, movie.getStudio()));

                    Glide.with(this)
                            .load(movie.getPosterUrl())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(moviePoster);
                }
            });
        }

        btnBack.setOnClickListener(v -> finish());
    }
}