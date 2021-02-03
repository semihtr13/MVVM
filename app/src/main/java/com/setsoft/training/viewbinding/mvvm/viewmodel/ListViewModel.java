package com.setsoft.training.viewbinding.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.setsoft.training.viewbinding.mvvm.service.CountriesService;
import com.setsoft.training.viewbinding.mvvm.model.CountryModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries=new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError=new MutableLiveData<>();
    public MutableLiveData<Boolean> loading=new MutableLiveData<>();

    private CountriesService countriesService=CountriesService.getInstance();
    private CompositeDisposable disposable=new CompositeDisposable();

    public ListViewModel() {
        super();
    }

    public void refresh(){
        fetchCountries();
    }

    private void fetchCountries() {
        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>(){

                            @Override
                            public void onSuccess(@NonNull List<CountryModel> countryModels) {
                                countries.setValue(countryModels);
                                countryLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                countryLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();

                            }
                        })
        );
//     List<CountryModel> countryList= Arrays.asList(new CountryModel("Almanya","Berlin",""),
//                new CountryModel("Almanya","Berlin",""),
//                new CountryModel("Almanya","Berlin",""));
//     countries.setValue(countryList);
//     countryLoadError.setValue(false);
//     loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
