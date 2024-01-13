package com.au.cit.handbook.ui.developers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevelopersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DevelopersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is developers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}