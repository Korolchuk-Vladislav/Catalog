package ru.korolchuk.catalog.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.presentation.presenter.NewUserPresenter;
import ru.korolchuk.catalog.presentation.view.NewUserView;

public class NewUserActivity extends MvpAppCompatActivity implements NewUserView{

    private static final String TAG = OpenScreenActivity.class.getSimpleName();

    @InjectPresenter
    NewUserPresenter presenter;

    ProgressBar progressBar;
    private Button mRegister;
    private EditText username, pass, passVerification, firstname, lastname;

    private void init() {
        username = findViewById(R.id.usernameRegistration);
        pass = findViewById(R.id.passwordRegistration);
        passVerification = findViewById(R.id.passwordRegistrationConfirmation);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        setViews(true);
        progressBar = findViewById(R.id.registrationPageProgressBar);
        mRegister = findViewById(R.id.registerButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        init();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViews(false);
                String email = username.getText().toString();
                String password = pass.getText().toString();
                String passwordVerification = passVerification.getText().toString();
                presenter.onClick(email, password, passwordVerification);
            }
        });

    }

    @Override
    public void finish() {
        startActivity(new Intent(getApplicationContext(), OpenScreenActivity.class));
        super.finish();
    }

    @Override
    public void setViews(boolean val) {
        username.setEnabled(val);
        pass.setEnabled(val);
        firstname.setEnabled(val);
        lastname.setEnabled(val);
        passVerification.setEnabled(val);
    }

    @Override
    public void setPass(String text) {
        pass.setText(text);
    }

    @Override
    public void setPassVerification(String text) {
        passVerification.setText(text);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void showSnack(String text) {
        Snackbar.make(findViewById(R.id.newUserPage), text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getName() {
        presenter.setName(firstname.getText().toString() + " " + lastname.getText().toString());
    }
}
