package com.example.myapplication.ExtratoAdapterCardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.SQL.Dados;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ExtratoViewHolder> {
    private  ArrayList<Dados> getAll;



    public static class ExtratoViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTexViewValor;
        public TextView mTextViewTitulo;
        public TextView mTextViewData;

        public ExtratoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTexViewValor = itemView.findViewById(R.id.edtValor);
            mTextViewTitulo = itemView.findViewById(R.id.edtTitulo);
            mTextViewData = itemView.findViewById(R.id.edtData);

        }
    }

    public Adapter (ArrayList<Dados> extratolist) {
        getAll = extratolist;
    }

    @NonNull
    @Override
    public ExtratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview_extrato, parent, false);
        ExtratoViewHolder evh = new ExtratoViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExtratoViewHolder holder, int position) {
        Dados currentItem = getAll.get(position);
        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextViewTitulo.setText(currentItem.getCategoria());
        holder.mTexViewValor.setText(String.valueOf( "R$ " + currentItem.getValor()));
        holder.mTextViewData.setText(currentItem.getData());



    }

    @Override
    public int getItemCount() {
        return getAll.size();
    }
}
