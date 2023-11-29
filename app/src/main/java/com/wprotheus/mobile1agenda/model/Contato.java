package com.wprotheus.mobile1agenda.model;

import java.io.Serializable;

public class Contato implements Serializable
{
    private int id;
    private int fotoContato;
    private String nomeContato;
    private String foneContato;
    private String emailContato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFotoContato() {
        return fotoContato;
    }

    public void setFotoContato(int fotoContato) {
        this.fotoContato = fotoContato;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getFoneContato() {
        return foneContato;
    }

    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }
}