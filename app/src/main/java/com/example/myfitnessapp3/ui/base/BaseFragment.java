package com.example.myfitnessapp3.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<BINDING extends ViewBinding, VM extends BaseViewModel> extends Fragment {

    protected VM viewModel;
    protected BINDING binding;

    @NonNull
    protected abstract VM createViewModel();

    @NonNull
    protected abstract BINDING createViewBinding(LayoutInflater layoutInflater);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = createViewBinding(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        viewModel = createViewModel();
    }
}
