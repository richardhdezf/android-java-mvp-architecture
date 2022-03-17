/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.desafio9.presenter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.example.desafio9.R;
import com.example.desafio9.model.Password;
import com.example.desafio9.model.PasswordStrength;

public class PasswordPresenter implements PasswordContract.Presenter {
    public PasswordPresenter(@NonNull Password password, @NonNull PasswordContract.View passwordView) {
        this.password = password;
        this.passwordView = passwordView;
        this.passwordView.setPresenter(this);
    }

    @NonNull
    private final Password password;

    @NonNull
    private final PasswordContract.View passwordView;

    private boolean validLength(String string) {
        return string.length() > 4;
    }

    private boolean containsUpperCase(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void verify(CharSequence cs) {
        PasswordStrength passwordStrength;
        String text = cs.toString();
        password.setValue(text);

        if (!validLength(text))
            passwordStrength = PasswordStrength.WEAK;
        else if (!containsUpperCase(text))
            passwordStrength = PasswordStrength.MEDIUM;
        else if (!containsNumber(text))
            passwordStrength = PasswordStrength.STRONG;
        else passwordStrength = PasswordStrength.VERY_STRONG;

        switch (passwordStrength) {
            case WEAK:
                password.setStrength(PasswordStrength.WEAK);
                passwordView.showPassword(R.color.red, Color.WHITE, password.getStrength().name());
                break;
            case MEDIUM:
                password.setStrength(PasswordStrength.MEDIUM);
                passwordView.showPassword(R.color.yellow, Color.BLACK, password.getStrength().name());
                break;
            case STRONG:
                password.setStrength(PasswordStrength.STRONG);
                passwordView.showPassword(R.color.green, Color.BLACK, password.getStrength().name());
                break;
            case VERY_STRONG:
                password.setStrength(PasswordStrength.VERY_STRONG);
                passwordView.showPassword(R.color.blue, Color.WHITE, password.getStrength().name());
                break;
            default:
                break;
        }
    }
}
