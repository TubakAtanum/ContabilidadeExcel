package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.PrefConfig.PrefConfig;
import com.example.myapplication.R;
import com.example.myapplication.SQL.Dados;
import com.example.myapplication.SQL.DataBaseHelper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivitySaida extends AppCompatActivity {

    EditText edtSaida, edtCategoria;
    Button btnSaveSaida, buttonAdd, buttonFechar;
    Spinner spinner;
    ArrayList<String> list;

    //Pegar a data e setar para o formato pt-BR
    static Locale local = new Locale("pt", "BR");
    static DateFormat formato = new SimpleDateFormat("dd/MM/yyyy", local);
    static DateFormat mes = new SimpleDateFormat("MMMM", local);
    String dataDeHoje = formato.format(Calendar.getInstance().getTime());
    String dataMes = mes.format(Calendar.getInstance().getTime());

    //Pegar o diretorio do aquivo
    String nomeArquivo = "/Demo.xls";
    File yourDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + nomeArquivo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_saida);

        edtSaida = (EditText) findViewById(R.id.edittextSaida);
        edtCategoria = (EditText) findViewById(R.id.edittextCategoria);
        btnSaveSaida = (Button) findViewById(R.id.buttonSave);
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonFechar = findViewById(R.id.buttonFechar);
        list = PrefConfig.readArrayFromPref(this);

        if (list == null)
            list = new ArrayList<>();
            list.add("Aluguel");
            list.add("Compras");
            list.add("Agua");
            list.add("Energia");
            list.add("Transporte");
            list.add("Internet");

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(arrayAdapter);

        btnSaveSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarValor();
                pegarDados();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = edtCategoria.getText().toString();
                list.add(categoria);
                PrefConfig.writeArrayInPref(getApplicationContext(), list);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        buttonFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecharTabela();
                novoMes();
            }
        });
    }

    private void pegarDados(){
        String categoria = spinner.getSelectedItem().toString();
        int valor = Integer.parseInt(edtSaida.getText().toString());

        Dados dados = new Dados(-1,categoria,System.currentTimeMillis(),valor);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivitySaida.this);

        boolean sucess = dataBaseHelper.addOne(dados);
        Toast.makeText(this, "Sucess" + sucess, Toast.LENGTH_SHORT).show();
    }

    private void novoMes() {
        try {
            FileInputStream fip = new FileInputStream(yourDir);
            Workbook wb = WorkbookFactory.create(fip);
            Sheet sheet = wb.cloneSheet(0);
            try {
                wb.getSheetAt(1);
                wb.setSheetName(1, dataMes);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivitySaida.this, "Espere ate o proximo mes para criar uma nova planilha", Toast.LENGTH_SHORT).show();
            }
            fip.close();
            FileOutputStream fileOutputStream = new FileOutputStream(yourDir);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void adicionarValor() {
        try {
            //Pega o arquivo e retorna o valor digitado no edit text
            int valor = Integer.parseInt(edtSaida.getText().toString());
            FileInputStream fip = new FileInputStream(yourDir);
            Workbook wb = WorkbookFactory.create(fip);
            Sheet sheet = wb.getSheetAt(0);

            //Retorna a opção como string e posição dela como int
            String tipoSaida = spinner.getSelectedItem().toString();
            int posicao = spinner.getSelectedItemPosition();

            Font font = wb.createFont();
            font.setBold(true);

            //ULtima linha da data
            int ultimaLinha = sheet.getLastRowNum();
            Row row = sheet.getRow(ultimaLinha);
            String dataCell = row.getCell(0).getStringCellValue();

            //Criando estilo para o resto das celulas
            CellStyle vermelho = wb.getCellStyleAt(5);
            CellStyle cellStyleResto = wb.createCellStyle();
            cellStyleResto.setBorderBottom(BorderStyle.THIN);
            cellStyleResto.setBorderTop(BorderStyle.THIN);
            cellStyleResto.setBorderLeft(BorderStyle.THIN);
            cellStyleResto.setBorderRight(BorderStyle.THIN);

            //Verifica se a data de hoje é a mesma da ultima linha criada, caso seja adicionar o valor do edit text na mesma linha
            if (!dataDeHoje.equals(dataCell)) {
                Row rowData = sheet.createRow(++ultimaLinha);
                rowData.createCell(0).setCellValue(dataDeHoje);
            }
            //Cria uma coluna nova com a categoria caso não exista
            Row rowColuna = sheet.getRow(0);
            rowColuna.createCell(posicao+4).setCellValue(tipoSaida);
            rowColuna.getCell(posicao+4).setCellStyle(vermelho);
            vermelho.setFont(font);

            //Adiciona o valor embaixo da coluna
            Row rowValor = sheet.getRow(ultimaLinha);
            Cell cellValue = rowValor.getCell(posicao + 4, Row.RETURN_BLANK_AS_NULL);
            if (cellValue != null) {
                double valorCelula = cellValue.getNumericCellValue();
                row.createCell(posicao + 4).setCellValue(valor+valorCelula);
                row.getCell(posicao + 4).setCellStyle(cellStyleResto);
                Toast.makeText(MainActivitySaida.this, "Valor salvo em " + tipoSaida, Toast.LENGTH_SHORT).show();
            } else {
                row.createCell(posicao + 4).setCellValue(valor);
                row.getCell(posicao + 4).setCellStyle(cellStyleResto);
                Toast.makeText(MainActivitySaida.this, "Valor salvo em " + tipoSaida, Toast.LENGTH_SHORT).show();
            }

            //Salva o arquivo em Documentos
            fip.close();
            FileOutputStream fileOutputStream = new FileOutputStream(yourDir);
            wb.write(fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            Toast.makeText(MainActivitySaida.this, "Não foi possivel salvar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    private void fecharTabela() {
        try {
            FileInputStream fip = new FileInputStream(yourDir);
            Workbook wb = WorkbookFactory.create(fip);
            Sheet sheet = wb.getSheetAt(0);

            int ultimaLinha = sheet.getLastRowNum();
            int rowNumber = 1;

            Row rowFechamento = sheet.createRow(++ultimaLinha);
            Cell cellFechamento = rowFechamento.createCell(0);
            cellFechamento.setCellValue("Total");

            String strFormula = "SUM(E2:Z"+rowNumber+")";
            Cell cellSoma = rowFechamento.createCell(1);
            cellSoma.setCellFormula(strFormula);
            cellSoma.setCellValue(true);

            Toast.makeText(MainActivitySaida.this, "Tabela Salva", Toast.LENGTH_SHORT).show();

            fip.close();
            FileOutputStream fileOutputStream = new FileOutputStream(yourDir);
            wb.write(fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e){
            Toast.makeText(MainActivitySaida.this, "Não foi possivel fechar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}