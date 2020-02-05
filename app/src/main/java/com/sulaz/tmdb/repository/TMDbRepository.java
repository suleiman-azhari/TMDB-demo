package com.sulaz.tmdb.repository;

import com.sulaz.tmdb.data.movieDetails.MovieDetails;
import com.sulaz.tmdb.data.discoverMovies.DiscoverMovieResponse;
import com.sulaz.tmdb.network.TMDbWebService;

import javax.inject.Inject;

import io.reactivex.Single;

public class TMDbRepository {

    private static final String TAG = "TMDbRepository";

    private static final String RELEASE_DATE_LTE = "2020-02-02";
    private static final String RELEASE_DATE_DESC = "release_date.desc";
    private static final int PAGES_NO = 1;

    private final TMDbWebService webService;

    @Inject
    public TMDbRepository(TMDbWebService webService) {
        this.webService = webService;
    }

    public Single<DiscoverMovieResponse> getMoviesByReleaseDate() {
        return webService
                .getMoviesByReleaseDate(RELEASE_DATE_LTE, RELEASE_DATE_DESC, PAGES_NO);
    }

    public Single<MovieDetails> getMovieDetails(int id) {
        return webService.getMovieDetails(id);
    }
}
