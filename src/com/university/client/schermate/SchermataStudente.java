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

public class SchermataStudente {
    Studente studente;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private StudenteServiceAsync serviceStudente = GWT.create(StudenteService.class);
    private SostieneServiceAsync serviceSostiene = GWT.create(SostieneService.class);
    private EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private FrequentaServiceAsync serviceFrequenta = GWT.create(FrequentaService.class);
    private CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);


    public void accesso(Studente studente) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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

        btn__voti__studente.addClickHandler(clickEvent -> {
            try {
                form__libretto();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__esami.addClickHandler(clickEvent -> {
            try {
                form__pianificaProve();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__esami__studente.addClickHandler(clickEvent -> {
            try {
                form__esamiPrenotati();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__corsi__studente.addClickHandler(clickEvent -> {
            try {
                form__mieiCorsi();
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
        serviceStudente.getStudenteByMatricola(studente.getMatricola(), new AsyncCallback<Studente>() {
            @Override
            public void onFailure(Throwable caught) {

            }
            @Override
            public void onSuccess(Studente result) {
                user__container.clear();
                HTML user__info = new HTML("<div class=\"content__profilo\"><b>Nome: </b>" + result.getNome()
                        + "<br /><b>Cognome: </b>" + result.getCognome()
                        + "<br /><b>Data di nascita: </b>" + result.getDataNascita()
                        + "<br /><b>E-mail: </b>" + result.getMail()
                        + "<br /><b>Matricola: </b>" + result.getMatricola() + "</div>");
                user__container.add(new HTML("<div class=\"user__title\">Profilo</div>"));
                user__container.add(user__info);
            }
        });
        RootPanel.get("container").add(user__container);
    }

    public void form__libretto() throws Exception {
        user__container.clear();
        serviceSostiene.getEsamiLibretto(studente.getMatricola(), new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getSostieneStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Il mio libretto</div>"));

                CellTable<Sostiene> tabella__libretto = tabella__libretto(result, "Sembra che tu non abbia ancora valutazioni disponibili!");
                user__container.add(tabella__libretto);

            }
        });
    }

    public void form__pianificaProve() throws Exception {

        user__container.clear();
        serviceFrequenta.getCorsiStudente(studente.getMatricola(),new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getEsami: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {
                serviceSostiene.getEsamiSostenibili(studente.getMatricola(), result,  new AsyncCallback<Esame[]>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure on getEsami: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(Esame[] result) {

                        user__container.add(new HTML("<div class=\"user__title\">Pianifico le mie prove</div>"));

                        CellTable<Esame> tabella__pianificaProve = tabella__pianificaProve(result, "Sembra che non ci siano esami sostenibili!");
                        user__container.add(tabella__pianificaProve);

                    }
                });
            }
        });
    }

    public void form__esamiPrenotati() throws Exception {

        user__container.clear();
        serviceSostiene.getSostieneStudenteSenzaVoto(studente.getMatricola(), new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getSostieneStudenteSenzaVoto: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] result) {
                serviceEsame.getEsami( new AsyncCallback<Esame[]>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure on getEsami: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(Esame[] esami) {

                        List<Esame> esamiPrenotati = new ArrayList<>();

                        try {

                            for (Sostiene sostiene : result) {
                                for (Esame esame : esami) {
                                    if (sostiene.getCodEsame() == esame.getCodEsame()) {
                                        esamiPrenotati.add(esame);
                                    }
                                }
                            }
                        }catch (Exception e){

                        }

                        CellTable<Esame> tabella__esamiPrenotati = tabella__esamiPrenotati(esamiPrenotati, "Sembra che tu non abbia esami prenotati!");
                        user__container.add(new HTML("<div class=\"user__title\">Esami prenotati</div>"));
                        user__container.add(tabella__esamiPrenotati);
                    }
                });
            }
        });
    }

    public void form__mieiCorsi() throws Exception {

        user__container.clear();
        serviceFrequenta.getCorsiStudente(studente.getMatricola(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">I miei corsi</div>"));

                CellTable<Corso> tabella__mieiCorsi = tabella__mieiCorsi(result, "Sembra che tu non sia iscritto a nessun corso!");
                user__container.add(tabella__mieiCorsi);

            }
        });
    }

    public void form__corsi() throws Exception {

        user__container.clear();
        serviceFrequenta.getCorsiDisponibili(studente.getMatricola(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsi: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Tutti i corsi</div>"));

                CellTable<Corso> tabella__corsi = tabella__corsi(result, "Sembra che non ci siano corsi disponibili!");
                user__container.add(tabella__corsi);

            }
        });
    }

    private CellTable<Sostiene> tabella__libretto(Sostiene[] result, String msg) {

        CellTable<Sostiene> tabella__libretto = new CellTable<>();
        tabella__libretto.addStyleName("tabella__libretto");
        tabella__libretto.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__libretto.setEmptyTableWidget(new Label(msg));

        TextColumn<Sostiene> colonna__codice = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getCodEsame());
            }
        };
        tabella__libretto.addColumn(colonna__codice, "Codice esame");

        TextColumn<Sostiene> colonna__nomeCorso = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getNomeCorso();
            }
        };
        tabella__libretto.addColumn(colonna__nomeCorso, "Corso");

        TextColumn<Sostiene> colonna__data = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__libretto.addColumn(colonna__data, "Data");

        TextColumn<Sostiene> colonna__voto = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getVoto());
            }
        };
        tabella__libretto.addColumn(colonna__voto, "Voto");


        tabella__libretto.setRowCount(result.length, true);
        tabella__libretto.setRowData(0, Arrays.asList(result));
        return tabella__libretto;
    }

    private CellTable<Esame> tabella__pianificaProve(Esame[] esami, String msg) {

        CellTable<Esame> tabella__pianificaProve = new CellTable<>();
        tabella__pianificaProve.addStyleName("tabella__pianificaProve");
        tabella__pianificaProve.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__pianificaProve.setEmptyTableWidget(new Label(msg));

        TextColumn<Esame> colonna__corso = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getNomeCorso();
            }
        };
        tabella__pianificaProve.addColumn(colonna__corso, "Corso");

        TextColumn<Esame> colonna__data = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__pianificaProve.addColumn(colonna__data, "Data");


        TextColumn<Esame> colonna__durata = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getDurata();
            }
        };
        tabella__pianificaProve.addColumn(colonna__durata, "Durata");


        TextColumn<Esame> colonna__aula = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getAula();
            }
        };
        tabella__pianificaProve.addColumn(colonna__aula, "Aula");


        ButtonCell cella__sostieni = new ButtonCell();
        Column<Esame, String> colonna__sostieni = new Column<Esame, String>(cella__sostieni) {
            @Override
            public String getValue(Esame object) {
                return "Iscriviti";
            }
        };

        tabella__pianificaProve.addColumn(colonna__sostieni, "");
        colonna__sostieni.setCellStyleNames("iscrivitiEsame__btn");

        colonna__sostieni.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                serviceSostiene.creaSostiene(studente.getMatricola(), object.getCodEsame(), object.getNomeCorso(), object.getData(), object.getOra(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'iscrizione all'esame': " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Iscrizione all'esame avvenuta con successo!");
                        try {
                            form__pianificaProve();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        tabella__pianificaProve.setRowCount(esami.length, true);
        tabella__pianificaProve.setRowData(0, Arrays.asList(esami));
        return tabella__pianificaProve;
    }

    private CellTable<Esame> tabella__esamiPrenotati(List<Esame> result, String msg) {

        CellTable<Esame> tabella__esamiPrenotati = new CellTable<>();
        tabella__esamiPrenotati.addStyleName("tabella__esamiPrenotati");
        tabella__esamiPrenotati.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__esamiPrenotati.setEmptyTableWidget(new Label(msg));

        TextColumn<Esame> colonna__corso = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getNomeCorso();
            }
        };
        tabella__esamiPrenotati.addColumn(colonna__corso, "Corso");

        TextColumn<Esame> colonna__data = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__esamiPrenotati.addColumn(colonna__data, "Data");


        TextColumn<Esame> colonna__durata = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getDurata();
            }
        };
        tabella__esamiPrenotati.addColumn(colonna__durata, "Durata");


        TextColumn<Esame> colonna__aula = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getAula();
            }
        };
        tabella__esamiPrenotati.addColumn(colonna__aula, "Aula");


        tabella__esamiPrenotati.setRowCount(result.size(), true);
        tabella__esamiPrenotati.setRowData(0, result);
        return tabella__esamiPrenotati;
    }

    private CellTable<Corso> tabella__mieiCorsi(Corso[] corsi, String msg) {

        CellTable<Corso> tabella__mieiCorsi = new CellTable<>();
        tabella__mieiCorsi.addStyleName("tabella__mieiCorsi");
        tabella__mieiCorsi.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__mieiCorsi.setEmptyTableWidget(new Label(msg));

        TextColumn<Corso> colonna__nome = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNome();
            }
        };
        tabella__mieiCorsi.addColumn(colonna__nome, "Nome");

        TextColumn<Corso> colonna__periodo = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio() + " - " + object.getDataFine();
            }
        };
        tabella__mieiCorsi.addColumn(colonna__periodo, "Periodo");

        TextColumn<Corso> colonna__descrizione = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tabella__mieiCorsi.addColumn(colonna__descrizione, "Descrizione");

        TextColumn<Corso> colonna__docente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getDocente());
            }
        };
        tabella__mieiCorsi.addColumn(colonna__docente, "Docente");

        TextColumn<Corso> colonna__coDocente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getCoDocente());
            }
        };
        tabella__mieiCorsi.addColumn(colonna__coDocente, "Co-Docente");


        tabella__mieiCorsi.setRowCount(corsi.length, true);
        tabella__mieiCorsi.setRowData(0, Arrays.asList(corsi));
        return tabella__mieiCorsi;
    }

    private CellTable<Corso> tabella__corsi(Corso[] corsi, String msg) {

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

        TextColumn<Corso> colonna__docente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getDocente());
            }
        };
        tabella__corsi.addColumn(colonna__docente, "Docente");

        TextColumn<Corso> colonna__coDocente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getCoDocente());
            }
        };
        tabella__corsi.addColumn(colonna__coDocente, "Co-Docente");



        ButtonCell cella__frequenta = new ButtonCell();
        Column<Corso, String> colonna__frequenta = new Column<Corso, String>(cella__frequenta) {
            @Override
            public String getValue(Corso object) {
                return "Iscriviti";
            }
        };

        tabella__corsi.addColumn(colonna__frequenta, "");
        colonna__frequenta.setCellStyleNames("frequentaCorso__btn");

        colonna__frequenta.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(int index, Corso object, String value) {
                serviceFrequenta.iscrivi(studente.getMatricola(), object.getNome(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'iscrizione al corso: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Iscrizione al corso avvenuta con successo!");
                        try {
                            form__corsi();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        tabella__corsi.setRowCount(corsi.length, true);
        tabella__corsi.setRowData(0, Arrays.asList(corsi));

        return tabella__corsi;
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