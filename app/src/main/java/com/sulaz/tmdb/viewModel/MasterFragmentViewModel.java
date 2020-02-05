package com.sulaz.tmdb.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sulaz.tmdb.data.discoverMovies.DiscoverMovie;
import com.sulaz.tmdb.data.discoverMovies.DiscoverMovieResponse;
import com.sulaz.tmdb.network.RxSingleSchedulers;
import com.sulaz.tmdb.repository.TMDbRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class MasterFragmentViewModel extends ViewModel {
    private static final String TAG = "MasterFragmentViewModel";

    private final RxSingleSchedulers rxSingleSchedulers;
    private final TMDbRepository repository;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<DiscoverMovie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public MasterFragmentViewModel(TMDbRepository repository, RxSingleSchedulers rxSingleSchedulers) {
        this.repository = repository;
        this.rxSingleSchedulers = rxSingleSchedulers;
        disposable = new CompositeDisposable();
    }

    public void init() {
        fetchMoviesByReleaseDate();
    }

    private void fetchMoviesByReleaseDate() {
        loading.setValue(true);

        disposable.add(
                repository.getMoviesByReleaseDate()
                        .compose(rxSingleSchedulers.applySchedulers())
                        .subscribeWith(new DisposableSingleObserver<DiscoverMovieResponse>() {
                            @Override
                            public void onSuccess(DiscoverMovieResponse discoverMovieResponse) {
                                error.setValue(false);
                                movies.setValue(discoverMovieResponse.getMovieList());
                                loading.setValue(false);
                                Log.d(TAG, "onSuccess: " + discoverMovieResponse.getTotalResults());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e);
                                error.setValue(true);
                                loading.setValue(false);
                            }
                        })
        );
    }

    public LiveData<List<DiscoverMovie>> getMoviesByReleaseDate() {
        return movies;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
        Log.d(TAG, "onCleared: disposable cleared!");
    }
}
