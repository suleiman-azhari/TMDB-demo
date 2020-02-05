package com.sulaz.tmdb.ui.master;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sulaz.tmdb.BuildConfig;
import com.sulaz.tmdb.R;
import com.sulaz.tmdb.R2;
import com.sulaz.tmdb.data.discoverMovies.DiscoverMovie;
import com.sulaz.tmdb.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private static final String TAG = "MoviesAdapter";

    private List<DiscoverMovie> moviesList = new ArrayList<>();
    private SelectedMovieListener selectedMovieListener;

    MoviesAdapter(SelectedMovieListener selectedMovieListener) {
        this.selectedMovieListener = selectedMovieListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie,
                parent, false
        );

//        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
//                R.layout.employee_list_item, viewGroup, false);

        return new MovieHolder(itemMovieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        DiscoverMovie currentMovieItem = moviesList.get(position);

        holder.itemMovieBinding.setMovie(currentMovieItem);

//        Glide.with(holder.itemView.getContext())
//                .load(BuildConfig.TMDB_POSTER_BASE_URL + currentMovieItem.getPosterPath())
//                .into(holder.poster);
//        holder.title.setText(currentMovieItem.getTitle());
//        holder.popularity.setText(String.valueOf(currentMovieItem.getPopularity()));
//
        holder.itemView.setOnClickListener(v -> {
            selectedMovieListener.onMovieSelected(currentMovieItem.getId());
            Log.d(TAG, "onBindViewHolder: movie selected " + currentMovieItem.getTitle());
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    void setMoviesList(List<DiscoverMovie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    public void clearMovies() {
        this.moviesList.clear();
        notifyDataSetChanged();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {

        ItemMovieBinding itemMovieBinding;

//        @BindView(R2.id.item_movie_iv_poster)
//        ImageView poster;
//
//        @BindView(R2.id.item_movie_tv_title_value)
//        TextView title;
//
//        @BindView(R2.id.item_movie_iv_popularity_value)
//        TextView popularity;

        MovieHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
        }
    }
}
