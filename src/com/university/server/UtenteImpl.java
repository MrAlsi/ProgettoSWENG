package com.university.server;

import com.university.client.model.*;
import com.university.client.services.UtenteService;


public class UtenteImpl extends Database implements UtenteService {

    @Override
    public Utente login(String mail, String password) {
        String[] dominio = mail.split("@");
        System.out.println(dominio[1]);
        //Switch per vedere il dominio e cercare in un determinato database
        return switch (dominio[1]) {
            case "studente.university.com" -> cercaStudente(mail, password);
            case "docente.university.com" -> cercaDocente(mail, password);
            default -> cercaSegreteria(mail, password);
            //default -> cercaAdmin(mail, password);
        };
    }

    @Override
    public Studente cercaStudente(String mail, String password) {
        Studente[] studenti = super.getStudenti();
        for (Studente studente : studenti) {
            if (mail.equals(studente.getMail()) && password.equals(studente.getPassword()))
                return studente;
        }
        return null;
    }

    @Override
    public Docente cercaDocente(String mail, String password){
        Docente[] docenti = super.getDocenti();
        for (Docente docente : docenti) {
            if (mail.equals(docente.getMail()) && password.equals(docente.getPassword()))
                return docente;
        }
        return null;
    }
    @Override
    public Segreteria cercaSegreteria(String mail, String password){
        Segreteria[] segreteria = super.getSegretari();
        for (Segreteria value : segreteria) {
            if (mail.equals(value.getMail()) && password.equals(value.getPassword()))
                return value;
        }
        return null;
    }

   /* public Admin cercaAdmin(String mail, String password){
        if(mail == "admin" && password == "admin"){
            return
        }
    }*/
}