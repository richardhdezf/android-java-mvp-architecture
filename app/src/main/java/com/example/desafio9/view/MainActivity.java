package com.example.desafio9.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.desafio9.databinding.ActivityMainBinding;
import com.example.desafio9.model.Password;
import com.example.desafio9.model.PasswordStrength;
import com.example.desafio9.presenter.PasswordContract;
import com.example.desafio9.presenter.PasswordPresenter;

public class MainActivity extends AppCompatActivity  implements PasswordContract.View {
    private ActivityMainBinding binding;
    private PasswordContract.Presenter presenter;
    private EditText passwordEditText;
    private TextView passwordStrengthText;


    @Override
    public void setPresenter(@NonNull PasswordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPassword(int backgroundColor, int textColor, String strength) {
        passwordStrengthText.setBackgroundResource(backgroundColor);
        passwordStrengthText.setText(strength);
        passwordStrengthText.setTextColor(textColor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Password password = new Password("", PasswordStrength.INITIAL);
        presenter = new PasswordPresenter(password, this);

        passwordEditText = binding.passwordEditText;
        passwordStrengthText = binding.passwordStrengthTextView;

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                presenter.verify(cs);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }
}