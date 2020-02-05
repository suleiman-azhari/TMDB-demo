package com.sulaz.tmdb;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.sulaz.tmdb.data.discoverMovies.DiscoverMovie;
import com.sulaz.tmdb.data.discoverMovies.DiscoverMovieResponse;
import com.sulaz.tmdb.network.RxSingleSchedulers;
import com.sulaz.tmdb.repository.TMDbRepository;
import com.sulaz.tmdb.viewModel.MasterFragmentViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MasterViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Mock
    TMDbRepository repository;
    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    Observer<List<DiscoverMovie>> moviesObserver;
    Lifecycle lifecycle;
    private MasterFragmentViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        viewModel = new MasterFragmentViewModel(repository, RxSingleSchedulers.TEST_SCHEDULER);
        viewModel.getMoviesByReleaseDate().observeForever(moviesObserver);
    }

    @Test
    public void testNull() {
        when(repository.getMoviesByReleaseDate()).thenReturn(null);
        assertNotNull(viewModel.getMoviesByReleaseDate());
        assertTrue(viewModel.getMoviesByReleaseDate().hasObservers());
    }

    @Test
    public void testRepoFetchDataSuccess() {
        List<DiscoverMovie> mockList = new ArrayList<>();
        DiscoverMovie discoverMovie = Mockito.mock(DiscoverMovie.class);
        mockList.add(discoverMovie);

        when(repository.getMoviesByReleaseDate()).thenReturn(Single.just(new DiscoverMovieResponse()));
        viewModel.init();
        verify(moviesObserver).onChanged(mockList);
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
        viewModel = null;
    }
}
