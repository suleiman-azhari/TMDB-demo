package com.sulaz.tmdb.di.module;

import com.sulaz.tmdb.network.RxSingleSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @Provides
    public RxSingleSchedulers providesScheduler() {
        return RxSingleSchedulers.DEFAULT;
    }
}
