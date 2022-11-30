package com.university.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.university.client.model.Corso;
import com.university.client.model.Frequenta;
import com.university.client.model.Sostiene;
import com.university.client.services.FrequentaService;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public class FrequentaImplementazione extends RemoteServiceServlet implements FrequentaService {
    RepositoryIntString<Frequenta> frequentaRepository;
    RepositoryDoubleInt<Sostiene>  sostieneRepository;
    RepositoryString<Corso> corsoRepository;
    Boolean singleton = false;
    Boolean test = false;

    //Costruttore
    public FrequentaImplementazione(){ }

    //Costruttore per il test
    public FrequentaImplementazione(Boolean test){
        this.test = test;
        frequentaRepository = new FrequentaRepositoryTest();
        corsoRepository = new CorsoRepositoryTest();
        sostieneRepository = new SostieneRepositoryTest();
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
                sostieneRepository= new SostieneRepository(context);
               singleton = true;
            }
        }
    }

    //Restituisce tutti gli oggetti frequenta
    @Override
    public Frequenta[] getFrequenta() {
        chiamaDB();
        return frequentaRepository.getAll();
    }

    //Restituisce tutti i corsi disponibili per uno studente, ovvero quelli a cui non è iscritto
    @Override
    public Corso[] getCorsiDisponibili(int matricola) {
        chiamaDB();
        ArrayList<Corso> corsiDisponibili= new ArrayList<>();
        boolean disponibile = true;
        for(Corso c : corsoRepository.getAll()){
            disponibile = true;
           for(Frequenta f : frequentaRepository.getArrayByString(c.getNome())){
               if(f.getMatricola()==matricola){
                   disponibile = false;
               }
           }
           if(disponibile){
               corsiDisponibili.add(c);
           }
        }
        return corsiDisponibili.toArray(new Corso[0]);
    }

    //Restitusice gli oggetti frequenta a cui è iscritto uno studente
    @Override
    public Frequenta[] getMieiCorsi(int matricola) {
        chiamaDB();
        ArrayList<Frequenta> mieiCorsi = new ArrayList<>();
        for(Frequenta f : frequentaRepository.getAll()){
            if(f.getMatricola()==matricola){
                mieiCorsi.add(f);
            }
        }
        return mieiCorsi.toArray(new Frequenta[0]);
    }

    //Restituisce tutti gli oggetti corso a cui c'è corrispondenza in Frequenta
    @Override
    public Corso[] getCorsiStudente(int matricola) {
        chiamaDB();
        ArrayList<Corso> corsiStudente = new ArrayList<>();
        for(Frequenta f : frequentaRepository.getAll()){
            if(f.getMatricola()==matricola){
                corsiStudente.add(traduciCorso(f));
            }
        }
        return corsiStudente.toArray(new Corso[0]);
    }

    //Restituisce tutti gli iscritti a un corso
    @Override
    public Frequenta[] getStudentiIscritti(String nomeCorso) {
        chiamaDB();
        ArrayList<Frequenta> studentiIscritti = new ArrayList<>();
        for(Frequenta f : frequentaRepository.getAll()){
            if(f.getNomeCorso().equals(nomeCorso)){
                studentiIscritti.add(f);
            }
        }
        return studentiIscritti.toArray(new Frequenta[0]);
    }

    //iscrivi uno studente a un corso, in pratica crea un oggetto Frequenta
    @Override
    public boolean iscrivi(int matricola, String nomeCorso) {
        chiamaDB();
        return frequentaRepository.Create(new Frequenta(matricola, nomeCorso));
    }

    //cancella l'iscrizione a un corso, in pratica elimina un oggetto Frequenta
    @Override
    public boolean cancellaIscrizione(int matricola, String nomeCorso) {
        chiamaDB();
        return frequentaRepository.Remove(matricola, nomeCorso);
    }

    //metodo che mi permette di avere qui in frequenza anche il db dei corsi
    private Corso traduciCorso(Frequenta f){
        chiamaDB();
        return corsoRepository.GetById(f.getNomeCorso());
    }

}
