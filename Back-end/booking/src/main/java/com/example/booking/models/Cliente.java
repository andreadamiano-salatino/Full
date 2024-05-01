package com.example.booking.models;

public class Cliente extends Persona{
    private String intolleranze;

    public Cliente(String nome, String cognome, String email, String cellulare, String intolleranze) {
        super(nome, cognome, email, cellulare);
        this.intolleranze = intolleranze;
    }

    public Cliente(String intolleranze) {
        this.intolleranze = intolleranze;
    }

    public Cliente(){}

    public String getIntolleranze() {
        return intolleranze;
    }

    public void setIntolleranze(String intolleranze) {
        this.intolleranze = intolleranze;
    }
}
