package com.university.client.schermate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.university.client.model.Sostiene;
import com.university.client.model.Studente;
import com.university.client.services.StudenteService;
import com.university.client.services.StudenteServiceAsync;

import java.util.ArrayList;

public class SchermataStudente {
    Studente studente;
    String[] info;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private StudenteServiceAsync serviceStudente = GWT.create(StudenteService.class);


    public void accesso(Studente studente) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.getElement().setId("user__container");
        user__container.addStyleName("user__container");

        nav__user = new VerticalPanel();
        nav__user.getElement().setId("nav__user");
        nav__user.addStyleName("nav__user");


        this.studente = studente;

        Button btn__profilo = new Button("Profilo");
        Button btn__voti__studente = new Button("Il mio libretto");
        Button btn__esami = new Button("Pianifico le mie prove");
        Button btn__esami__studente = new Button("Esami prenotati");
        Button btn__corsi__studente = new Button("I miei corsi");
        Button btn__corsi = new Button("Tutti i corsi");

        btn__profilo.addStyleName("nav__user__btn");
        btn__voti__studente.addStyleName("nav__user__btn");
        btn__esami.addStyleName("nav__user__btn");
        btn__esami__studente.addStyleName("nav__user__btn");
        btn__corsi__studente.addStyleName("nav__user__btn");
        btn__corsi.addStyleName("nav__user__btn");

        btn__profilo.addClickHandler(clickEvent -> {
            try {
                form__profilo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nav__user.add(btn__profilo);
        nav__user.add(btn__voti__studente);
        nav__user.add(btn__esami);
        nav__user.add(btn__esami__studente);
        nav__user.add(btn__corsi__studente);
        nav__user.add(btn__corsi);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }


    public void form__profilo(){
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
                user__container.clear();
                HTML user__info = new HTML("<div class=\"content__studente\"><b>Nome: </b>" + result[1]
                        + "<br /><b>Cognome: </b>" + result[2]
                        + "<br /><b>E-mail: </b>" + result[0] + "</div>");
                user__container.add(new HTML("<div class=\"user__title\">Informazioni personali</div>"));
                user__container.add(user__info);
                user__container.add(user__info);
                user__container.add(user__info);
            }
        });
        RootPanel.get("container").add(user__container);
    }

    public void caricaEsamiSvolti(){
        //Qua metti il biding della sezione informazioni personali

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