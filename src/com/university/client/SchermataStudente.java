package com.university.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import com.university.client.services.StudenteService;
import com.university.client.services.StudenteServiceAsync;

import java.util.ArrayList;

public class SchermataStudente {
    Studente studente;
    String[] info;
    private StudenteServiceAsync serviceStudente = GWT.create(StudenteService.class);

    public void accesso(Studente studente) {
        RootPanel.get("container").clear();
        this.studente = studente;
        caricaInformazioniPersonali();
        caricaEsamiSvolti();
    }


    public void caricaInformazioniPersonali(){
        //Qua metti il biding della sezione informazioni personali
        final Label[] lab = new Label[1];
        serviceStudente.getInformazioniPersonali(studente, new AsyncCallback<String[]>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String[] result) {
                //In result dovresti avere tutti i dati personali dell'utente in un array
                lab[0] = new Label(result[0]);
            }
        });

        RootPanel.get("container").add(lab[0]);
    }

    public void caricaEsamiSvolti(){
        //Qua metti il biding della sezione informazioni personali

        serviceStudente.getVoti(studente.matricola, new AsyncCallback<ArrayList<Sostiene>>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ArrayList<Sostiene> result) {
                //In result dovresti un arraylist con tutti gli esami svolti
                //Metodo calcolaMedia
            }
        });
    }

    /**
     * Dati un arraylist di esami svolti calcola la media complessiva
     */
    public long calcolaMedia(ArrayList<Sostiene> esami){
        int totale = 0;
        for(Sostiene esame : esami ){
            totale+=esame.voto;
        }
        return totale/esami.size();
    }
}
