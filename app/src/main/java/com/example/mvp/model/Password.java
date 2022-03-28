package com.example.mvp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "passwords")
public class Password {
    public Password(@NonNull String id, String value, PasswordStrength strength) {
        this.id = id;
        this.value = value;
        this.strength = strength;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "strength")
    private PasswordStrength strength;

    public static final String DEFAULT_ID = "DEFAULT_PASSWORD_ID";

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String value) {
        this.id = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PasswordStrength getStrength() {
        return strength;
    }

    public void setStrength(PasswordStrength strength) {
        this.strength = strength;
    }
}
