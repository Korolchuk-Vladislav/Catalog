package ru.korolchuk.catalog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.adapter.ShoppingCartAdapter;
import ru.korolchuk.catalog.other.item.ShoppingItem;
import ru.korolchuk.catalog.presentation.presenter.MainAppPresenter;
import ru.korolchuk.catalog.presentation.presenter.ShoppingCartPresenter;
import ru.korolchuk.catalog.presentation.view.ShoppingCartView;

public class ShoppingCartActivity extends MvpAppCompatActivity implements ShoppingCartView{

    private final String TAG = ShoppingCartActivity.class.getSimpleName();

    @InjectPresenter
    ShoppingCartPresenter presenter;

    TextView priceView;
    Button returnToPrev, checkOut, clearCart;
    ListView listView;

    private void init(){
        priceView =  findViewById(R.id.totalPriceCheckout);
        returnToPrev = findViewById(R.id.returnToPrevPage);
        checkOut = findViewById(R.id.checkOut);
        clearCart = findViewById(R.id.clearCart);
        listView = findViewById(R.id.shoppingCartList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_shopping_cart_window);
        init();

        returnToPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CheckOutScreenActivity.class));
            }
        });

        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnack("Cleared!");
                presenter.clearCart();

                Intent intent = new Intent(getApplicationContext(), MainAppPresenter.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void showSnack(String text) {
        Snackbar.make(findViewById(R.id.shoppingCartWindowLayout),
                text,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPriceView(String price) {
        priceView.setText(price);
    }

    @Override
    public void setItems(ArrayList<ShoppingItem> items) {
        listView.setAdapter(new ShoppingCartAdapter(getApplicationContext(), items));
    }
}
