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

package com.example.mvp.presenter;

import androidx.annotation.NonNull;

import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;
import com.example.mvp.model.source.PasswordDataSource;
import com.example.mvp.model.source.PasswordRepository;

public class PasswordPresenter implements PasswordContract.Presenter {
    public PasswordPresenter(@NonNull PasswordRepository passwordRepository,
                             @NonNull PasswordContract.View passwordView) {
        this.passwordRepository = passwordRepository;
        this.passwordView = passwordView;
        this.passwordView.setPresenter(this);
    }

    @NonNull
    private final PasswordRepository passwordRepository;

    @NonNull
    private final PasswordContract.View passwordView;


    private void loadPassword() {
        String passwordId = Password.DEFAULT_ID;
//        EspressoIdlingResource.increment(); // App is busy until further notice

        passwordRepository.get(passwordId, new PasswordDataSource.GetPasswordCallback() {
            @Override
            public void onPasswordLoaded(Password password) {
                passwordView.showPasswordStrength(password.getStrength());
            }

            @Override
            public void onDataNotAvailable() {
                passwordView.showLoadingPasswordError();
            }
        });
    }

    @Override
    public void start() {
        loadPassword();
    }

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

    private void updatePassword(String passwordId, PasswordStrength passwordStrength, String text) {
        Password updatedPassword = new Password(passwordId, text, passwordStrength);
        passwordRepository.update(updatedPassword);
    }

    @Override
    public void verify(String passwordId, CharSequence cs) {
        PasswordStrength passwordStrength;
        String passwordValue = cs.toString();

        if (!validLength(passwordValue))
            passwordStrength = PasswordStrength.WEAK;
        else if (!containsUpperCase(passwordValue))
            passwordStrength = PasswordStrength.MEDIUM;
        else if (!containsNumber(passwordValue))
            passwordStrength = PasswordStrength.STRONG;
        else {
            passwordStrength = PasswordStrength.VERY_STRONG;
            updatePassword(passwordId, passwordStrength, passwordValue);
            loadPassword();
            return;
        }
        passwordRepository.updateStrength(passwordId, passwordStrength);
        loadPassword();
    }
}
