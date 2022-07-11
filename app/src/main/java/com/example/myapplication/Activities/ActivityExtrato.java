package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.SQL.Dados;
import com.example.myapplication.SQL.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityExtrato extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        recyclerView = findViewById(R.id.recyclerview);
        listView = findViewById(R.id.listView);

        DataBaseHelper dbh = new DataBaseHelper(ActivityExtrato.this);
        List<Dados> getAll = dbh.getAll();
        ArrayAdapter adapter = new ArrayAdapter<Dados>(ActivityExtrato.this, android.R.layout.simple_list_item_1, getAll);
        listView.setAdapter(adapter);
    }

}