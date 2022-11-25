package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Segreteria;
import com.university.client.services.SegreteriaService;

import javax.servlet.ServletContext;

public class SegreteriaImplementazione extends RemoteServiceServlet implements SegreteriaService {

    RepositoryString<Segreteria> repositorySegreteria;
    Boolean singleton = false;
    Boolean test = false;

    public SegreteriaImplementazione(){}

    public SegreteriaImplementazione(Boolean test){
        this.test = test;
        repositorySegreteria = new SegreteriaRepositoryTest();
    }

    //Metodo Wrapper
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositorySegreteria = new SegreteriaRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public boolean creaSegretaria(String nome, String cognome, String password) {
        chiamaDB();
        return  repositorySegreteria.Create(new Segreteria(nome,cognome,creaMailSegreteria(nome,cognome), password));
    }

    @Override
    public Segreteria[] getSegreteria() {
        chiamaDB();
        return repositorySegreteria.getAll();
    }

    @Override
    public boolean modificaSegreteria(String nome, String cognome, String mail, String password) {
        chiamaDB();
        return repositorySegreteria.Update(new Segreteria(nome,cognome,mail,password), mail);
    }

    @Override
    public boolean eliminaSegreteria(String mail) {
        chiamaDB();
        return repositorySegreteria.Remove(mail);
    }

    @Override
    public Segreteria getSegreteria(String mail) {
        chiamaDB();
        return repositorySegreteria.GetById(mail);
    }

    @Override
    public Segreteria loginSegreteria(String mail, String password) {
        Segreteria[] segreteria = repositorySegreteria.getAll();
        for (Segreteria s: segreteria) {
            if (s.getMail().equals(mail) && s.getPassword().equals(password)) {
                return s;
            }
        }
        return null;
    }

    public String creaMailSegreteria(String nome, String cognome){
        int num = 0;
        Segreteria[] segreteria = repositorySegreteria.getAll();
        for(Segreteria s: segreteria) {
            if (nome.equals(s.getNome()) && cognome.equals(s.getCognome())) {
                num++;
            }
        }
        if(num>0){
            return nome.toLowerCase() + "." + cognome.toLowerCase() + num + "@segreteria.university.com";
        } else {
            return nome.toLowerCase() + "." + cognome.toLowerCase() + "@segreteria.university.com";
        }
    }
}
