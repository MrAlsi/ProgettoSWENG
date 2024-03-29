/*
 * La classe SchermataSegreteria permette di creare e rendere visualizzabile la pagina del portale
 * dedicata all'utente Segreteria. Questa pagina è accessibile solamente eseguendo l'accesso attraverso il
 * form di login con le credenziali di un utente Segreteria.
 *
 * In questa pagina è possibile eseguire le seguenti opperazioni:
 *  -   Visualizzare i dati personali degli studenti
 *  -   Pubblicare i voti degli esami
 * */

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
import com.university.client.model.*;
import com.university.client.services.*;

import java.util.Arrays;

public class SchermataSegreteria {
    Segreteria segreteria;
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
                studenti();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__valutazioni.addClickHandler(clickEvent -> {
            try {
                valutazioni();
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

    // Metodo per la visualizzazione degli studenti all'interno della tabella "tabella__studenti"
    public void studenti() throws Exception {
        user__container.clear();

        // Metodo che restituisce la lista di tutti gli studenti iscritti alla piattaforma
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

    // Metodo per la visualizzazione degli esami sostenuti dagli studenti con le valutazioni
    // inviate dai docenti che aspettano di essere pubblicate
    public void valutazioni() throws Exception {
        user__container.clear();

        // Metodo che ritorna, degli oggetti Sostiene, quelli che hanno il campo "accettato" == false
        // ovvero il voto non è ancora stato pubblicato
        serviceSostiene.esamiSostenuti(new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getStudenti: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] esamiSostenuti) {

                user__container.add(new HTML("<div class=\"user__title\">Valutazioni</div>"));

                CellTable<Sostiene> tabella__valutazioni = tabella__valutazioni(esamiSostenuti, "Sembra che non ci siano valutazioni da pubblicare!");
                user__container.add(tabella__valutazioni);
            }
        });
    }

    // Metodo per la  visualizzazione dei corsi di uno studente
    // all'interno della tabella "tabella__corsiStudente"
    public void visualizzaCorsi(int matricola, String nome, String cognome) throws Exception {
        user__container.clear();
        serviceFrequenta.getCorsiStudente(matricola, new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Corsi dello studente " + cognome + " " + nome + "</div>"));

                CellTable<Corso> tabella__corsiStudente = tabella__corsiStudente(result, "Sembra che lo studente non sia iscritto a nessun corso!");
                user__container.add(tabella__corsiStudente);

                Button btn__chiudi = new Button("< Back");
                btn__chiudi.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        try {
                            studenti();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                btn__chiudi.addStyleName("back__btn");
                user__container.add(btn__chiudi);
            }
        });
    }

