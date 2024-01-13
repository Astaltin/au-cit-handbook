package com.au.cit.handbook.ui.phinma_education;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhinmaEducationModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PhinmaEducationModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is phinma education fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}