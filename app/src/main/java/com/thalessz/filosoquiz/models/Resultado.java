package com.thalessz.filosoquiz.models;

public class Resultado {
    private String nome;
    private int pontuacao;
    private int resultado;

    public Resultado(String nome, int pontuacao, int resultado) {
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.resultado = resultado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}
