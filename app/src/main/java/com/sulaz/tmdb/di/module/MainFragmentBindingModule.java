package com.sulaz.tmdb.di.module;

import com.sulaz.tmdb.ui.detail.DetailsFragment;
import com.sulaz.tmdb.ui.master.MasterFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract MasterFragment provideMasterFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
