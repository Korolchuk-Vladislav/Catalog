package ru.korolchuk.catalog.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.korolchuk.catalog.R;
import ru.korolchuk.catalog.other.Catalog;
import ru.korolchuk.catalog.presentation.view.OpenScreenView;

@InjectViewState
public class OpenScreenPresenter extends MvpPresenter<OpenScreenView> {

    private final String TAG = OpenScreenPresenter.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public OpenScreenPresenter() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    getViewState().showToast("Sign in Successful!");
                    getViewState().setProgressBarVisibility(View.GONE);
                    getViewState().mFinish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void signIn(String email, String password){
        getViewState().setProgressBarVisibility(View.VISIBLE);
        getViewState().setInputs(false);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            getViewState().setProgressBarVisibility(View.GONE);
                            getViewState().showToast(Catalog.getAppContext().getResources().getString(R.string.auth_failed));
                            getViewState().setInputs(true);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
