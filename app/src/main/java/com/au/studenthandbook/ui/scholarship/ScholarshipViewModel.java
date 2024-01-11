package com.au.studenthandbook.ui.scholarship;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScholarshipViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ScholarshipViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is scholarship fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}