package com.example.mvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvp.Injection;
import com.example.mvp.R;
import com.example.mvp.databinding.ActivityMainBinding;
import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;
import com.example.mvp.presenter.PasswordContract;
import com.example.mvp.presenter.PasswordPresenter;

public class MainActivity extends AppCompatActivity implements PasswordContract.View {
    private ActivityMainBinding binding;
    private PasswordContract.Presenter presenter;
    private TextView passwordStrengthText;


    @Override
    public void setPresenter(@NonNull PasswordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPasswordStrength(PasswordStrength strength) {
        String strengthName = strength.name();
        passwordStrengthText.setText(strengthName);
        switch (strength) {
            case WEAK:
                passwordStrengthText.setBackgroundResource(R.color.red);
                passwordStrengthText.setTextColor(Color.WHITE);
                break;
            case MEDIUM:
                passwordStrengthText.setBackgroundResource(R.color.yellow);
                passwordStrengthText.setTextColor(Color.BLACK);
                break;
            case STRONG:
                passwordStrengthText.setBackgroundResource(R.color.green);
                passwordStrengthText.setTextColor(Color.BLACK);
                break;
            case VERY_STRONG:
                passwordStrengthText.setBackgroundResource(R.color.blue);
                passwordStrengthText.setTextColor(Color.WHITE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoadingPasswordError() {
        passwordStrengthText.setBackgroundResource(R.color.white);
        passwordStrengthText.setTextColor(Color.RED);
        passwordStrengthText.setText(getString(R.string.errorLoadingPassword));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new PasswordPresenter(
                Injection.providePasswordRepository(getApplicationContext()), this);

        EditText passwordEditText = binding.passwordEditText;
        passwordStrengthText = binding.passwordStrengthTextView;

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                presenter.verify(Password.DEFAULT_ID, cs);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
}
