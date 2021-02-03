package com.setsoft.training.viewbinding.mvvm.service;

import com.setsoft.training.viewbinding.mvvm.model.CountryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static final String BASE_URL="https://raw.githubusercontent.com";

    private static CountriesService countryService=null;

    private CountriesApi countriesApi= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi.class);

    private CountriesService() {

    }

    public static CountriesService getInstance(){
        if(countryService==null){
            synchronized (CountriesService.class){
                if(countryService==null){
                    countryService=new CountriesService();
                }
            }
        }
        return countryService;
    }

    public Single<List<CountryModel>> getCountries(){
        return countriesApi.getCountries();
    }
}
