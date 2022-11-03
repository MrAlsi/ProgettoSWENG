package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.services.StudenteService;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerFrequenta;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class StudenteImpl extends Database implements StudenteService {
    /**
     * Restituisce tutte le informazioni personale di uno studente
     */
    @Override
    public String[] getInformazioniPersonali(Studente studente) {
        try {
            return new String[]{studente.getMail(), studente.getNome(), studente.getCognome(), String.valueOf(studente.getMatricola()), studente.getDataNascita()};
        } catch(Exception e){
            return null;
        }
    }

    /**
     * Restituisce tutti i corsi a cui non è iscritta la matricola
     */
    @Override
    public ArrayList<Frequenta> getCorsiDisponibili(int matricola) {
        return null;
    }

    @Override
    public boolean iscrizioneCorso(String mail) {
        return false;
    }

    @Override
    public ArrayList<Sostiene> getVoti(int matricola) {
        /*ArrayList<Sostiene> esamiSvolti = new ArrayList<>();
        Sostiene[] sostenuti = caricaEsami();
        assert sostenuti != null;
        for(Sostiene s : sostenuti){
            if(s.matricola == matricola){
                esamiSvolti.add(s);
            }
        }

        return esamiSvolti;*/
        return null;
    }


}
