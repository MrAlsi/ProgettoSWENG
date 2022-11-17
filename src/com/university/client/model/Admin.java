package com.university.client.model;

public class Admin extends Utente{
    public Admin(String nome, String cognome, String mail, String password) {
        super(nome, cognome, mail, password, "Admin");
    }

    public Admin() {
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
