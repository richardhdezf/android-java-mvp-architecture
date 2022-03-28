package com.example.mvp.model.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mvp.model.Password;

@Database(entities = {Password.class}, version = 1, exportSchema = false)
@TypeConverters(StrengthConverter.class)
public abstract class PasswordDatabase extends RoomDatabase {

    private static PasswordDatabase INSTANCE;

    public abstract PasswordDao passwordDao();

    public static PasswordDatabase getInstance(Context context) {
        synchronized (PasswordDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PasswordDatabase.class, "passwordDb")
//                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }
}
