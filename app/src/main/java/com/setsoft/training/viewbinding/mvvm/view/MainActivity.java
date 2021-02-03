package com.setsoft.training.viewbinding.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.setsoft.training.viewbinding.mvvm.adapter.CountryListAdapter;
import com.setsoft.training.viewbinding.mvvm.databinding.ActivityMainBinding;
import com.setsoft.training.viewbinding.mvvm.viewmodel.ListViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding=null;
    private ListViewModel viewModel=null;
    private CountryListAdapter adapter=new CountryListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        viewModel= ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        binding.countriesList.setLayoutManager(new LinearLayoutManager(this));
        binding.countriesList.setAdapter(adapter);

        binding.swipeRefreshLayout.setOnRefreshListener(()->{
            viewModel.refresh();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        observerViewModel();

    }

    private void observerViewModel() {
        viewModel.countries.observe(this,countryModels -> {
            if(!countryModels.isEmpty()){
                binding.countriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this,isError->{
            if(isError!=null){
                binding.listError.setVisibility(isError?View.VISIBLE:View.GONE);
            }
        });

        viewModel.loading.observe(this,isLoading->{
            if(isLoading!=null){
                binding.loadingView.setVisibility(isLoading?View.VISIBLE:View.GONE);
            }
            if (isLoading){
                binding.countriesList.setVisibility(View.GONE);
                binding.listError.setVisibility(View.GONE);
            }
        });
    }
}