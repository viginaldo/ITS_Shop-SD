package com.trabalho;

public class Equipamento {
    private String id, nome;
    private double preco;

    public Equipamento(String id, String nome, double preco) {
        this.id = id; this.nome = nome; this.preco = preco;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
}