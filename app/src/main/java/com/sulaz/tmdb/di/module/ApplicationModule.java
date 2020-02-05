package com.sulaz.tmdb.di.module;

import com.sulaz.tmdb.BuildConfig;
import com.sulaz.tmdb.network.TMDbWebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//@Singleton
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                    .build();

            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            client.addInterceptor(logging);
        }

        return new Retrofit.Builder().baseUrl(BuildConfig.TMDB_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    @Singleton
    @Provides
    static TMDbWebService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(TMDbWebService.class);
    }
}
