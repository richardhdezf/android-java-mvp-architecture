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

package com.example.mvp.model.source;

import androidx.annotation.NonNull;

import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;

import java.util.List;

public interface PasswordDataSource {

    interface GetPasswordsCallback {

        void onPasswordsLoaded(List<Password> passwords);

        void onDataNotAvailable();
    }

    interface GetPasswordCallback {

        void onPasswordLoaded(Password password);

        void onDataNotAvailable();
    }

    void getAll(@NonNull GetPasswordsCallback callback);
    void get(@NonNull String id, @NonNull GetPasswordCallback callback);

    void insert(@NonNull Password password);

    void update(@NonNull Password password);

    void updateStrength(String id, PasswordStrength strength);

    void deleteAll();
    void delete(@NonNull String id);
}
