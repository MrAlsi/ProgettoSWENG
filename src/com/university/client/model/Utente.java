package com.university.client.model;


import java.io.Serializable;

public class Utente implements Serializable {
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private String tipo;

    public Utente() {
    }
    public Utente(String nome, String cognome, String mail, String password, String tipo){
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.tipo = tipo;

    }
    public String getNome(){
        return this.nome;
    }
    public String getCognome(){
        return this.cognome;
    }
    public String getMail(){
        return this.mail;
    }
    public String getPassword(){
        return password;
    }
    public String getTipo() {
        return tipo;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username){
        this.mail = username;
    }

    @Override
    public String toString(){
        return this.nome+" "+this.cognome+" "+this.mail;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null){
            if(obj.getClass() == this.getClass()){
                Utente other = (Utente) obj;
                if(other.getMail().equals(this.mail)){
                    return true;
                }
            }
        }
        return false;
    }

}
