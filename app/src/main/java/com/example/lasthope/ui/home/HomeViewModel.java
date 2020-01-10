package com.example.lasthope.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mUserInput;
    private MutableLiveData<String> mTranslationResult;



    public HomeViewModel() {
        mUserInput = new MutableLiveData<>();
        mTranslationResult = new MutableLiveData<>();
    }

    public LiveData<String> getUserInput() {
        return mUserInput;
    }
    public void setUserInput(String text) { this.mUserInput.setValue(text); }


    public LiveData<String> getTranslationResult() {
        return mTranslationResult;
    }
    public void setTranslationResult(String text) { this.mTranslationResult.setValue(text); }

}