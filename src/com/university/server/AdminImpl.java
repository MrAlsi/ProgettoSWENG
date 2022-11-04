package com.university.server;

import com.university.client.services.AdminService;
import com.university.client.model.Docente;
import com.university.client.model.Segreteria;
import com.university.client.model.Serializer.SerializerSegreteria;
import com.university.client.model.Serializer.SerializerStudente;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class AdminImpl extends Database implements AdminService {

    /**         ~~ metodi per Studente ~~       **/
    @Override
    public Studente[] getStudenti() {
        try{
            return super.getStudenti();
        } catch (Exception e){
            System.out.println("C: AdminImpl - M: getStudenti:" + e);
            return null;
        }
    }

    /**
     * Crea un nuovo oggetto studente e lo salva nel DB
     */
    @Override
    public boolean creaStudente(String nome, String cognome, String password, String dataNascita) {
        try{
            super.creaStudente(nome, cognome, creaMailStudente(nome, cognome), password, dataNascita, super.getStudenti().length+1);
            return true;
        } catch (Exception e){
            System.out.println("C: AdminImpl M: creaStudente " + e);
            return false;
        }
    }

    /**
     * Metodo che crea la mail per studenti nome.cognomeN@studente.university.com
     * controlla se esistono degli ononimi nel DB, nel caso aggiunge un numero N alla mail
     * come la mail unibo
     */
    public String creaMailStudente(String nome, String cognome){
        int num = 0;
        Studente[] studenti = super.getStudenti();
        for(int i = 0; i < studenti.length; i++){
            if(nome.equals(studenti[i].getNome()) && cognome.equals(studenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@studente.university.com";
        } else {
            return nome + "." + cognome + "@studente.university.com";
        }
    }


    /**
     * Dato una mail restituisce tutte le informazioni di quello studente
     */
    @Override
    public String[] informazioniStudente(String mail) {
        Studente[] studenti = super.getStudenti();
        String [] informazioni = new String[6];
        for(int i = 0; i < studenti.length; i++){
            if(mail.equals(studenti[i].getMail())){
                informazioni[0] = studenti[i].getNome();
                informazioni[1] = studenti[i].getCognome();
                informazioni[2] = studenti[i].getMail();
                informazioni[3] = studenti[i].getPassword();
                informazioni[4] = studenti[i].getDataNascita();
                informazioni[5] = Integer.toString(studenti[i].getMatricola());
                return informazioni;
            }
        }
        return null;
    }


    /**         ~~ metodi per Docente ~~       **/
    @Override
    public boolean creaDocente(String nome, String cognome, String password) {
        try{
            super.creaDocenti(nome, cognome, getMailDocente(nome, cognome), password, super.getDocenti().length+1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getMailDocente(String nome, String cognome){
        int num = 0;
        Docente[] docenti = getDocenti();
        for(int i = 0; i < docenti.length; i++){
            if(nome.equals(docenti[i].getNome()) && cognome.equals(docenti[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@docente.university.com";
        } else {
            return nome + "." + cognome + "@docente.university.com";
        }
    }

    @Override
    public Docente[] getDocenti(){
        return super.getDocenti();
    }

    @Override
    public String[] informazioniDocente(int codDocente) {
        try {
            Docente[] docenti = super.getDocenti();
            String[] informazioniPersonali = new String[4];
            for(Docente docente : docenti){
                if(docente.codDocente == codDocente){
                    informazioniPersonali[0] = docente.getNome();
                    informazioniPersonali[1] = docente.getCognome();
                    informazioniPersonali[2] = docente.getMail();
                    informazioniPersonali[3] = Integer.toString(docente.getCodDocente());
                    break;
                }
            }
            return informazioniPersonali;
        } catch(Exception e){
            System.out.println("C: AdminImpl  M: informazioniDocente: " + e);
            return null;
        }
    }


    /**         ~~ metodi per Segreteria ~~       **/

    @Override
    public boolean creaSegreteria(String nome, String cognome, String password) {
        try{
            super.creaSegratario(nome, cognome, creaMailSegreteria(nome, cognome), password);
            return true;
        } catch(Exception e){
            System.out.println("C: AdminImpl  M: creaSegreteria: " + e);
            return false;
        }
    }
    public String creaMailSegreteria(String nome, String cognome){
        int num = 0;
        Segreteria[] segreteria = super.getSegretari();
        for(int i = 0; i < segreteria.length; i++){
            if(nome.equals(segreteria[i].getNome()) && cognome.equals(segreteria[i].getCognome())){
                num++;
            }
        }
        if(num>0){
            return nome + "." + cognome + num + "@segreteria.university.com";
        } else {
            return nome + "." + cognome + "@segreteria.university.com";
        }
    }

    @Override
    public Segreteria[] getSegreteria(){
        return super.getSegretari();
    }

}
