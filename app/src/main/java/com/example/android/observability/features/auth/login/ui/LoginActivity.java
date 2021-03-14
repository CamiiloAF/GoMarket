package com.example.android.observability.features.auth.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.observability.Injection;
import com.example.android.observability.core.CurrentUser;
import com.example.android.observability.features.auth.sign_up.ui.SignUpActivity;
import com.example.android.observability.features.home.ui.HomeActivity;
import com.example.android.persistence.R;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private EditText mUserNameInput;
    private EditText mPasswordInput;
    private Button mLoginButton;
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserNameInput = findViewById(R.id.editTextUserName);
        mPasswordInput = findViewById(R.id.editTextPassword);

        mLoginButton = findViewById(R.id.login_button);
        TextView mGoToSignUpText = findViewById(R.id.txt_go_to_sign_up);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(Injection.provideUserDataSource(this));
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(LoginViewModel.class);

        mLoginButton.setOnClickListener(v -> doLogin());

        mGoToSignUpText.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void doLogin() {
        String userName = mUserNameInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        mLoginButton.setEnabled(false);

        mDisposable.add(mViewModel.getUserByUserNameAndPassword(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((userModel) -> {
                            CurrentUser.setUser(userModel);
                            Intent intent = new Intent(this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        },
                        throwable -> {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Bad credentials",
                                    Snackbar.LENGTH_SHORT).show();

                            mLoginButton.setEnabled(true);
                        }));
    }
}
