package com.example.lasthope.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tento fragment slouží pro zobrazení náhodného citátu");
    }

    public LiveData<String> getText() {
        return mText;
    }
}