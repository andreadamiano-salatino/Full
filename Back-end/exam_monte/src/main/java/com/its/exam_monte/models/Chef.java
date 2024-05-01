package com.its.exam_monte.models;

public class Chef extends Persona{
    private String specializzazione;

    public Chef(String nome, String cognome, String email, String cellulare, String specializzazione) {
        super(nome, cognome, email, cellulare);
        this.specializzazione = specializzazione;
    }

    public Chef(){}


    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }
}
