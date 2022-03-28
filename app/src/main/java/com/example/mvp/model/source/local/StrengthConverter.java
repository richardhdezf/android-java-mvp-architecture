package com.example.mvp.model.source.local;

import androidx.room.TypeConverter;

import com.example.mvp.model.PasswordStrength;

public class StrengthConverter {
    @TypeConverter
    public static PasswordStrength toStrength(String value) {
        return PasswordStrength.valueOf(value);
    }

    @TypeConverter
    public static String fromStrength(PasswordStrength value) {
        return value.name();
    }
}
