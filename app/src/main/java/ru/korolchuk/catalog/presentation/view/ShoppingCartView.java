package ru.korolchuk.catalog.presentation.view;

import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

import ru.korolchuk.catalog.other.item.ShoppingItem;

public interface ShoppingCartView extends MvpView {
    void showSnack(String text);

    void setPriceView(String price);

    void setItems(ArrayList<ShoppingItem> items);
}
