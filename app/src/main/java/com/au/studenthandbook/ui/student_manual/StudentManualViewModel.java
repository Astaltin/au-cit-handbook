package com.au.studenthandbook.ui.student_manual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentManualViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StudentManualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is student manual fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}