package ru.korolchuk.catalog.presentation.view;

import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

import ru.korolchuk.catalog.other.item.ShoppingItem;

public interface MainAppView extends MvpView {
    void setProgressBarVisibility(int visibility);

    void setShoppingItems(ArrayList<ShoppingItem> shoppingItems);

    void showSnack(String text);
}