    // Metodo per la visualizzazione degli esami di uno studente
    // all'interno della tabella "tabella__esamiStudente"
    public void visualizzaEsami(int matricola, String nome, String cognome) throws Exception {
        user__container.clear();

        serviceSostiene.getEsamiLibretto(matricola, new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getSostieneStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Voti dello studente " + cognome + " " + nome + "</div>"));

                CellTable<Sostiene> tabella__esamiStudente = tabella__esamiStudente(result, "Sembra che lo studente non abbia ancora valutazioni disponibili!");
                user__container.add(tabella__esamiStudente);

                Button btn__chiudi = new Button("< Back");
                btn__chiudi.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        try {
                            studenti();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                btn__chiudi.addStyleName("back__btn");
                user__container.add(btn__chiudi);
            }
        });
    }

    // Oggetto tabella con le informazioni degli studenti
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


        // Pulsante disponibile su ogni riga che permette di caricara nel container
        // una nuova schermata contenente i corsi dello studente scelto attraverso il metodo
        // "visualizzaCorsi()"
        ButtonCell cella__visualizzaCorsi = new ButtonCell();
        Column<Studente, String> colonna__visualizzaCorsi = new Column<Studente, String>(cella__visualizzaCorsi) {
            @Override
            public String getValue(Studente object) {
                return "Corsi";
            }
        };


        tabella__studenti.addColumn(colonna__visualizzaCorsi, "");
        colonna__visualizzaCorsi.setCellStyleNames("pubblicaVoto__btn");

        colonna__visualizzaCorsi.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {

                try {
                    visualizzaCorsi(object.getMatricola(), object.getNome(), object.getCognome());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });


        // Pulsante disponibile su ogni riga che permette di caricara nel container
        // una nuova schermata contenente gli esami sostenuti dallo studente scelto attraverso il metodo
        // "visualizzaEsami()"
        ButtonCell cella__visualizzaEsami = new ButtonCell();
        Column<Studente, String> colonna__visualizzaEsami = new Column<Studente, String>(cella__visualizzaEsami) {
            @Override
            public String getValue(Studente object) {
                return "Esami";
            }
        };


        tabella__studenti.addColumn(colonna__visualizzaEsami, "");
        colonna__visualizzaEsami.setCellStyleNames("pubblicaVoto__btn");

        colonna__visualizzaEsami.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {

                try {
                    visualizzaEsami(object.getMatricola(), object.getNome(), object.getCognome());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });


        tabella__studenti.setRowCount(result.length, true);
        tabella__studenti.setRowData(0, Arrays.asList(result));
        return tabella__studenti;
    }

    // Oggetto tabella contenente i corsi ai quali uno studente è iscritto
    private CellTable<Corso> tabella__corsiStudente(Corso[] corsi, String msg) {

        CellTable<Corso> tabella__corsiStudente = new CellTable<>();
        tabella__corsiStudente.addStyleName("tabella__mieiCorsi");
        tabella__corsiStudente.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__corsiStudente.setEmptyTableWidget(new Label(msg));

        TextColumn<Corso> colonna__nome = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNome();
            }
        };
        tabella__corsiStudente.addColumn(colonna__nome, "Nome");

        TextColumn<Corso> colonna__periodo = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio() + " - " + object.getDataFine();
            }
        };
        tabella__corsiStudente.addColumn(colonna__periodo, "Periodo");

        TextColumn<Corso> colonna__descrizione = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tabella__corsiStudente.addColumn(colonna__descrizione, "Descrizione");

        TextColumn<Corso> colonna__docente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return String.valueOf(object.getDocente());
            }
        };
        tabella__corsiStudente.addColumn(colonna__docente, "Docente");

        TextColumn<Corso> colonna__coDocente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                if(String.valueOf(object.getCoDocente()).equals("-1")){
                    return "nessun codocente";
                }
                return String.valueOf(object.getCoDocente());
            }
        };
        tabella__corsiStudente.addColumn(colonna__coDocente, "Co-Docente");


        tabella__corsiStudente.setRowCount(corsi.length, true);
        tabella__corsiStudente.setRowData(0, Arrays.asList(corsi));
        return tabella__corsiStudente;
    }

    // Oggetto tabella contenente gli esami sostenuti da uno studente
    private CellTable<Sostiene> tabella__esamiStudente(Sostiene[] result, String msg) {

        CellTable<Sostiene> tabella__esamiStudente = new CellTable<>();
        tabella__esamiStudente.addStyleName("tabella__libretto");
        tabella__esamiStudente.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__esamiStudente.setEmptyTableWidget(new Label(msg));

        TextColumn<Sostiene> colonna__codice = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getCodEsame());
            }
        };
        tabella__esamiStudente.addColumn(colonna__codice, "Codice esame");

        TextColumn<Sostiene> colonna__nomeCorso = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getNomeCorso();
            }
        };
        tabella__esamiStudente.addColumn(colonna__nomeCorso, "Corso");

        TextColumn<Sostiene> colonna__data = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__esamiStudente.addColumn(colonna__data, "Data");

        TextColumn<Sostiene> colonna__voto = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return String.valueOf(object.getVoto());
            }
        };
        tabella__esamiStudente.addColumn(colonna__voto, "Voto");


        tabella__esamiStudente.setRowCount(result.length, true);
        tabella__esamiStudente.setRowData(0, Arrays.asList(result));
        return tabella__esamiStudente;
    }

    // Oggetto tabella contenente gli esami di uno studente in cui il voto non è ancora stato pubblicato
    private CellTable<Sostiene> tabella__valutazioni(Sostiene[] result, String msg) {

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

                // Metodo che imposta il campo "accettato" del Sostiene su true rendendolo visualizzabile nel
                // libretto dello studente
                serviceSostiene.accettaVoto(object.getCodEsame(), object.getMatricola(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Errore durante la pubblicazione del voto: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Boolean check) {
                        Window.alert("Voto pubblicato con successo!");
                        try {
                            valutazioni();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        tabella__valutazioni.setRowCount(result.length, true);
        tabella__valutazioni.setRowData(0, Arrays.asList(result));
        return tabella__valutazioni;
    }

}