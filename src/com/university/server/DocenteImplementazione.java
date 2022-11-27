package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.services.DocenteService;

import javax.servlet.ServletContext;

public class DocenteImplementazione extends RemoteServiceServlet implements DocenteService {
    RepositoryInt<Docente> repositoryDocente;
    RepositoryString<Corso> repositoryCorso;
    Boolean singleton = false;
    Boolean test = false;

    public DocenteImplementazione() { }

    public DocenteImplementazione(Boolean test){
        this.test = test;
        repositoryDocente = new DocenteRepositoryTest();
        repositoryCorso = new CorsoRepositoryTest();
    }

    //Metodo Wrapper
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryDocente = new DocenteRepository(context);
                repositoryCorso= new CorsoRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public int getNumeroDocenti() {
        chiamaDB();
        return repositoryDocente.getAll().length;
    }

    @Override
    public Docente[] getDocenti() {
        chiamaDB();
        return repositoryDocente.getAll();
    }

    @Override
    public Docente getDocente(int codDocente) {
        chiamaDB();
        return repositoryDocente.GetById(codDocente);
    }

    @Override
    public Boolean eliminaDocente(int codDocente) {
        chiamaDB();
        return repositoryDocente.Remove(codDocente);
    }

    @Override
    public Boolean modificaDocente(String nome, String cognome, String mail, String password, int codDocente) {
        chiamaDB();
        return repositoryDocente.Update(new Docente(nome, cognome, mail, password, codDocente));
    }

    @Override
    public Boolean creaDocente(String nome, String cognome, String password) {
        chiamaDB();
        return repositoryDocente.Create(new Docente( nome, cognome,getMailDocente(nome,cognome), password, repositoryDocente.getAll().length+1));
    }

    @Override
    public Docente loginDocente(String mail, String password) {
        chiamaDB();
        for(Docente d : repositoryDocente.getAll()){
            if(d.getMail().equals(mail) && d.getPassword().equals(password)){
                return d;
            }
        }
        return null;
    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String getMailDocente(String nome, String cognome){
        chiamaDB();
        int num = 0;
        for(Docente d : repositoryDocente.getAll()){
            if(d.getNome().equals(nome) && d.getCognome().equals(cognome)){
                num++;
            }
        }
        if(num>0){
            return nome.toLowerCase() + "." + cognome.toLowerCase() + num + "@docente.university.com";
        } else {
            return nome.toLowerCase() + "." + cognome.toLowerCase() + "@docente.university.com";
        }
    }
}
