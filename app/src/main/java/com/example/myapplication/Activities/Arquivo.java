package com.example.myapplication.Activities;

import android.os.Environment;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Arquivo {

    private File seuDiretorio;
    private FileOutputStream salvaArquivo;
    private FileInputStream arquivo;


    public File getSeuDiretorio() {
        seuDiretorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Demo.xls");
        return seuDiretorio;
    }


    public FileInputStream abreArquivo(File diretorioArquivo ) {
            try {
                arquivo = new FileInputStream(seuDiretorio);
                return arquivo;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
    }

    public Workbook pegaArquivo(FileInputStream arquivo){
        try {
            Workbook wb = WorkbookFactory.create(arquivo);
            return wb;
        } catch (IOException |InvalidFormatException e) {
            e.printStackTrace();
        }return null;
    }

    public FileOutputStream salvaArquivo(File diretorioArquivo, Workbook wb) {
            try {
                salvaArquivo = new FileOutputStream(seuDiretorio);
                wb.write(salvaArquivo);
                salvaArquivo.close();
                return salvaArquivo;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

    }
}
