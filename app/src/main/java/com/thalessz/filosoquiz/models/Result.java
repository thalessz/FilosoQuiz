package com.thalessz.filosoquiz.models;

public class Result {
    private String nome;
    private int pontuacao;
    private String resultado;

    public Result(String nome, int pontuacao, String resultado) {
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.resultado = resultado;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
