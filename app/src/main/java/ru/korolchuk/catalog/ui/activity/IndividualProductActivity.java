package ru.korolchuk.catalog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.item.ShoppingItem;
import ru.korolchuk.catalog.presentation.presenter.IndividualProductPresenter;
import ru.korolchuk.catalog.presentation.view.IndividualProductView;

import static ru.korolchuk.catalog.other.adapter.ShoppingCartAdapter.getLinkImage;

public class IndividualProductActivity extends MvpAppCompatActivity implements IndividualProductView {

    private final String TAG = IndividualProductActivity.class.getSimpleName();

    @InjectPresenter
    IndividualProductPresenter presenter;

    @ProvidePresenter
    public IndividualProductPresenter providePresenter(){
        ShoppingItem item = (ShoppingItem) getIntent().getSerializableExtra("product");

        return new IndividualProductPresenter(item);
    }

    Button add, sub;
    TextView name, description, quantityView, price;
    FloatingActionButton addToCart, shoppingCart;
    ImageView productImage;
    ProgressBar progressBar;

    private void init() {

        progressBar = findViewById(R.id.individualProductPageProgressBar);
        setProgressBarVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.productNameIndividualProduct);
        description = findViewById(R.id.productDescriptionIndividualProduct);
        quantityView = findViewById(R.id.quantityProductPage);
        productImage = findViewById(R.id.productImageIndividualProduct);
        price = findViewById(R.id.productPriceIndividualProduct);
        add = findViewById(R.id.incrementQuantity);
        sub = findViewById(R.id.decrementQuantity);
        addToCart = findViewById(R.id.addToCartProductPage);
        shoppingCart = findViewById(R.id.cartProductPage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_product);
        init();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.increment();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.decrement();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addToCart();
            }
        });

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
            }
        });

        setProgressBarVisibility(View.GONE);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setQuantity(String text) {
        quantityView.setText(text);
    }

    @Override
    public void setName(String text) {
        name.setText(text);
    }

    @Override
    public void setDescription(String text) {
        description.setText(text);
    }

    @Override
    public void setPrice(String text) {
        price.setText(text);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void setImage(int productId) {
        Glide.with(getApplicationContext()).load(getLinkImage(productId)).into(productImage);
    }

    @Override
    public void showSnack(String text) {
        Snackbar.make(
                findViewById(R.id.addToCartProductPage),
                text,
                Snackbar.LENGTH_SHORT).show();
    }
}