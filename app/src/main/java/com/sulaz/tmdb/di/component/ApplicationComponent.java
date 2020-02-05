package com.sulaz.tmdb.di.component;

import android.app.Application;

import com.sulaz.tmdb.di.base.BaseApplication;
import com.sulaz.tmdb.di.module.ActivityBindingModule;
import com.sulaz.tmdb.di.module.ApplicationModule;
import com.sulaz.tmdb.di.module.ContextModule;
import com.sulaz.tmdb.di.module.RxModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        ContextModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        RxModule.class,
})

public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}