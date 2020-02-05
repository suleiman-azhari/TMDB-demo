package com.sulaz.tmdb.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sulaz.tmdb.data.movieDetails.MovieDetails;
import com.sulaz.tmdb.repository.TMDbRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsFragmentViewModel extends ViewModel {
    private static final String TAG = "DetailsFragViewModel";

    private final TMDbRepository repository;
    private CompositeDisposable disposable;

    private final MutableLiveData<MovieDetails> movieDetailsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public DetailsFragmentViewModel(TMDbRepository repository) {
        this.repository = repository;
        disposable = new CompositeDisposable();
    }

    public void init(int id) {
        fetchMovieDetails(id);
    }

    private void fetchMovieDetails(int id) {
        loading.setValue(true);

        disposable.add(repository.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
                    @Override
                    public void onSuccess(MovieDetails movieDetails) {
                        error.setValue(false);
                        movieDetailsMutableLiveData.setValue(movieDetails);
                        loading.setValue(false);
                        Log.d(TAG, "onSuccess: " + movieDetails.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.setValue(true);
                        loading.setValue(false);
                        Log.d(TAG, "onError: " + e);
                    }
                }));
    }

    public LiveData<MovieDetails> getMovieDetails() {
        return movieDetailsMutableLiveData;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
