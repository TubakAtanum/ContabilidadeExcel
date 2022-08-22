package com.example.myapplication.SQL;

public class Dados {
    public String categoria;
    public String data;
    public int valor;
    public int id;


    public Dados(int id, String categoria, String data, int valor) {

        this.categoria = categoria;
        this.data = data;
        this.valor = valor;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dados{" +
                "categoria='" + categoria + '\'' +
                ", data=" + data +
                ", valor=" + valor +
                ", id=" + id +
                '}';
    }

    public Dados() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setdata(String createdAt) {
        this.data = createdAt;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}