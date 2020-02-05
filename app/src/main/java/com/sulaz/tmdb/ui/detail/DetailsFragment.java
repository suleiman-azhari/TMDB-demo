package com.sulaz.tmdb.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.sulaz.tmdb.R;
import com.sulaz.tmdb.databinding.FragmentDetailsBinding;
import com.sulaz.tmdb.di.base.BaseFragment;
import com.sulaz.tmdb.viewModel.DetailsFragmentViewModel;
import com.sulaz.tmdb.viewModel.ViewModelFactory;

import javax.inject.Inject;


public class DetailsFragment extends BaseFragment {
    private static final String TAG = "DetailsFragment";

    private FragmentDetailsBinding binding;

    @Inject
    ViewModelFactory viewModelFactory;

    private DetailsFragmentViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_details;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_details, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(DetailsFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);

        int id = getArguments() != null ? getArguments().getInt("id") : 0;

        observeViewModel();
        viewModel.init(id);

        //TODO use ViewModel & WebView instead
        bookOnClickListerner();
    }

    private void observeViewModel() {
        viewModel.getMovieDetails()
                .observe(getViewLifecycleOwner(), movieDetails -> {
                    Log.d(TAG, "onViewCreated: updating UI" + movieDetails.getTitle());
                    binding.setMovieDetails(movieDetails);
                });

        viewModel.getLoading()
                .observe(getViewLifecycleOwner(), isLoading ->
                        //Loading progress
                        Log.d(TAG, "loading: " + isLoading));

        viewModel.getError()
                .observe(getViewLifecycleOwner(), isError ->
                        //Alert
                        Log.d(TAG, "error: " + isError));
    }

    private void bookOnClickListerner() {
        binding.fragmentDetailsBtnBook.setOnClickListener(v -> {
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://www.cathaycineplexes.com.sg/"));
            startActivity(browserIntent);
        });
    }
}
