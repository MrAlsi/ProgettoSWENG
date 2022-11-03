package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.DocenteService;
import com.university.client.model.Corso;
import com.university.client.model.Docente;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerDocente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;

public class DocenteImpl extends Database implements DocenteService {
    @Override
    public Docente getInfoPersonali(int codDocente) {
        try{
            Docente[] docenti = super.getDocenti();
            for(Docente docente: docenti){
                if(docente.codDocente==codDocente){
                    return docente;
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("errore Docente - getinfoPersonali: "+e);
            return null;
        }
    }

    public boolean creaCorso(String nome, String dataInizio, String dataFine, String descrizione, int coDocente, int docente, int esame) {
        try {
            //super.creaCorso(nome, dataInizio, dataFine, descrizione, coDocente, docente, esame);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
