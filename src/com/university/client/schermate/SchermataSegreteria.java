package com.university.client.schermate;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.university.client.model.*;
import com.university.client.services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchermataSegreteria {
    Segreteria segreteria;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private StudenteServiceAsync serviceStudente = GWT.create(StudenteService.class);
    private SostieneServiceAsync serviceSostiene = GWT.create(SostieneService.class);
    private EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private FrequentaServiceAsync serviceFrequenta = GWT.create(FrequentaService.class);
    private CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);


    public void accesso(Segreteria segreteria) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        user__container.getElement().setId("user__container");
        user__container.addStyleName("user__container");

        nav__user = new VerticalPanel();
        nav__user.getElement().setId("nav__user");
        nav__user.addStyleName("nav__user");


        this.segreteria = segreteria;

        Button btn__studenti = new Button("Studenti");
        Button btn__valutazioni = new Button("Gescisci valutazioni");

        btn__studenti.addStyleName("nav__user__btn");
        btn__valutazioni.addStyleName("nav__user__btn");

        btn__studenti.addClickHandler(clickEvent -> {
            try {
                form__studenti();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__valutazioni.addClickHandler(clickEvent -> {
            try {
                form__valutazioni();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nav__user.add(btn__studenti);
        nav__user.add(btn__valutazioni);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }

    public void form__studenti() throws Exception {
        user__container.clear();
        serviceStudente.getStudenti(new AsyncCallback<Studente[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getStudenti: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Studente[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Studenti</div>"));

                CellTable<Studente> tabella__studenti = tabella__studenti(result, "Sembra che non ci siano studenti iscritti!");
                user__container.add(tabella__studenti);

            }
        });
    }

    public void form__valutazioni() throws Exception {
        user__container.clear();

        List<Sostiene> tuttiSostieneConVoto = new ArrayList<>();
        serviceStudente.getStudenti(new AsyncCallback<Studente[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getStudenti: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Studente[] studenti) {

                for(Studente studenteIndex : studenti) {
                    serviceSostiene.getSostieneStudenteConVoto(studenteIndex.getMatricola(), new AsyncCallback<Sostiene[]>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Window.alert("Failure on getSostieneStudenteConVoto: " + throwable.getMessage());
                        }
                        @Override
                        public void onSuccess(Sostiene[] esamiSostenuti) {

                            for(Sostiene esameSostenuto : esamiSostenuti){
                                tuttiSostieneConVoto.add(esameSostenuto);
                            }
                        }
                    });
                }
                user__container.add(new HTML("<div class=\"user__title\">Valutazioni</div>"));

                CellTable<Sostiene> tabella__valutazioni = tabella__valutazioni(tuttiSostieneConVoto, "Sembra che non ci siano valutazioni da pubblicare!");
                user__container.add(tabella__valutazioni);

            }
        });
    }

    private CellTable<Studente> tabella__studenti(Studente[] result, String msg) {

        CellTable<Studente> tabella__studenti = new CellTable<>();
        tabella__studenti.addStyleName("tabella__studenti");
        tabella__studenti.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__studenti.setEmptyTableWidget(new Label(msg));

        TextColumn<Studente> colonna__matricola = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return String.valueOf(object.getMatricola());
            }
        };
        tabella__studenti.addColumn(colonna__matricola, "Matricola");

        TextColumn<Studente> colonna__nome = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getNome();
            }
        };
        tabella__studenti.addColumn(colonna__nome, "Nome");

        TextColumn<Studente> colonna__cognome = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getCognome();
            }
        };
        tabella__studenti.addColumn(colonna__cognome, "Cognome");

        TextColumn<Studente> colonna__mail = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getMail();
            }
        };
        tabella__studenti.addColumn(colonna__mail, "Mail");

        TextColumn<Studente> colonna__nascita = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getDataNascita();
            }
        };
        tabella__studenti.addColumn(colonna__nascita, "Data di nascita");



        tabella__studenti.setRowCount(result.length, true);
        tabella__studenti.setRowData(0, Arrays.asList(result));
        return tabella__studenti;
    }

    private CellTable<Sostiene> tabella__valutazioni(List<Sostiene> result, String msg) {

        CellTable<Sostiene> tabella__valutazioni = new CellTable<>();
        tabella__valutazioni.addStyleName("tabella__valutazioni");
        tabella__valutazioni.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__valutazioni.setEmptyTableWidget(new Label(msg));

        TextColumn<Sostiene> colonna__matricola = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getMatricola());
            }
        };
        tabella__valutazioni.addColumn(colonna__matricola, "Matricola");

        TextColumn<Sostiene> colonna__codEsame = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getCodEsame());
            }
        };
        tabella__valutazioni.addColumn(colonna__codEsame, "Codice Esame");

        TextColumn<Sostiene> colonna__nomeCorso = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getNomeCorso();
            }
        };
        tabella__valutazioni.addColumn(colonna__nomeCorso, "Corso");

        TextColumn<Sostiene> colonna__data = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__valutazioni.addColumn(colonna__data, "Data");


        TextColumn<Sostiene> colonna__voto = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {

                return String.valueOf(object.getVoto());
            }
        };
        tabella__valutazioni.addColumn(colonna__voto, "Voto");


        ButtonCell cella__pubblica = new ButtonCell();
        Column<Sostiene, String> colonna__pubblica = new Column<Sostiene, String>(cella__pubblica) {
            @Override
            public String getValue(Sostiene object) {
                return "Pubblica";
            }
        };

        tabella__valutazioni.addColumn(colonna__pubblica, "");
        colonna__pubblica.setCellStyleNames("pubblicaVoto__btn");

        colonna__pubblica.setFieldUpdater(new FieldUpdater<Sostiene, String>() {
            @Override
            public void update(int index, Sostiene object, String value) {
                serviceSostiene.accettaVoto(object.getCodEsame(), object.getMatricola(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante la pubblicazione del voto: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Voto pubblicato con successo!");
                        try {
                            form__valutazioni();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        tabella__valutazioni.setRowCount(result.size(), true);
        tabella__valutazioni.setRowData(0, result);
        return tabella__valutazioni;
    }

}