package com.sulaz.tmdb.data.discoverMovies;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscoverMovieResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<DiscoverMovie> movieList;

    @SerializedName("total_results")
    private int totalResults;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setMovieList(List<DiscoverMovie> movieList) {
        this.movieList = movieList;
    }

    public List<DiscoverMovie> getMovieList() {
        return movieList;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return
                "DiscoverMovieResponse{" +
                        "page = '" + page + '\'' +
                        ",total_pages = '" + totalPages + '\'' +
                        ",movieList = '" + movieList + '\'' +
                        ",total_results = '" + totalResults + '\'' +
                        "}";
    }
}