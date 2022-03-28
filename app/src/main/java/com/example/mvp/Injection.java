package com.example.mvp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mvp.model.source.PasswordRepository;
import com.example.mvp.model.source.local.PasswordDatabase;
import com.example.mvp.model.source.local.PasswordLocalDataSource;
import com.example.mvp.util.AppExecutors;

public class Injection {

    public static PasswordRepository providePasswordRepository(@NonNull Context context) {
        PasswordDatabase database = PasswordDatabase.getInstance(context);
        return PasswordRepository.getInstance(PasswordLocalDataSource.getInstance(new AppExecutors(),
                database.passwordDao()));
    }
}
