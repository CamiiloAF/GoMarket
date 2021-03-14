package com.example.android.observability.features.auth.sign_up.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.observability.Injection;
import com.example.android.observability.features.auth.login.ui.LoginViewModel;
import com.example.android.observability.features.auth.login.ui.ViewModelFactory;
import com.example.android.persistence.R;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.observability.Injection.provideUserDataSource;

public class SignUpActivity extends AppCompatActivity {
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private EditText mUserNameInput;
    private EditText mPasswordInput;
    private LoginViewModel mViewModel;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameInput = findViewById(R.id.editTextUserNameRegisterUser);
        mPasswordInput = findViewById(R.id.editTextPasswordRegisterUser);

        mSignUpButton = findViewById(R.id.signUpButton);
        TextView mGoToLoginText = findViewById(R.id.goToLoginText);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(provideUserDataSource(this));
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(LoginViewModel.class);

        mSignUpButton.setOnClickListener(v -> registerUser());

        mGoToLoginText.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String userName = mUserNameInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        mSignUpButton.setEnabled(false);

        mDisposable.add(mViewModel.registerUser(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::finish,
                        throwable -> {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Has been occurred an error. please try again",
                                    Snackbar.LENGTH_SHORT).show();

                            mSignUpButton.setEnabled(false);
                        }));
    }
}