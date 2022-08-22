package com.example.myapplication.Activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Data {

    //Usei 2 metodos para pegar a data porque não posso fazer comparção com String

    private Locale locale = new Locale("pt", "BR");
    private DateFormat dataFormatoCompleto = new SimpleDateFormat("dd/MM/yyyy", locale);
    private DateFormat dataFormatoMes =  new SimpleDateFormat("MMMM", locale);

    private String dataCompletaEmString ;
    private String dataMesEmString;


    public Locale getLocale() {
        return locale;

    }

    public DateFormat getDataFormatoCompleto() {
        return dataFormatoCompleto;
    }

    public DateFormat getDataFormatoMes() {
        return dataFormatoMes;
    }

    public String getDataCompletaEmString() {
        dataCompletaEmString = dataFormatoCompleto.format(Calendar.getInstance().getTime());
        return dataCompletaEmString;
    }

    public String getDataMesEmString() {
        dataMesEmString = dataFormatoMes.format(Calendar.getInstance().getTime());
        return dataMesEmString;
    }
}





