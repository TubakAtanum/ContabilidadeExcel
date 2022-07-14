package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.ExtratoAdapterCardView.Adapter;
import com.example.myapplication.R;
import com.example.myapplication.SQL.Dados;
import com.example.myapplication.SQL.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityExtrato extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);


        mRecyclerView = findViewById(R.id.recyclerview);


        DataBaseHelper dbh = new DataBaseHelper(ActivityExtrato.this);
        List<Dados> getAll = dbh.getAll();

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter((ArrayList<Dados>) getAll);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

    }

}