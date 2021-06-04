package com.example.tugas5dbfilm.view;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tugas5dbfilm.entity.AppDatabase;
import com.example.tugas5dbfilm.entity.DataFilm;

import java.util.List;

public class MainPresenter implements MainContact.presenter{
    private MainContact.view view;

    public MainPresenter(MainContact.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void, Long, Long> {
        private AppDatabase appDatabase;
        private DataFilm dataFilm;

        public InsertData(AppDatabase appDatabase, DataFilm dataFilm) {
            this.appDatabase = appDatabase;
            this.dataFilm = dataFilm;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(dataFilm);
        }

        @Override
        protected void onPostExecute(Long along) {
            super.onPostExecute(along);
            view.successAdd();
        }
    }

    @Override
    public void insertData(String judul, int tahun, String sinopsis, final AppDatabase database) {
        final DataFilm dataFilm = new DataFilm();
        dataFilm.setJudul(judul);
        dataFilm.setTahun(tahun);
        dataFilm.setSinopsis(sinopsis);
        new InsertData(database,dataFilm).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<DataFilm> list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void, Long, Integer> {
        private AppDatabase appDatabase;
        private DataFilm dataFilm;

        public EditData(AppDatabase appDatabase, DataFilm dataFilm) {
            this.appDatabase = appDatabase;
            this.dataFilm = dataFilm;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.dao().updateData(dataFilm);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("integer db","onPostExecute: "+integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String judul, int tahun, String sinopsis, int id, AppDatabase database) {
        final DataFilm dataFilm = new DataFilm();
        dataFilm.setJudul(judul);
        dataFilm.setTahun(tahun);
        dataFilm.setSinopsis(sinopsis);
        dataFilm.setId(id);
        new EditData(database,dataFilm).execute();
    }

    class DeleteData extends AsyncTask<Void, Long, Long> {
        private AppDatabase appDatabase;
        private DataFilm dataFilm;

        public DeleteData(AppDatabase appDatabase, DataFilm dataFilm) {
            this.appDatabase = appDatabase;
            this.dataFilm = dataFilm;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(dataFilm);
            return null;
        }

        @Override
        protected void onPostExecute(Long along) {
            super.onPostExecute(along);
            view.successDelete();
        }
    }

    @Override
    public void deleteData(final DataFilm dataFilm, final AppDatabase database) {
        new DeleteData(database,dataFilm).execute();
    }
}
