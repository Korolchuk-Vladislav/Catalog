package ru.korolchuk.catalog.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface IndividualProductView extends MvpView {
    void showToast(String text);

    void setQuantity(String text);

    void setName(String text);

    void setDescription(String text);

    void setPrice(String text);

    void setProgressBarVisibility(int visibility);

    void setImage(int productId);

    void showSnack(String text);
}
