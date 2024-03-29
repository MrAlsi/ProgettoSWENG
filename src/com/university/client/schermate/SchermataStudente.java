/*
 * La classe SchermataStudente permette di creare e rendere visualizzabile la pagina del portale
 * dedicata all'utente Studente. Questa pagina è accessibile solamente eseguendo l'accesso attraverso il
 * form di login con le credenziali di un utente Studente.
 *
 * In questa pagina è possibile eseguire le seguenti opperazioni:
 *  -   Visualizzare la lista dei corsi disponibili.
 *  -   Iscriversi a un corso.
 *  -   Registrarsi per un esame di un corso.
 *  -   Visualizzare le informazioni personali (nome, cognome, email, corsi ed esami a cui sono iscritti).
 *  -   Visualizzare i voti degli esami svolti.

 * */

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

    // Metodo richiamato all'interno di University.java durante il login
    // Si occupa di creare il menu laterale nav__user e il container user__container
    // nel quale verranno visualizzate tutte le funzioni dell'utente
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

        //Menù laterale
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
                profilo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__voti__studente.addClickHandler(clickEvent -> {
            try {
                libretto();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__esami.addClickHandler(clickEvent -> {
            try {
                pianificaProve();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__esami__studente.addClickHandler(clickEvent -> {
            try {
                esamiPrenotati();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__corsi__studente.addClickHandler(clickEvent -> {
            try {
                mieiCorsi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__corsi.addClickHandler(clickEvent -> {
            try {
                corsi();
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


    // Metodo per la visualizzazione delle informazioni personali dello studente
    public void profilo(){
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

    // Metodo per la visualizzazione dei voti presi dallo studente agli esami sostenuti
    // all'interno della tabella "tabella__libretto"
    public void libretto() throws Exception {
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


    // Metodo per la visualizzazione degli esami a cui uno studente può iscriversi
    // all'interno della tabella "tabella__pianificaProve"
    public void pianificaProve() throws Exception {

        user__container.clear();

        // Metodo che ritorna una lista dei corsi ai quali lo studente è iscritto
        serviceFrequenta.getCorsiStudente(studente.getMatricola(),new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getEsami: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                // Metodo che, dati i corsi ai quali lo studente è iscritto, ritonra tutti gli esami sostenibili
                // ai quali non è ancora iscritto
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


    // Metodo per la visualizzazione degli esami a cui uno studente è iscritto ma che non hanno ancora un voto
    // all'interno della tabella "tabella__pianificaProve"
    public void esamiPrenotati() throws Exception {
        user__container.clear();

        // Metodo chge ritorna tutti gli oggetti sostiene relativi agli esami prenotati dallo studente
        // che non hanno ancora una valutazione
        serviceSostiene.getSostieneStudenteSenzaVoto(studente.getMatricola(), new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getSostieneStudenteSenzaVoto: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] result) {

                // Metodo che, dati i sostiene, ritorna i relativi oggetti Esame
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


    // Metodo per la visualizzazione dei corsi ai quali uno studente è iscritto
    // all'interno della tabella "tabella__mieiCorsi"
    public void mieiCorsi() throws Exception {

        user__container.clear();

        // Metodo che ritorna tutti gli oggetti Corso ai quali lo studente è iscritto
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

    // Metodo per la visualizzazione dei corsi ai quali uno studente non è ancora iscritto
    // all'interno della tabella "tabella__corsi"
    public void corsi() throws Exception {

        user__container.clear();

        // Metodo che ritorna tutti i corsi ai quali lo studente non è ancora iscritto
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

    // Oggetto tabella contenente i voti degli esami sostenuti dallo studente
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
                if(String.valueOf(object.getVoto()).equals("31") || String.valueOf(object.getVoto()).equals("32")) {
                    return "30 e lode";
                }
                return String.valueOf(object.getVoto());
            }
        };
        tabella__libretto.addColumn(colonna__voto, "Voto");


        tabella__libretto.setRowCount(result.length, true);
        tabella__libretto.setRowData(0, Arrays.asList(result));
        return tabella__libretto;
    }

    // Oggetto tabella contenente gli esami ai quali uno studente può iscriversi in base ai suoi corsi
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

                // Metodo per la creazione di un oggettto "Sostiene" all'interno del DB
                // Questo oggetto tiene traccia della relazione Studente/Esame
                serviceSostiene.creaSostiene(studente.getMatricola(), object.getCodEsame(), object.getNomeCorso(), object.getData(), object.getOra(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'iscrizione all'esame': " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Iscrizione all'esame avvenuta con successo!");
                        try {
                            pianificaProve();
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

    // Oggetto tabella contenente gli esami ai quali uno studente è iscritto e deve ancora sostenere
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

    // Oggetto tabella contenente i corsi ai quali lo studente è iscritto e che frequenta
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
                if(object.getCoDocente()==-1){
                    return "Nessun codocente";
                } else {
                    return String.valueOf(object.getCoDocente());
                }
            }
        };
        tabella__mieiCorsi.addColumn(colonna__coDocente, "Co-Docente");


        tabella__mieiCorsi.setRowCount(corsi.length, true);
        tabella__mieiCorsi.setRowData(0, Arrays.asList(corsi));
        return tabella__mieiCorsi;
    }

    // Oggetto tabella contenente i corsi ai quali uno studente può iscriversi
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
                if(object.getCoDocente()==-1){
                    return "Nessun codocente";
                } else {
                    return String.valueOf(object.getCoDocente());
                }
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

                // Metodo per la creazione di un oggettto "Frequenta" all'interno del DB
                // Questo oggetto tiene traccia della relazione Studente/Corso
                serviceFrequenta.iscrivi(studente.getMatricola(), object.getNome(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante l'iscrizione al corso: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Iscrizione al corso avvenuta con successo!");
                        try {
                            corsi();
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

    // Metodo per calcolare la media dei voti di uno studente
    public long calcolaMedia(ArrayList<Sostiene> esami){
        int totale = 0;
        for(Sostiene esame : esami ){
            totale+=esame.voto;
        }
        return totale/esami.size();
    }
}