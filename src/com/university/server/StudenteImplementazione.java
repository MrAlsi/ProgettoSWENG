package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Studente;
import com.university.client.services.StudenteService;

import javax.servlet.ServletContext;

public class StudenteImplementazione extends RemoteServiceServlet implements StudenteService {
    RepositoryInt<Studente> repositoryStudenti;
    Boolean singleton = false;
    Boolean test = false;

    public StudenteImplementazione(){ }

    //Costruttore per il test
    public StudenteImplementazione(Boolean test){
        this.test = test;
        repositoryStudenti = new RepositoryStudenteTest();
    }

    //Metodo Wrapper...
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                repositoryStudenti = new RepositoryStudenti(context);
                singleton = true;
            }
        }
    }

    @Override
    public int getNumeroStudenti() {
        chiamaDB();
        return repositoryStudenti.getAll().length;
    }

    @Override
    public boolean creaStudente(String nome, String cognome, String password, String dataNascita) {
        chiamaDB();
        repositoryStudenti.Create(new Studente(nome, cognome, creaMailStudente(nome, cognome), password, dataNascita, repositoryStudenti.getAll().length+1));
        return true;
    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String creaMailStudente(String nome, String cognome){
        chiamaDB();
        int num = 0;
        Studente[] studenti = repositoryStudenti.getAll();
        for(Studente s : studenti){
            if(s.getNome().equals(nome) && s.getCognome().equals(cognome)){
                num++;
            }
        }
        if(num>0){
            return nome.toLowerCase() + "." + cognome.toLowerCase() + num + "@studente.university.com";
        } else {
            return nome.toLowerCase() + "." + cognome.toLowerCase() + "@studente.university.com";
        }
    }

    @Override
    public boolean modificaStudente(String nome, String cognome, String mail, String password, String dataNascita, int matricola) {
        return repositoryStudenti.Update(new Studente(nome, cognome, mail, password, dataNascita, matricola));
    }

    @Override
    public boolean eliminaStudente(int matricola) {
        chiamaDB();
        return repositoryStudenti.Remove(matricola);
    }

    @Override
    public Studente[] getStudenti() {
        chiamaDB();
        return repositoryStudenti.getAll();
    }

    @Override
    public Studente getStudenteByMatricola(int matricola) {
        chiamaDB();
        return repositoryStudenti.GetById(matricola);
    }

    @Override
    public Studente loginStudente(String mail, String password) {
        chiamaDB();
        Studente[] studenti = repositoryStudenti.getAll();
        for(Studente s : studenti){
            if(s.getMail().equals(mail) && s.getPassword().equals(password)){
                return s;
            }
        }
        return null;
    }
}
