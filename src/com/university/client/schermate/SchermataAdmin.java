/*
 * La classe SchermataAdmin permette di creare e rendere visualizzabile la pagina del portale
 * dedicata all'utente Admin. Questa pagina è accessibile solamente eseguendo l'accesso attraverso il
 * form di login con le credenziali di un utente Admin.
 *
 * ============ CREDENZIALI DI ACCESSO ADMIN ============
 * Username: admin
 * Password: admin
 *
 * In questa pagina è possibile eseguire le seguenti opperazioni:
 *  -   Creare account per studenti/docenti/segreteria utilizzando il loro indirizzo email
 *  -   Aggiungere/Modificare/Visualizzare le informazioni personali degli studenti e dei docenti.
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

public class SchermataAdmin {

    VerticalPanel user__container;
    VerticalPanel nav__user;
    private static StudenteServiceAsync studenteServiceAsync = GWT.create(StudenteService.class);
    private static DocenteServiceAsync docenteServiceAsync = GWT.create(DocenteService.class);
    private static SegreteriaServiceAsync segreteriaServiceAsync = GWT.create(SegreteriaService.class);
    private static FrequentaServiceAsync frequentaServiceAsync= GWT.create(FrequentaService.class);
    private static SostieneServiceAsync sostieneServiceAsync= GWT.create(SostieneService.class);


    // Metodo richiamato all'interno di University.java durante il login
    // Si occupa di creare il menu laterale nav__user e il container user__container
    // nel quale verranno visualizzate tutte le funzioni dell'utente
    public void accesso(){
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        user__container.getElement().setId("user__container");
        user__container.addStyleName("user__container");

        nav__user = new VerticalPanel();
        nav__user.getElement().setId("nav__user");
        nav__user.addStyleName("nav__user");

        Button btn__studenti = new Button("Lista studenti");
        Button btn__docenti = new Button("Lista docenti");
        Button btn__segreteria = new Button("Lista segreteria");

        btn__studenti.addStyleName("nav__user__btn");
        btn__docenti.addStyleName("nav__user__btn");
        btn__segreteria.addStyleName("nav__user__btn");

        btn__studenti.addClickHandler(clickEvent -> {
            try {
                listaStudenti();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__docenti.addClickHandler(clickEvent -> {
            try {
                listaDocenti();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__segreteria.addClickHandler(clickEvent -> {
            try {
                listaSegreteria();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nav__user.add(btn__studenti);
        nav__user.add(btn__docenti);
        nav__user.add(btn__segreteria);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }

    /*       ~ ~ Metodi per Studente ~ ~        */

    // Metodo per la visualizzazione degli studenti all'interno della tabella "tabella__studenti"
    public void listaStudenti(){
        user__container.clear();

        // Metodo che restituisce la lista di tutti gli studenti iscritti alla piattaforma
        studenteServiceAsync.getStudenti(new AsyncCallback<Studente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel visualizzare gli studenti "+caught);
            }

            @Override
            public void onSuccess(Studente[] result) {
                // Bottone che attraverso il metodo "formStudenti" fa visualizzare il form
                // per la creazione di un nuovo studente all'interno del container
                Button btn__creaStudente = new Button("Crea studente");
                btn__creaStudente.addStyleName("creaCorso__btn");
                btn__creaStudente.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formStudenti();
                    }
                });

                // Tabella contenente tutti gli stundenti iscritti alla piattaforma
                CellTable <Studente> tabella__studenti= tabella__studenti(result,"Sembra non ci siano studenti");
                user__container.add(new HTML("<div class=\"user__title\">Studenti iscritti alla piattaforma</div>"));
                user__container.add(btn__creaStudente);
                user__container.add(tabella__studenti);
            }
        });
    }

    // Oggetto tabella con le informazioni degli studenti
    private CellTable<Studente> tabella__studenti(Studente[] result, String msg) {

        CellTable<Studente> tabellaStudente= new CellTable<>();
        tabellaStudente.addStyleName("tabella__studenti");
        tabellaStudente.setEmptyTableWidget(new Label(msg));

        TextColumn<Studente> colonna__nome = new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getNome();
            }
        };
        tabellaStudente.addColumn(colonna__nome, "Nome");
        TextColumn  <Studente> colonna__cognome= new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getCognome();
            }
        };
        tabellaStudente.addColumn(colonna__cognome, "Cognome");
        TextColumn <Studente> colonna__matricola= new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return String.valueOf(object.getMatricola());
            };
        };
        tabellaStudente.addColumn(colonna__matricola,"Matricola");
        TextColumn<Studente> colonna__mail=new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getMail();
            }
        };
        tabellaStudente.addColumn(colonna__mail,"Email");

        // Pulsante disponibile su ogni riga che permette di caricara nel container
        // una nuova schermata contenente i corsi dello studente scelto attraverso il metodo
        // "form__visualizzaCorsi()"
        ButtonCell cella__visualizzaCorsi= new ButtonCell();
        Column<Studente, String> colonna__visualizzaCorsi=new Column<Studente, String>(cella__visualizzaCorsi) {
            @Override
            public String getValue(Studente object) {
                return "Corsi";
            }
        };
        colonna__visualizzaCorsi.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {
                try {
                    form__visualizzaCorsi(object.getMatricola(), object.getCognome(), object.getNome());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tabellaStudente.addColumn(colonna__visualizzaCorsi,"");
        colonna__visualizzaCorsi.setCellStyleNames("visualizza__btn");


        // Pulsante disponibile su ogni riga che permette di caricara nel container
        // una nuova schermata contenente gli esami sostenuti dallo studente scelto attraverso il metodo
        // "form__visualizzaEsami()"
        ButtonCell cella_visualizzaEsami= new ButtonCell();

        Column<Studente, String> colonna__visualizzaEsami = new Column<Studente, String>(cella_visualizzaEsami) {
            @Override
            public String getValue(Studente object) {
                return "Esami";
            }
        };
        colonna__visualizzaEsami.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {
                try {
                    form__visualizzaEsami(object.getMatricola(), object.getCognome(), object.getCognome());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tabellaStudente.addColumn(colonna__visualizzaEsami,"");
        colonna__visualizzaEsami.setCellStyleNames("visualizza__btn");


        // Pulsante disponibile su ogni riga che permette di caricara nel container il form
        // per l'aggiornamento dei dati di uno studente
        // attraverso il metodo "formModificaStudente()"
        ButtonCell cella__modifica= new ButtonCell();
        Column<Studente, String> colonna__modifica=new Column<Studente, String>(cella__modifica) {
            @Override
            public String getValue(Studente object) {
                return "Modifica";
            }
        };

        colonna__modifica.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {
                formModificaStudente(object.getNome(), object.getCognome(), object.getPassword(), object.getMail(),
                        object.getDataNascita(), object.getMatricola());
            }
        });
        tabellaStudente.addColumn(colonna__modifica,"");
        colonna__modifica.setCellStyleNames("modifica__btn");

        tabellaStudente.setRowCount(result.length);
        tabellaStudente.setRowData(0, Arrays.asList(result));
        return tabellaStudente;
    }

    // Form per la creazione di un nuovo studente
    public void formStudenti(){
        user__container.clear();
        Button btn__chiudi = new Button("< Back");
        btn__chiudi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listaStudenti();
            }
        });
        btn__chiudi.addStyleName("back__btn");

        FormPanel creaStudente = new FormPanel();
        creaStudente.setAction("/creanuvoStudente");
        creaStudente.setMethod(FormPanel.METHOD_POST);
        creaStudente.addStyleName("form__crea-modifica");

        VerticalPanel studenteContainer= new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        studenteContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        studenteContainer.add(nome__textBox);
        final Label cognome__label = new Label("Cognome: ");
        studenteContainer.add(cognome__label);
        final TextBox cognome__textBox = new TextBox();
        studenteContainer.add(cognome__textBox);
        final Label password__label = new Label("Password: ");
        studenteContainer.add(password__label);
        final TextBox password__textBox = new TextBox();
        studenteContainer.add(password__textBox);

        final Label data__label = new Label("Data di nascita: ");

        data__label.setStyleName("label__margin");
        studenteContainer.add(data__label);
        ListBox[] data = getInsertData();
        Label giorno__label = new Label("Giorno");
        studenteContainer.add(giorno__label);
        studenteContainer.add(data[0]);
        Label mese__label = new Label("Mese");
        studenteContainer.add(mese__label);
        studenteContainer.add(data[1]);
        Label anno__label = new Label("Anno");
        studenteContainer.add(anno__label);
        studenteContainer.add(data[2]);

        final Button crea__btn = new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaStudente.submit();
            }
        });

        creaStudente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                if(nome__textBox.getText().length()==0 || cognome__textBox.getText().length()==0 ||
                        password__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
                if(controlloMese(data[0].getSelectedValue(), data[1].getSelectedValue(), data[2].getSelectedValue())==null) {
                    Window.alert("Trenta giorni ha novembre\n" +
                            "con april, giugno e settembre\n" +
                            "di ventotto ce n'è uno\n" +
                            "tutti gli altri ne han trentuno");
                    event.cancel();
                }
            }
        });

        creaStudente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                // Metodo che crea un nuovo studente e lo salva all'interno del DB
                studenteServiceAsync.creaStudente(nome__textBox.getText(), cognome__textBox.getText(), password__textBox.getText(), controlloMese(data[0].getSelectedValue(), data[1].getSelectedValue(), data[2].getSelectedValue()), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare lo studente: " + caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Studente creato");
                        listaStudenti();
                    }
                });
            }
        });

        user__container.add(new HTML("<div class=\"user__title\">Crea un nuovo profilo Studente</div>"));
        user__container.add(btn__chiudi);
        studenteContainer.add(crea__btn);
        creaStudente.add(studenteContainer);

        user__container.add(creaStudente);
    }

    // Form per la modifica di uno studente selezionato
    public void formModificaStudente(String nome, String cognome, String password, String mail, String dataNascita, int matricola){
        user__container.clear();
        Button btn__chiudi = new Button("< Back");
        btn__chiudi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listaStudenti();
            }
        });
        btn__chiudi.addStyleName("back__btn");
        FormPanel modificaStudente =new FormPanel();
        modificaStudente.setAction("/creanuvoStudente");
        modificaStudente.setMethod(FormPanel.METHOD_POST);
        modificaStudente.addStyleName("form__crea-modifica");
        VerticalPanel studenteContainer= new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        studenteContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        nome__textBox.setValue(nome);
        studenteContainer.add(nome__textBox);
        final Label cognome__label = new Label("Cognome: ");
        studenteContainer.add(cognome__label);
        final TextBox cognome__textBox = new TextBox();
        cognome__textBox.setValue(cognome);
        studenteContainer.add(cognome__textBox);
        final Label password__label = new Label("Password: ");
        studenteContainer.add(password__label);
        final TextBox password__textBox = new TextBox();
        password__textBox.setValue(password);
        studenteContainer.add(password__textBox);

        final Label data__label = new Label("Data di nascita: ");
        data__label.addStyleName("label__margin");

        studenteContainer.add(data__label);
        ListBox[] data = getInsertData();
        data[0].setItemSelected(Integer.parseInt(dataNascita.split("/")[0])-1, true);
        studenteContainer.add(data[0]);
        data[1].setItemSelected(Integer.parseInt(dataNascita.split("/")[1])-1, true);
        studenteContainer.add(data[1]);
        data[2].setItemSelected((Integer.parseInt(dataNascita.split("/")[2])-2003)*-1, true);
        studenteContainer.add(data[2]);

        final Button crea__btn = new Button("Modifica");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                modificaStudente.submit();
            }
        });

        modificaStudente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                if(nome__textBox.getText().length()==0 || cognome__textBox.getText().length()==0 ||
                        password__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
                if(controlloMese(data[0].getSelectedValue(), data[1].getSelectedValue(), data[2].getSelectedValue())==null) {
                    Window.alert("Trenta giorni ha novembre\n" +
                            "con april, giugno e settembre\n" +
                            "di ventotto ce n'è uno\n" +
                            "tutti gli altri ne han trentuno");
                    event.cancel();
                }
            }
        });

        modificaStudente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                // Metodo che permette di sovrascrivere dati relativi ad uno studente nel DB
                studenteServiceAsync.modificaStudente(nome__textBox.getText(), cognome__textBox.getText(), mail,
                        password__textBox.getText(), controlloMese(data[0].getSelectedValue(), data[1].getSelectedValue(), data[2].getSelectedValue()),
                        matricola, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Errore nel creare lo studente: "+caught);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                Window.alert("Studente modificato");
                                listaStudenti();
                            }
                        });
            }
        });
        user__container.add(new HTML("<div class=\"user__title\">Modifica i dati dello studente</div>"));
        user__container.add(btn__chiudi);
        studenteContainer.add(crea__btn);
        modificaStudente.add(studenteContainer);
        user__container.add(modificaStudente);
    }

    // Metodo per la visualizzazione dei corsi di uno studente
    // all'interno della tabella "tabella__corsiStudente"
    public void form__visualizzaCorsi(int matricola, String nome, String cognome) throws Exception {
        user__container.clear();

        // Metodo che restituisce una lista di corsi ai quali lo studente è iscritto
        frequentaServiceAsync.getCorsiStudente(matricola, new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Corsi dello studente " + cognome + " " + nome + "</div>"));

                Button btn__chiudi = new Button("< Back");
                btn__chiudi.addStyleName("back__btn");
                btn__chiudi.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        listaStudenti();
                    }
                });
                user__container.add(btn__chiudi);

                CellTable<Corso> tabella__corsiStudente = tabella__corsiStudente(result, "Sembra che lo studente non sia iscritto a nessun corso!");
                user__container.add(tabella__corsiStudente);

            }
        });
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

    // Metodo per la visualizzazione degli esami di uno studente
    // all'interno della tabella "tabella__esamiStudente"
    public void form__visualizzaEsami(int matricola, String nome, String cognome) throws Exception {
        user__container.clear();
        sostieneServiceAsync.getEsamiLibretto(matricola, new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getSostieneStudente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Sostiene[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Voti dello studente " + cognome + " " + nome + "</div>"));

                Button btn__chiudi = new Button("< Back");
                btn__chiudi.addStyleName("back__btn");
                btn__chiudi.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        listaStudenti();
                    }
                });
                user__container.add(btn__chiudi);
                CellTable<Sostiene> tabella__esamiStudente = tabella__esamiStudente(result, "Sembra che lo studente non abbia ancora valutazioni disponibili!");
                user__container.add(tabella__esamiStudente);

            }
        });
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

    /*       ~ ~ Metodi per Docente ~ ~        */

    // Metodo per la visualizzazione dei docenti all'interno della tabella "tabella__docenti"
    public void listaDocenti(){
        user__container.clear();
        docenteServiceAsync.getDocenti(new AsyncCallback<Docente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare i docenti "+caught);
            }
            @Override
            public void onSuccess(Docente[] result) {

                // Bottone che attraverso il metodo "formDocenti" fa visualizzare il form
                // per la creazione di un nuovo docente all'interno del container
                Button btn__creaDocente = new Button("Crea docente");
                btn__creaDocente.addStyleName("creaCorso__btn");
                btn__creaDocente.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formDocenti();
                    }
                });

                // Tabella contenente tutti i docenti iscritti alla piattaforma
                CellTable <Docente> tabella__docenti = tabella__docenti(result,"Sembra non ci siano docenti");
                user__container.add(new HTML("<div class=\"user__title\">Docenti iscritti alla piattaforma</div>"));
                user__container.add(btn__creaDocente);
                user__container.add(tabella__docenti);

            }
        });
    }

    // Oggetto tabella con le informazioni dei docenti
    private CellTable<Docente> tabella__docenti(Docente[] result, String msg) {

        CellTable<Docente> tabellaDocente = new CellTable<>();
        tabellaDocente.addStyleName("tabella__docenti");

        tabellaDocente.setEmptyTableWidget(new Label(msg));

        TextColumn<Docente> colonna__nome = new TextColumn<Docente>() {
            @Override
            public String getValue(Docente object) {
                return object.getNome();
            }
        };
        tabellaDocente.addColumn(colonna__nome, "Nome");

        TextColumn<Docente> colonna__cognome = new TextColumn<Docente>() {
            @Override
            public String getValue(Docente object) {
                return object.getCognome();
            }
        };
        tabellaDocente.addColumn(colonna__cognome, "Cognome");
        TextColumn<Docente> colonna__codice = new TextColumn<Docente>() {
            @Override
            public String getValue(Docente object) {
                return String.valueOf(object.getCodDocente());
            }
        };
        tabellaDocente.addColumn(colonna__codice, "Codice docente");
        TextColumn<Docente> colonna__mail = new TextColumn<Docente>() {
            @Override
            public String getValue(Docente object) {
                return object.getMail();
            }
        };
        tabellaDocente.addColumn(colonna__mail, "Email");


        // Pulsante disponibile su ogni riga che permette di caricara nel container il form
        // per l'aggiornamento dei dati di un docente
        // attraverso il metodo "formModificaDocente()"
        ButtonCell cella__modifica= new ButtonCell();
        Column<Docente, String> colonna__modifica=new Column<Docente, String>(cella__modifica) {
            @Override
            public String getValue(Docente object) {
                return "Modifica";
            }
        };

        colonna__modifica.setFieldUpdater(new FieldUpdater<Docente, String>() {
            @Override
            public void update(int index, Docente object, String value) {
                formModificaDocente(object.getNome(), object.getCognome(), object.getPassword(), object.getMail(), object.getCodDocente());
            }
        });
        tabellaDocente.addColumn(colonna__modifica,"");
        colonna__modifica.setCellStyleNames("modifica__btn");

        tabellaDocente.setRowCount(result.length);
        tabellaDocente.setRowData(0, Arrays.asList(result));
        return tabellaDocente;
    }

    // Form per la creazione di un nuovo docente
    public void formDocenti(){
        user__container.clear();
        Button btn__chiudi = new Button("< Back");
        btn__chiudi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listaDocenti();
            }
        });
        btn__chiudi.addStyleName("back__btn");

        FormPanel creaDocente =new FormPanel();
        creaDocente.setAction("/creanuvoDocente");
        creaDocente.setMethod(FormPanel.METHOD_POST);
        creaDocente.addStyleName("form__crea-modifica");
        VerticalPanel docenteContainer = new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        docenteContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        docenteContainer.add(nome__textBox);
        final Label cognome__label = new Label("Cognome: ");
        docenteContainer.add(cognome__label);
        final TextBox cognome__textBox = new TextBox();
        docenteContainer.add(cognome__textBox);
        final Label password__label = new Label("Password: ");
        docenteContainer.add(password__label);
        final TextBox password__textBox = new TextBox();
        docenteContainer.add(password__textBox);


        final Button crea__btn = new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaDocente.submit();
            }
        });

        creaDocente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                if(nome__textBox.getText().length()==0 || cognome__textBox.getText().length()==0 ||
                        password__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
            }
        });

        creaDocente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                // Metodo che crea un nuovo docente e lo salva all'interno del DB
                docenteServiceAsync.creaDocente(nome__textBox.getText(), cognome__textBox.getText(), password__textBox.getText(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare il docente: "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Docente creato");
                        listaDocenti();
                    }
                });
            }
        });

        user__container.add(new HTML("<div class=\"user__title\">Crea un nuovo profilo Docente</div>"));
        user__container.add(btn__chiudi);
        docenteContainer.add(crea__btn);

        creaDocente.add(docenteContainer);
        user__container.add(creaDocente);
    }

    // Form per la modifica di un docente selezionato
    public void formModificaDocente(String nome, String cognome, String password, String mail, int codDocente){
        user__container.clear();
        Button btn__chiudi = new Button("< Back");
        btn__chiudi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listaDocenti();
            }
        });
        btn__chiudi.addStyleName("back__btn");
        FormPanel creaDocente =new FormPanel();
        creaDocente.setAction("/creanuvoDocente");
        creaDocente.setMethod(FormPanel.METHOD_POST);
        creaDocente.addStyleName("form__crea-modifica");
        VerticalPanel docenteContainer = new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        docenteContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        nome__textBox.setValue(nome);
        docenteContainer.add(nome__textBox);
        final Label cognome__label = new Label("Cognome: ");
        docenteContainer.add(cognome__label);
        final TextBox cognome__textBox = new TextBox();
        cognome__textBox.setValue(cognome);
        docenteContainer.add(cognome__textBox);
        final Label password__label = new Label("Password: ");
        docenteContainer.add(password__label);
        final TextBox password__textBox = new TextBox();
        password__textBox.setValue(password);
        docenteContainer.add(password__textBox);

        final Button crea__btn = new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaDocente.submit();
            }
        });

        creaDocente.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                if(nome__textBox.getText().length()==0 || cognome__textBox.getText().length()==0 ||
                        password__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
            }
        });

        creaDocente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                // Metodo che permette di sovrascrivere dati relativi ad un docente nel DB
                docenteServiceAsync.modificaDocente(nome__textBox.getText(), cognome__textBox.getText(), mail, password__textBox.getText(), codDocente, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare il docente: "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Docente modificato");
                        listaDocenti();
                    }
                });
            }
        });

        user__container.add(new HTML("<div class=\"user__title\">Modifica i dati del docente</div>"));
        user__container.add(btn__chiudi);
        docenteContainer.add(crea__btn);
        creaDocente.add(docenteContainer);
        user__container.add(creaDocente);
    }


    /*       ~ ~ Metodi per Segreteria ~ ~        */

    // Metodo per la visualizzazione delle segreterie all'interno della tabella "tabella__segreteria"
    public void listaSegreteria(){
        user__container.clear();
        segreteriaServiceAsync.getSegreteria(new AsyncCallback<Segreteria[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel visualizzare la segreteria "+caught);

            }
            @Override
            public void onSuccess(Segreteria[] result) {

                // Bottone che attraverso il metodo "formSegreteria" fa visualizzare il form
                // per la creazione di una nuova segreteria all'interno del container
                Button btn__creaSegreteria = new Button("Crea segreteria");
                btn__creaSegreteria.addStyleName("creaCorso__btn");
                btn__creaSegreteria.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formSegreteria();
                    }
                });

                // Tabella contenente tutte le segreterie iscritte alla piattaforma
                CellTable <Segreteria> tabella__segreteria= tabella__segreteria(result,"Sembra non ci siano segreterie");
                user__container.add(new HTML("<div class=\"user__title\">Segreterie iscritte alla piattaforma</div>"));
                user__container.add(btn__creaSegreteria);
                user__container.add(tabella__segreteria);
            }
        });
    }

    // Oggetto tabella con le informazioni delle segreterie
    private CellTable<Segreteria> tabella__segreteria(Segreteria[] result, String msg) {

        CellTable<Segreteria> tabellaSegreteria = new CellTable<>();
        tabellaSegreteria.addStyleName("tabella__segreteria");
        tabellaSegreteria.setEmptyTableWidget(new Label(msg));

        //Colonna nome
        TextColumn<Segreteria> colonna__nome = new TextColumn<Segreteria>() {
            @Override
            public String getValue(Segreteria object) {
                return object.getNome();
            }
        };
        tabellaSegreteria.addColumn(colonna__nome, "Nome");

        //Colonna cognome
        TextColumn<Segreteria> colonna__cognome = new TextColumn<Segreteria>() {
            @Override
            public String getValue(Segreteria object) {
                return object.getCognome();
            }
        };
        tabellaSegreteria.addColumn(colonna__cognome, "Cognome");

        //Colonna mail
        TextColumn<Segreteria> colonna__mail = new TextColumn<Segreteria>() {
            @Override
            public String getValue(Segreteria object) {
                return object.getMail();
            }
        };
        tabellaSegreteria.addColumn(colonna__mail, "Email");

        //Colonna password
        TextColumn<Segreteria> colonna__password = new TextColumn<Segreteria>() {
            @Override
            public String getValue(Segreteria object) {
                return object.getPassword();
            }
        };
        tabellaSegreteria.addColumn(colonna__password, "Password");


        tabellaSegreteria.setRowCount(result.length);
        tabellaSegreteria.setRowData(0, Arrays.asList(result));
        return tabellaSegreteria;
    }

    // Form per la creazione di una nuova segreteria
    public void formSegreteria(){
        user__container.clear();
        Button btn__chiudi = new Button("< Back");
        btn__chiudi.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listaSegreteria();
            }
        });
        btn__chiudi.addStyleName("back__btn");
        FormPanel creaSegreteria =new FormPanel();

        creaSegreteria.setAction("/creanuovaSegreteria");
        creaSegreteria.setMethod(FormPanel.METHOD_POST);
        creaSegreteria.addStyleName("form__crea-modifica");

        VerticalPanel segreteriaContainer = new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        segreteriaContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        segreteriaContainer.add(nome__textBox);
        final Label cognome__label = new Label("Cognome: ");
        segreteriaContainer.add(cognome__label);
        final TextBox cognome__textBox = new TextBox();
        segreteriaContainer.add(cognome__textBox);
        final Label password__label = new Label("Password: ");
        segreteriaContainer.add(password__label);
        final TextBox password__textBox = new TextBox();
        segreteriaContainer.add(password__textBox);


        final Button crea__btn = new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaSegreteria.submit();
            }
        });

        creaSegreteria.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                if(nome__textBox.getText().length()==0 || cognome__textBox.getText().length()==0 ||
                        password__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
            }
        });

        creaSegreteria.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                // Metodo che crea una nuova segreteria e la salva all'interno del DB
                segreteriaServiceAsync.creaSegretaria(nome__textBox.getText(), cognome__textBox.getText(), password__textBox.getText(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare la segreteria: "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Segreteria creata");
                        listaSegreteria();
                    }
                });
            }
        });

        user__container.add(new HTML("<div class=\"user__title\">Crea un nuovo profilo Segreteria</div>"));
        user__container.add(btn__chiudi);
        segreteriaContainer.add(crea__btn);
        creaSegreteria.add(segreteriaContainer);
        user__container.add(creaSegreteria);
    }

    public ListBox[] getInsertData(){
        //Giorno
        ListBox giorno__list = new ListBox();
        for(int i=1; i<=31; i++){
            giorno__list.addItem((String.valueOf(i)));
        }

        //Mese
        ListBox mese__list = new ListBox();
        for(int i=1; i<=12; i++){
            mese__list.addItem((String.valueOf(i)));
        }
        //Anno
        ListBox anno__list = new ListBox();
        for(int i=2003; i>=1904; i--){
            anno__list.addItem((String.valueOf(i)));
        }

        return new ListBox[]{giorno__list, mese__list, anno__list};
    }

    public String controlloMese(String giorno, String mese, String anno){
        //Controllo anno bisestile
        if(mese.equals("2")){
            int maxFeb;
            if((Integer.parseInt(anno)%4==0) && (Integer.parseInt(anno)%100==0)){
                maxFeb = 29;
            } else {
                maxFeb = 28;
            }
            if(Integer.parseInt(giorno) >= maxFeb){
                return null;
            }
        }

        //Controllo mesi con 30 giorni e non 31
        if(Integer.parseInt(giorno)>30){
            switch(mese){
                case "4": return null;
                case "6": return null;
                case "9": return null;
                case "11": return null;
            }
        }
        return giorno+"/"+mese+"/"+anno;
    }
}
