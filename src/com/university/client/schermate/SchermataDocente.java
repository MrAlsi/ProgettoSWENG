package com.university.client.schermate;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.university.client.model.Docente;
import com.university.client.services.DocenteServiceAsync;
import com.university.client.services.DocenteService;
import com.university.client.model.Corso;
import com.university.client.services.CorsoServiceAsync;
import com.university.client.services.CorsoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchermataDocente {
    Docente docente;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private DocenteServiceAsync serviceDocente = GWT.create(DocenteService.class);
    private CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);


    public void accesso(Docente docente) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.getElement().setId("user__container");
        user__container.addStyleName("user__container");

        nav__user = new VerticalPanel();
        nav__user.getElement().setId("nav__user");
        nav__user.addStyleName("nav__user");

        this.docente = docente;

        Button btn__profilo = new Button("Profilo");
        Button btn__corsi = new Button("Gestione corsi");
        Button btn__esami = new Button("Gestione esami");

        btn__profilo.addStyleName("nav__user__btn");
        btn__corsi.addStyleName("nav__user__btn");
        btn__esami.addStyleName("nav__user__btn");

        btn__profilo.addClickHandler(clickEvent -> {
            try {
                form__profilo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__corsi.addClickHandler(clickEvent -> {
            try {
                form__corsi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nav__user.add(btn__profilo);
        nav__user.add(btn__corsi);
        nav__user.add(btn__esami);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }


    public void form__profilo(){
        serviceDocente.getDocente(docente.getCodDocente(), new AsyncCallback<Docente>() {
            @Override
            public void onFailure(Throwable caught) {

            }
            @Override
            public void onSuccess(Docente result) {
                user__container.clear();
                HTML user__info = new HTML("<div class=\"content__profilo\"><b>Nome: </b>" + result.getNome()
                        + "<br /><b>Cognome: </b>" + result.getCognome()
                        + "<br /><b>Codice Docente: </b>" + result.getCodDocente()
                        + "<br /><b>E-mail: </b>" + result.getMail()
                        + "</div>");
                user__container.add(new HTML("<div class=\"user__title\">Profilo</div>"));
                user__container.add(user__info);
            }
        });
        RootPanel.get("container").add(user__container);
    }



    public void form__corsi() throws Exception {
        user__container.clear();
        serviceCorso.getCorsiDocente(docente.getCodDocente(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiDocente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Gestione Corsi</div>"));

                CellTable<Corso> tabella__corsi = tabella__corsi(result, "Sembra che tu non appartenga a nessun corso, creane uno!");
                user__container.add(tabella__corsi);

                Button btnCreaCorso = new Button("Crea corso");
                btnCreaCorso.addStyleName("creaCorso__btn");
                user__container.add(btnCreaCorso);
            }
        });
    }

    private CellTable<Corso> tabella__corsi(Corso[] result, String msg) {

        CellTable<Corso> tabella__corsi = new CellTable<>();
        tabella__corsi.addStyleName("tabella__corsi");
        tabella__corsi.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__corsi.setEmptyTableWidget(new Label(msg));

        TextColumn<Corso> colonna__nome = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNome();
            }
        };
        tabella__corsi.addColumn(colonna__nome, "Nome");

        TextColumn<Corso> colonna__periodo = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio() + " - " + object.getDataFine();
            }
        };
        tabella__corsi.addColumn(colonna__periodo, "Periodo");

        TextColumn<Corso> colonna__descrizione = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tabella__corsi.addColumn(colonna__descrizione, "Descrizione");

        TextColumn<Corso> colonna__coDocente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getCoDocente());
            }
        };
        tabella__corsi.addColumn(colonna__coDocente, "Co-Docente");

        ButtonCell cella__modifica = new ButtonCell();
        Column<Corso, String> colonna__modifica = new Column<Corso, String>(cella__modifica) {
            @Override
            public String getValue(Corso object) {
                return "Modifica";
            }
        };

        tabella__corsi.addColumn(colonna__modifica, "");
        colonna__modifica.setCellStyleNames("tabella__btn");

        /*
        colonna__modifica.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(Corso object) {
                user__container.clear();
                user__container.add(new HTML("<div class=\"user__title\">Modifica corso</div>"));
                user__container.add((new form__modificaCorso(docente, object)).getForm());
            }
        }); */

        ButtonCell cella__elimina = new ButtonCell();
        Column<Corso, String> colonna__elimina = new Column<Corso, String>(cella__elimina) {
            @Override
            public String getValue(Corso object) {
                return "Cancella";
            }
        };

        tabella__corsi.addColumn(colonna__elimina, "");
        colonna__elimina.setCellStyleNames("eliminaCorso__btn");

        /*
        colonna__elimina.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(Corso object) {
                serviceCorso.eliminaCorso(object.getNome(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante la rimozione del corso: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Corso rimosso con successo!");
                        try {
                            form__corsi();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        */
        tabella__corsi.setRowCount(result.length, true);
        tabella__corsi.setRowData(0, Arrays.asList(result));
        return tabella__corsi;
    }
}