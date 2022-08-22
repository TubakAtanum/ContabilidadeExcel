package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivitySaida extends AppCompatActivity{

    EditText edtSaida, edtCategoria;
    Button btnSaveSaida, buttonAdd, buttonFechar;
    Spinner spinner;
    ArrayList<String> list;
    private Data data;
    private Arquivo arquivo;
    private Tabela tabela;



    // ISSO É UMA ATROCIDADE, NÃO ME ATREVO A MECHER NISSO
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


    //Construtor
    public MainActivitySaida() {
        this.arquivo = new Arquivo();
        this.tabela = new Tabela();
        this.data = new Data();
    }



    private void pegarDados() {
        String categoria = spinner.getSelectedItem().toString();
        int valor = Integer.parseInt(edtSaida.getText().toString());

        Dados dados = new Dados(-1, categoria, data.getDataCompletaEmString(), valor);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivitySaida.this);

        boolean sucess = dataBaseHelper.addOne(dados);
        Toast.makeText(this, "Sucess" + sucess, Toast.LENGTH_SHORT).show();
    }

    private void novoMes() {
        try {
            FileInputStream fip = new FileInputStream(arquivo.getSeuDiretorio());
            Workbook wb = WorkbookFactory.create(fip);
            Sheet sheet = wb.cloneSheet(0);
            try {
                wb.getSheetAt(1);
                wb.setSheetName(1, data.getDataMesEmString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivitySaida.this, "Espere ate o proximo mes para criar uma nova planilha", Toast.LENGTH_SHORT).show();
            }
            fip.close();
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo.getSeuDiretorio());
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void adicionarValor() {

        try {

            double valor = Integer.parseInt(edtSaida.getText().toString());
            String categoria = spinner.getSelectedItem().toString();
            int posicao = spinner.getSelectedItemPosition();

            FileInputStream fip = arquivo.abreArquivo(arquivo.getSeuDiretorio());
            Workbook wb = arquivo.pegaArquivo(fip);


            tabela.adcionaValor(valor,posicao,categoria,wb);

            //Salva o arquivo em Downloads
            fip.close();
            arquivo.salvaArquivo(arquivo.getSeuDiretorio(),tabela.getWb());

            Toast.makeText(this, "Valor salvo em " + categoria, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(MainActivitySaida.this, "Não foi possivel salvar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void fecharTabela() {

        FileInputStream fip = arquivo.abreArquivo(arquivo.getSeuDiretorio());

        try {
            //FileInputStream fip = new FileInputStream(arquivo.getSeuDiretorio());
            Workbook wb = WorkbookFactory.create(fip);
            Sheet sheet = wb.getSheetAt(0);

            int ultimaLinha = sheet.getLastRowNum();
            int rowNumber = 1;

            Row rowFechamento = sheet.createRow(++ultimaLinha);
            Cell cellFechamento = rowFechamento.createCell(0);
            cellFechamento.setCellValue("Total");

            String strFormula = "SUM(E2:Z" + rowNumber + ")";
            Cell cellSoma = rowFechamento.createCell(1);
            cellSoma.setCellFormula(strFormula);
            cellSoma.setCellValue(true);

            Toast.makeText(MainActivitySaida.this, "Tabela Salva", Toast.LENGTH_SHORT).show();

            fip.close();
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo.getSeuDiretorio());
            wb.write(fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            Toast.makeText(MainActivitySaida.this, "Não foi possivel fechar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}