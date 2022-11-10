package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.*;
import com.university.client.services.UtenteService;


public class UtenteImpl extends RemoteServiceServlet implements UtenteService {

    @Override
    public String login(String mail) {
        String[] dominio = mail.split("@");
        System.out.println(dominio[1]);
        //Switch per vedere il dominio e cercare in un determinato database
        return switch (dominio[1]) {
            case "studente.university.com" -> "Studente";
            case "docente.university.com" -> "Docente";
            case "segreteria.university.com" -> "Segreteria";
            default -> "Admin";
        };
    }

}