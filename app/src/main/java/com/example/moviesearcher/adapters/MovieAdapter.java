package com.example.moviesearcher.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.moviesearcher.R;
import com.example.moviesearcher.models.Movie;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private final OnMovieClickListener listener;

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText(movie.getYear());

        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.moviePoster);

        holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void updateMovies(List<Movie> newMovies) {
        if (newMovies != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MovieDiffCallback(this.movies, newMovies));
            this.movies = newMovies;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    private static class MovieDiffCallback extends DiffUtil.Callback {
        private final List<Movie> oldList;
        private final List<Movie> newList;

        public MovieDiffCallback(List<Movie> oldList, List<Movie> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList != null ? oldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return newList != null ? newList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle()) &&
                    oldList.get(oldItemPosition).getYear().equals(newList.get(newItemPosition).getYear());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieYear;
        ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieYear = itemView.findViewById(R.id.movie_year);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}