package com.example.myapplication.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DADOS = "DADOS_TABLE";
    public static final String CATEGORIA = "CATEGORIA";
    public static final String DATA = "DATA";
    public static final String VALOR = "VALOR";
    public static final String ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "dados.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTabelStatement = "CREATE TABLE IF NOT EXISTS " + DADOS + " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORIA + " TEXT, " + DATA + " LONG, " + VALOR + " INT)";

        db.execSQL(createTabelStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Dados dados){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CATEGORIA, dados.getCategoria());
        cv.put(DATA, dados.getData());
        cv.put(VALOR, dados.getValor());

        long insert = db.insert(DADOS, null, cv);
        if (insert == -1) {
            return false;
        }
        else
            return true;
    }

    public List<Dados> getAll(){

        List<Dados> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + DADOS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do {
                int dadoID = cursor.getInt(0);
                String dadoCategoria = cursor.getString(1);
                String dadoData = cursor.getString(2);
                int dadoValor = cursor.getInt(3);

                Dados dados = new Dados(dadoID,dadoCategoria,dadoData,dadoValor);
                returnList.add(dados);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  returnList;
    }
}
