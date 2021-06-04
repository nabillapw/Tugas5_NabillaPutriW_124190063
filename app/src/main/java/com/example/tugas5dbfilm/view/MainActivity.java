package com.example.tugas5dbfilm.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugas5dbfilm.R;
import com.example.tugas5dbfilm.entity.AppDatabase;
import com.example.tugas5dbfilm.entity.DataFilm;
import com.example.tugas5dbfilm.view.MainContact.view;

import java.util.List;

public class MainActivity extends AppCompatActivity implements view {
    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;

    private Button btnOK;
    private RecyclerView recyclerView;
    private EditText etJudul, etTahun, etSinopsis;

    private int id = 0;
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOK = findViewById(R.id.btn_submit);
        btnOK.setOnClickListener(this);
        etJudul = findViewById(R.id.et_judul);
        etTahun = findViewById(R.id.et_tahun);
        etSinopsis = findViewById(R.id.et_sinopsis);
        recyclerView = findViewById(R.id.rc_main);

        appDatabase = AppDatabase.inidb(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainPresenter = new MainPresenter(this);

        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Berhasil input data!!!", Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil menghapus data!!!", Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etJudul.setText("");
        etTahun.setText("");
        etSinopsis.setText("");
        btnOK.setText("submit");
    }

    @Override
    public void getData(List<DataFilm> list) {
        mainAdapter = new MainAdapter(this,list, this);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void editData(DataFilm item) {
        etJudul.setText(item.getJudul());
        etTahun.setText(item.getTahun());
        etSinopsis.setText(item.getSinopsis());
        id = item.getId();
        edit = true;
        btnOK.setText("EDIT DATA");
    }

    @Override
    public void deleteData(DataFilm item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT>- Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus data")
                .setMessage("Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        resetForm();
                        mainPresenter.deleteData(item,appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnOK){
            if(etJudul.getText().toString().equals("") || etTahun.getText().toString().equals("") ||
                    etSinopsis.getText().toString().equals("")){
                Toast.makeText(this, "Harap diisi semua data!!!", Toast.LENGTH_SHORT).show();
            }else{
                if(!edit){
                    mainPresenter.insertData(etJudul.getText().toString(), Integer.parseInt(etTahun.getText().toString()),
                            etSinopsis.getText().toString(),appDatabase);
                }else{
                    mainPresenter.insertData(etJudul.getText().toString(), Integer.parseInt(etTahun.getText().toString()),
                            etSinopsis.getText().toString(),appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }
}
