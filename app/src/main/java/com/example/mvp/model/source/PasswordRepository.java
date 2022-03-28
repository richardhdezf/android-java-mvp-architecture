package com.example.mvp.model.source;

import androidx.annotation.NonNull;

import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;

import java.util.ArrayList;
import java.util.List;

public class PasswordRepository implements PasswordDataSource {
    private PasswordRepository(@NonNull PasswordDataSource tasksLocalDataSource) {
        localDataSource = tasksLocalDataSource;
        setup();
    }

    private void setup() {
        localDataSource.deleteAll();
        String passwordId = Password.DEFAULT_ID;
        Password password = new Password(passwordId, "", PasswordStrength.INITIAL);
        localDataSource.insert(password);
    }

    private final PasswordDataSource localDataSource;

    private static PasswordRepository INSTANCE = null;

    public static PasswordRepository getInstance(PasswordDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PasswordRepository(tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getAll(@NonNull final GetPasswordsCallback callback) {
        localDataSource.getAll(new GetPasswordsCallback() {
            @Override
            public void onPasswordsLoaded(List<Password> passwords) {
                callback.onPasswordsLoaded(new ArrayList<>(passwords));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void get(@NonNull final String id, @NonNull final GetPasswordCallback callback) {
        localDataSource.get(id, new GetPasswordCallback() {
            @Override
            public void onPasswordLoaded(Password password) {
                callback.onPasswordLoaded(password);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void insert(@NonNull Password password) {
        localDataSource.insert(password);
    }

    @Override
    public void update(@NonNull Password password) {
        localDataSource.update(password);
    }

    @Override
    public void updateStrength(String id, PasswordStrength strength) {
        localDataSource.updateStrength(id, strength);
    }

    @Override
    public void deleteAll() {
        localDataSource.deleteAll();
    }

    @Override
    public void delete(@NonNull String id) {
        localDataSource.delete(id);
    }
}
