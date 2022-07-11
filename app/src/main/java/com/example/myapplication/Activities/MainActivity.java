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


    Button btncriarTabela, btnsaida, btnentradada, btnAbrirTabela, btnAbrirExtrato;

    @SuppressLint("SimpleDateFormat")

    static Date date = new Date();
    static Locale local = new Locale("pt", "BR");
    static DateFormat formato = new SimpleDateFormat("MMMM", local);
    static DateFormat formar1 = new SimpleDateFormat("dd/MM/yyyy", local);

    String dataMes = formato.format(Calendar.getInstance().getTime());
    String data = formar1.format(Calendar.getInstance().getTime());

    //final private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MMMM/yyyy");


    private File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Demo.xls");


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

        btncriarTabela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criacaoTabela();
            }
        });

        btnAbrirTabela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String csvFile = "Demo.xls";
                File yourDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), csvFile);
                String davUrl = "ms-excel:ofv|u|" + yourDir.toString();
                Uri uri = Uri.parse(davUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    public void criacaoTabela() {

        //Criação da tabela
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(dataMes);
        sheet.setDefaultColumnWidth(12);

        //Criando os estilos das celulas na cor verde (usado nos valores)
        HSSFPalette paletteVerde = ((HSSFWorkbook) wb).getCustomPalette();
        paletteVerde.setColorAtIndex(HSSFColor.LIME.index, (byte) 146, (byte) 208, (byte) 80);

        HSSFCellStyle cellStyleVerde = (HSSFCellStyle) wb.createCellStyle();
        cellStyleVerde.setFillForegroundColor(HSSFColor.LIME.index);
        cellStyleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleVerde.setBorderBottom(BorderStyle.THIN);
        cellStyleVerde.setBorderTop(BorderStyle.THIN);
        cellStyleVerde.setBorderLeft(BorderStyle.THIN);
        cellStyleVerde.setBorderRight(BorderStyle.THIN);


        //Criando os estilos das celulas na cor vermelha (usado nos valores)
        HSSFPalette paletteVermelho = ((HSSFWorkbook) wb).getCustomPalette();
        paletteVermelho.setColorAtIndex(HSSFColor.RED.index, (byte) 255, (byte) 80, (byte) 80);

        CellStyle cellStyleVermelho = wb.createCellStyle();
        cellStyleVermelho.setFillForegroundColor(HSSFColor.RED.index);
        cellStyleVermelho.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleVermelho.setBorderBottom(BorderStyle.THIN);
        cellStyleVermelho.setBorderTop(BorderStyle.THIN);
        cellStyleVermelho.setBorderLeft(BorderStyle.THIN);
        cellStyleVermelho.setBorderRight(BorderStyle.THIN);

        //Crinado estilo de alinhamento (usado somente no mês)
        CellStyle cellStyleAlinhamento = wb.createCellStyle();
        cellStyleAlinhamento.setAlignment(HorizontalAlignment.CENTER);

        //Criando estilo para o resto das celula
        CellStyle cellStyleResto = wb.createCellStyle();
        cellStyleResto.setBorderBottom(BorderStyle.THIN);
        cellStyleResto.setBorderTop(BorderStyle.THIN);
        cellStyleResto.setBorderLeft(BorderStyle.THIN);
        cellStyleResto.setBorderRight(BorderStyle.THIN);


        //Fonte das celulas de valores
        Font font = wb.createFont();
        font.setBold(true);

        //linha dos Valores
        Row rowValores = sheet.createRow(0);
        Row rowDataAtula = sheet.createRow(1);

        //celula da mês do ano
        Cell cellMes = rowValores.createCell(0);
        cellStyleVerde.setFont(font);
        cellMes.setCellStyle(cellStyleAlinhamento);
        cellMes.setCellStyle(cellStyleResto);
        cellMes.setCellValue(dataMes);

        //celula do dia atual
        Cell celldata = rowDataAtula.createCell(0);
        celldata.setCellValue(data);
        cellMes.setCellStyle(cellStyleResto);
        cellStyleAlinhamento.setFont(font);


        Cell cell1 = rowValores.createCell(1);
        cell1.setCellValue("Dinheiro");
        cell1.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        Cell cell2 = rowValores.createCell(2);
        cell2.setCellValue("Cartão");
        cell2.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        Cell cell3 = rowValores.createCell(3);
        cell3.setCellValue("Pix");
        cell3.setCellStyle(cellStyleVerde);
        cellStyleVerde.setFont(font);

        Cell cell4 = rowValores.createCell(4);
        cell4.setCellValue("Aluguel");
        cell4.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        Cell cell5 = rowValores.createCell(5);
        cell5.setCellValue("Compras");
        cell5.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        Cell cell6 = rowValores.createCell(6);
        cell6.setCellValue("Energia");
        cell6.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        Cell cell7 = rowValores.createCell(7);
        cell7.setCellValue("Água");
        cell7.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        Cell cell8 = rowValores.createCell(8);
        cell8.setCellValue("Internet");
        cell8.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);

        Cell cell9 = rowValores.createCell(9);
        cell9.setCellValue("Trasporte");
        cell9.setCellStyle(cellStyleVermelho);
        cellStyleVermelho.setFont(font);


        try {
            //if (filePath.exists()) {
            //Toast.makeText(this, "Tabela já existe", Toast.LENGTH_SHORT).show();
            //}

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            wb.write(fileOutputStream);
            Toast.makeText(this, "Tabela Criada", Toast.LENGTH_SHORT).show();

            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void janelaExtrato(View view){
        Intent extratoIntent = new Intent(MainActivity.this, ActivityExtrato.class);
        startActivity(extratoIntent);
    }
}

