package com.setsoft.training.viewbinding.mvvm.service;

import com.setsoft.training.viewbinding.mvvm.model.CountryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountries();

}
