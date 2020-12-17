package ru.korolchuk.catalog.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface OpenScreenView extends MvpView {
    void setInputs(boolean val);

    void showToast(String text);

    void setProgressBarVisibility(int visibility);

    void mFinish();
}
