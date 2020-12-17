package ru.korolchuk.catalog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.item.ShoppingItem;
import ru.korolchuk.catalog.other.adapter.ShoppingListAdapter;
import ru.korolchuk.catalog.presentation.presenter.MainAppPresenter;
import ru.korolchuk.catalog.presentation.view.MainAppView;

public class MainAppActivity extends MvpAppCompatActivity implements MainAppView {

    public final String TAG = MainAppActivity.class.getSimpleName();

    @InjectPresenter
    MainAppPresenter presenter;

    ListView shoppingItemView;
    ProgressBar progressBar;
    Toolbar toolbar;
    FloatingActionButton shoppingCart;

    ShoppingListAdapter adapter;

    private Boolean exit = false;

    private void init() {
        progressBar = findViewById(R.id.mainPageProgressBar);
        shoppingItemView = findViewById(R.id.shoppingList);
        shoppingCart = findViewById(R.id.cartMainPage);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main_app_page);
        init();

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
            }
        });

        shoppingItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent productIntent = new Intent(MainAppActivity.this, IndividualProductActivity.class);
                productIntent.putExtra("product", presenter.getShopingItem(i));
                startActivity(productIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_app_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutItem) {
            presenter.signOut();
            startActivity(new Intent(getApplicationContext(), OpenScreenActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // For exiting the application
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            showSnack("Press back again to exit");
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void setShoppingItems(ArrayList<ShoppingItem> shoppingItems) {
        adapter = new ShoppingListAdapter(getApplicationContext(), shoppingItems);
        setProgressBarVisibility(View.GONE);
        shoppingItemView.setAdapter(adapter);
    }

    @Override
    public void showSnack(String text) {
        Snackbar.make(
                findViewById(R.id.main_content),
                text,
                Snackbar.LENGTH_SHORT).show();
    }
}
