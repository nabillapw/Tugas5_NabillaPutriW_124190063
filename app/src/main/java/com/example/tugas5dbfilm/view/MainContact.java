package com.example.tugas5dbfilm.view;

import android.view.View;

import com.example.tugas5dbfilm.entity.AppDatabase;
import com.example.tugas5dbfilm.entity.DataFilm;

import java.util.List;

public interface MainContact {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataFilm> list);
        void editData(DataFilm item);
        void deleteData(DataFilm item);
    }

    interface presenter{
        void insertData(String judul, int tahun, String sinopsis, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String judul, int tahun, String sinopsis, int id, AppDatabase database);
        void deleteData(DataFilm dataFilm, AppDatabase database);
    }
}
