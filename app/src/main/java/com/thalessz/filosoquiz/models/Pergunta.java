package com.thalessz.filosoquiz.models;

public class Pergunta {
    private String textoPergunta;
    private String[] alternativas;
    private int indiceEmpirista;
    private int indiceRacionalista;
    private int indiceNeutra;

    public Pergunta(String textoPergunta, String[] alternativas, int indiceEmpirista, int indiceRacionalista, int indiceNeutra) {
        this.textoPergunta = textoPergunta;
        this.alternativas = alternativas;
        this.indiceEmpirista = indiceEmpirista;
        this.indiceRacionalista = indiceRacionalista;
        this.indiceNeutra = indiceNeutra;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public int getIndiceEmpirista() {
        return indiceEmpirista;
    }

    public int getIndiceRacionalista() {
        return indiceRacionalista;
    }

    public int getIndiceNeutra() {
        return indiceNeutra;
    }
}
