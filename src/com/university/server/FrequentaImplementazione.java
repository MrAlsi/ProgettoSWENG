package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;
import com.university.client.model.Serializer.SerializerCorso;
import com.university.client.model.Serializer.SerializerSostiene;
import com.university.client.model.Sostiene;
import com.university.client.services.FrequentaService;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;

public class FrequentaImplementazione extends RemoteServiceServlet implements FrequentaService {
    RepositoryIntString<Frequenta> frequentaRepository;
    Boolean singleton = false;
    Boolean test = false;

    RepositoryString<Corso> corsoRepository;

    public FrequentaImplementazione(){ }

    //Costruttore per il test
    public FrequentaImplementazione(Boolean test){
        this.test = test;
        frequentaRepository = new FrequentaRepositoryTest();
    }

    //Metodo Wrapper...
    public void chiamaDB(){
        // Singleton è un modello di progettazione creazionale che consente di garantire che una classe abbia una
        // sola istanza, fornendo al tempo stesso un punto di accesso globale a questa istanza.
        if(!singleton){
            if(!test){
                ServletContext context = this.getServletContext();
                frequentaRepository = new FrequentaRepository(context);
                corsoRepository = new CorsoRepository(context);
                singleton = true;
            }
        }
    }

    @Override
    public Frequenta[] getFrequenta() {
        chiamaDB();
        return frequentaRepository.getAll();
    }

    @Override
    public Corso[] getCorsiDisponibili(int matricola) {
        chiamaDB();
        ArrayList<Corso> corsiDisponibili= new ArrayList<>();
        for(Frequenta f: frequentaRepository.getAll()){
            if(f.getMatricola()!=matricola){
                corsiDisponibili.add(traduciCorso(f));
            }
        }
        return corsiDisponibili.toArray(new Corso[0]);
    }

    @Override
    public ArrayList<Frequenta> getMieiCorsi(int matricola) {
        return null;
    }

    @Override
    public Corso[] getCorsiStudente(int matricola) {
        return null;
    }

    @Override
    public ArrayList<Frequenta> getStudentiIscritti(String nomeCorso) {
        return null;
    }

    @Override
    public boolean iscrivi(int matricola, String nomeCorso) {
        return false;
    }

    @Override
    public boolean cancellaIscrizione(int matricola, String nomeCorso) {
        return false;
    }

    //metodo che mi permette di avere qui in frequenza anche il db dei corsi
    private Corso traduciCorso(Frequenta f){
        chiamaDB();
        return corsoRepository.GetById(f.getNomeCorso());
    }

}
