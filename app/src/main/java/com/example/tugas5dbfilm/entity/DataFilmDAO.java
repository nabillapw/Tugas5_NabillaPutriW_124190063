package com.example.tugas5dbfilm.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataFilmDAO {
    @Insert
    Long insertData(DataFilm dataFilm);

    @Query("Select * from film_db")
    List<DataFilm> getData();

    @Update
    int updateData(DataFilm item);

    @Delete
    void deleteData(DataFilm item);
}
