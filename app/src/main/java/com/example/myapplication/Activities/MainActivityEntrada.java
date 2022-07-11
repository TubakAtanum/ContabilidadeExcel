package com.example.myapplication.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivityEntrada extends AppCompatActivity {


    EditText edtEntrada;
    Button btnSaveEntrada;
    RadioButton rbCartao;
    RadioButton rbPix;
    RadioButton rbDinheiro;
    RadioGroup rgEntrada;
    ArrayList<String> list;

    //Pegar a data e setar para o formato pt-BR
    Locale local = new Locale("pt", "BR");
    DateFormat formarto = new SimpleDateFormat("dd/MM/yyyy", local);
    DateFormat formatoMes = new SimpleDateFormat("MM", local);

    String dataDeHoje = formarto.format(Calendar.getInstance().getTime());

    int dataMes = Integer.parseInt(formatoMes.format((Calendar.getInstance()).getTime()));

    String ultimoDia = String.valueOf(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));


    //Pegar o aquivo
    String nomeArquivo = "/Demo.xls";
    File yourDir = new File(Environment.getExternalStorageDirectory() + nomeArquivo);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entrada);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        // nomenclatura das Views
        rbCartao = (RadioButton) findViewById(R.id.radioBtnCartao);
        rbPix = (RadioButton) findViewById(R.id.radioBtnPix);
        rbDinheiro = (RadioButton) findViewById(R.id.radioBtnDinheiro);
        rgEntrada = (RadioGroup) findViewById(R.id.radiogroupEntrada);
        btnSaveEntrada = (Button) findViewById(R.id.buttonSave);
        edtEntrada = (EditText) findViewById(R.id.edittextEntrada);
        btnSaveEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    int valor = Integer.parseInt(edtEntrada.getText().toString());
                    FileInputStream fip = new FileInputStream(yourDir);


                    Workbook wb = WorkbookFactory.create(fip);
                    Sheet sheet = wb.getSheetAt(0);


                    //ULtima linha da data
                    int ultimaLinha = sheet.getLastRowNum();
                    Row row = sheet.getRow(ultimaLinha);
                    String dataCell = row.getCell(0).getStringCellValue();


                    //Criando estilo para o resto das celula
                    CellStyle cellStyleResto = wb.createCellStyle();
                    cellStyleResto.setBorderBottom(BorderStyle.THIN);
                    cellStyleResto.setBorderTop(BorderStyle.THIN);
                    cellStyleResto.setBorderLeft(BorderStyle.THIN);
                    cellStyleResto.setBorderRight(BorderStyle.THIN);


                    if (!dataDeHoje.equals(dataCell)) {
                        Row row1 = sheet.createRow(++ultimaLinha);
                        row1.createCell(0).setCellValue(dataDeHoje);
                    }


                    switch (rgEntrada.getCheckedRadioButtonId()) {
                        case R.id.radioBtnDinheiro:
                            int cellValorDinehiro = (int) row.getCell(1).getNumericCellValue();
                            row.createCell(1).setCellValue(valor + cellValorDinehiro);
                            row.getCell(1).setCellStyle(cellStyleResto);
                            Toast.makeText(MainActivityEntrada.this, "Valor salvo em Dinheiro", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radioBtnCartao:
                            int cellValorCartao = (int) row.getCell(1).getNumericCellValue();
                            row.createCell(2).setCellValue(valor + cellValorCartao);
                            row.getCell(2).setCellStyle(cellStyleResto);
                            Toast.makeText(MainActivityEntrada.this, "Valor salvo em Cartão", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radioBtnPix:
                            int cellValorPix = (int) row.getCell(1).getNumericCellValue();
                            row.createCell(3).setCellValue(valor + cellValorPix);
                            row.getCell(3).setCellStyle(cellStyleResto);
                            Toast.makeText(MainActivityEntrada.this, "Valor salvo em Pix", Toast.LENGTH_SHORT).show();
                            break;

                    }

                    fip.close();

                    FileOutputStream fileOutputStream = new FileOutputStream(yourDir);
                    wb.write(fileOutputStream);
                    fileOutputStream.close();


                } catch (Exception e) {
                    Toast.makeText(MainActivityEntrada.this, "Não foi possivel salvar", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        });


    }


}






