package com.example.myapplication.SQL;

public class Dados {
    public String categoria;
    public Long createdAt;
    public int valor;
    public int id;

    public Dados(int id, String categoria, long createdAt, int valor) {
        this.categoria = categoria;
        this.createdAt = createdAt;
        this.valor = valor;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dados{" +
                "categoria='" + categoria + '\'' +
                ", createdAt=" + createdAt +
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
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
