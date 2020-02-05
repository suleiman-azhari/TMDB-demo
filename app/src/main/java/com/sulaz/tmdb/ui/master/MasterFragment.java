package com.sulaz.tmdb.ui.master;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sulaz.tmdb.R;
import com.sulaz.tmdb.databinding.FragmentMasterBinding;
import com.sulaz.tmdb.ui.detail.DetailsFragment;
import com.sulaz.tmdb.di.base.BaseFragment;
import com.sulaz.tmdb.viewModel.MasterFragmentViewModel;
import com.sulaz.tmdb.viewModel.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

public class MasterFragment extends BaseFragment implements SelectedMovieListener {
    private static final String TAG = "MasterFragment";

    private Context context;

    private FragmentMasterBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshContainer;

    @Inject
    ViewModelFactory viewModelFactory;
    private MasterFragmentViewModel viewModel;

    private final MoviesAdapter moviesAdapter = new MoviesAdapter(this);

    @Override
    protected int layoutRes() {
        return R.layout.fragment_master;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_master, container, false);

        swipeRefreshContainer = binding.fragmentMasterSrl;
        recyclerView = binding.fragmentMasterRv;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(MasterFragmentViewModel.class);

        binding.setLifecycleOwner(this);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getContext(), LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(moviesAdapter);

        observeViewModel();
        viewModel.init();
    }

    @Override
    public void onMovieSelected(int id) {
        Log.d(TAG, "onMovieSelected: " + id);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);

        getBaseActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void observeViewModel() {
        viewModel.getMoviesByReleaseDate()
                .observe(getViewLifecycleOwner(), discoverMovieResponse -> {
                    Log.d(TAG, "onChanged: " + discoverMovieResponse.size());
                    moviesAdapter.setMoviesList(discoverMovieResponse);
                });

        viewModel.getLoading()
                .observe(getViewLifecycleOwner(), isLoading -> {
                    if (isLoading) {
                        moviesAdapter.clearMovies();
                    }
                    Log.d(TAG, "loading: " + isLoading);
                    swipeRefreshContainer.setRefreshing(isLoading);
                });

        viewModel.getError()
                .observe(getViewLifecycleOwner(), isError -> {
                    Log.d(TAG, "error: " + isError);
                    swipeRefreshContainer.setRefreshing(isError);
                });

        swipeRefreshContainer.setOnRefreshListener(() -> {
            Log.d(TAG, "force fetch Movies: ");
            viewModel.init();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
