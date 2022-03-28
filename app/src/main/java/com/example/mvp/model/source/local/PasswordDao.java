package com.example.mvp.model.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvp.model.Password;
import com.example.mvp.model.PasswordStrength;

import java.util.List;

@Dao
public interface PasswordDao {

    @Query("SELECT * FROM passwords")
    List<Password> getAll();

    @Query("SELECT * FROM passwords WHERE id = :id")
    Password get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Password password);

    @Update
    void update(Password password);

    @Query("UPDATE passwords SET strength = :strength WHERE id = :id")
    void updateStrength(String id, PasswordStrength strength);

    @Query("DELETE FROM passwords")
    void deleteAll();

    @Query("DELETE FROM passwords WHERE id = :id")
    void delete(String id);
}
