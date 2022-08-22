package com.example.myapplication.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.File;
import java.io.FileOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Data data = new Data();
    //Arquivo arquivo = new Arquivo();
    Tabela tabela = new Tabela();
    Button btncriarTabela, btnsaida, btnentradada, btnAbrirTabela, btnAbrirExtrato;
    private Data data;
    private Arquivo arquivo;

    //private File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Demo.xls");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsaida = (Button) findViewById(R.id.buttonSaida);
        btnentradada = (Button) findViewById(R.id.buttonEntrada);
        btncriarTabela = (Button) findViewById(R.id.criartabela);
        btnAbrirTabela = (Button) findViewById(R.id.abrirtabela);
        btnAbrirExtrato = (Button) findViewById(R.id.abrirExtrato);

        btncriarTabela.setOnClickListener(onClickListenerCriarTabela);
        btnAbrirTabela.setOnClickListener(onClickListenerAbrirTabela);

    }

    private View.OnClickListener onClickListenerCriarTabela =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    criacaoTabela();

                }
            };


    private View.OnClickListener onClickListenerAbrirTabela =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String davUrl = "ms-excel:ofv|u|" + arquivo.getSeuDiretorio().toString();
                    Uri uri = Uri.parse(davUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);


                }
            };

    public MainActivity() {
        this.data = new Data();
        this.arquivo = new Arquivo();
    }

    public void criacaoTabela() {

        tabela.criarTabela(data.getDataFormatoMes(), data.getDataFormatoCompleto());
        FileOutputStream resultado = arquivo.salvaArquivo(arquivo.getSeuDiretorio(), tabela.getWb());

        if (resultado == null) {
            Toast.makeText(this, "Não foi possivel criar a tabela", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tabela criada", Toast.LENGTH_SHORT).show();
        }

    }

    public void janelaSaida(View view) {
        Intent saidaIntent = new Intent(MainActivity.this, MainActivitySaida.class);
        startActivity(saidaIntent);
    }


    public void janelaEntrada(View view) {
        Intent entradaIntent = new Intent(MainActivity.this, MainActivityEntrada.class);
        startActivity(entradaIntent);
    }

    public void janelaExtrato(View view) {
        Intent extratoIntent = new Intent(MainActivity.this, ActivityExtrato.class);
        startActivity(extratoIntent);
    }
}

