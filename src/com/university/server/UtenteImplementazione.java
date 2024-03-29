package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.UtenteService;


public class UtenteImplementazione extends RemoteServiceServlet implements UtenteService {
    //Metodo per indirizzare al login delle varie classi
    @Override
    public String login(String mail) {
        try{
            String[] dominio = mail.split("@");
            //Switch per vedere il dominio e cercare in un determinato database
            return switch (dominio[1].toLowerCase()) {
                case "studente.university.com" -> "Studente";
                case "docente.university.com" -> "Docente";
                case "segreteria.university.com" -> "Segreteria";
                default -> "Admin";
            };
        }catch(Exception e){
            return "Admin";
        }

    }

}