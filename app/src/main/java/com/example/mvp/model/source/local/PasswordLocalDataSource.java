package com.example.mvp.model.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;
import com.example.mvp.model.source.PasswordDataSource;
import com.example.mvp.util.AppExecutors;

import java.util.List;

public class PasswordLocalDataSource implements PasswordDataSource {
    private PasswordLocalDataSource(@NonNull AppExecutors appExecutors,
                                    @NonNull PasswordDao passwordDao) {
        this.appExecutors = appExecutors;
        this.passwordDao = passwordDao;
    }


    private final PasswordDao passwordDao;
    private final AppExecutors appExecutors;

    private static volatile PasswordLocalDataSource INSTANCE;

    public static PasswordLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                      @NonNull PasswordDao passwordDao) {
        if (INSTANCE == null) {
            synchronized (PasswordLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PasswordLocalDataSource(appExecutors, passwordDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getAll(@NonNull final GetPasswordsCallback callback) {
        Runnable runnable = () -> {
            final List<Password> passwords = passwordDao.getAll();
            appExecutors.mainThread().execute(() -> callback.onPasswordsLoaded(passwords));
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void get(@NonNull final String id, @NonNull final GetPasswordCallback callback) {
        Runnable runnable = () -> {
            final Password password = passwordDao.get(id);

            appExecutors.mainThread().execute(() -> {
                if (password != null) {
                    callback.onPasswordLoaded(password);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void insert(@NonNull Password password) {
        Runnable saveRunnable = () -> passwordDao.insert(password);
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void update(@NonNull Password password) {
        Runnable saveRunnable = () -> passwordDao.update(password);
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateStrength(String id, PasswordStrength strength) {
        Runnable completeRunnable = () -> passwordDao.updateStrength(id, strength);
        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void deleteAll() {
        Runnable deleteRunnable = passwordDao::deleteAll;
        appExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void delete(@NonNull String id) {
        Runnable deleteRunnable = () -> passwordDao.delete(id);
        appExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
