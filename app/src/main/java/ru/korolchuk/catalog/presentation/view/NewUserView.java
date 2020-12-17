package ru.korolchuk.catalog.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface NewUserView extends MvpView {
    void setProgressBarVisibility(int visibility);

    void showSnack(String text);

    void showToast(String text);

    void getName();

    void setViews(boolean val);

    void setPass(String text);

    void setPassVerification(String text);

    void finish();
}
