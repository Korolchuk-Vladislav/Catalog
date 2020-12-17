package ru.korolchuk.catalog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.presentation.presenter.OpenScreenPresenter;
import ru.korolchuk.catalog.presentation.view.OpenScreenView;

public class OpenScreenActivity extends MvpAppCompatActivity implements OpenScreenView{

    private static final String TAG = OpenScreenActivity.class.getSimpleName();

    @InjectPresenter
    OpenScreenPresenter presenter;

    private Button loginButton;
    private EditText user, pass;
    private TextView newUser, resetPassword;

    ProgressBar progressBar;

    void init(){
        loginButton = findViewById(R.id.loginButton);
        user = findViewById(R.id.usernameLogin);
        pass = findViewById(R.id.passwordLogin);
        setInputs(true);
        newUser = findViewById(R.id.newUserRegistration);
        resetPassword = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.loginPageProgressBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        init();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                presenter.signIn(username, password);
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Function not yet set.");
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUserReg = new Intent(OpenScreenActivity.this, NewUserActivity.class);
                startActivity(newUserReg);
                finish();
            }
        });
    }

    @Override
    public void setInputs(boolean val) {
        user.setEnabled(val);
        pass.setEnabled(val);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void mFinish(){
        startActivity(new Intent(getApplicationContext(), MainAppActivity.class));
        finish();
    }
}
