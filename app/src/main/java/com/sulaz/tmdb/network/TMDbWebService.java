package com.sulaz.tmdb.network;

import com.sulaz.tmdb.data.discoverMovies.DiscoverMovieResponse;
import com.sulaz.tmdb.data.movieDetails.MovieDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbWebService {

    @GET("discover/movie")
    Single<DiscoverMovieResponse> getMoviesByReleaseDate(
            @Query("primary_release_date.lte") String releaseDate,
            @Query("sort") String sort,
            @Query("page") int page
    );

    @GET("movie/{id}")
    Single<MovieDetails> getMovieDetails(@Path("id") int id);
}
