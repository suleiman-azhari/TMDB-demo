package com.sulaz.tmdb.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sulaz.tmdb.di.util.ViewModelKey;
import com.sulaz.tmdb.viewModel.MasterFragmentViewModel;
import com.sulaz.tmdb.viewModel.ViewModelFactory;
import com.sulaz.tmdb.viewModel.DetailsFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

//@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MasterFragmentViewModel.class)
    abstract ViewModel bindSharedViewModel(MasterFragmentViewModel masterFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsFragmentViewModel.class)
    abstract ViewModel bindDetailsFragmentViewModel(DetailsFragmentViewModel detailsFragmentViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
