package com.university.client.schermate;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.university.client.model.*;
import com.university.client.services.*;

import java.util.ArrayList;
import java.util.List;

public class SchermataAdmin {

    VerticalPanel user__container;
    VerticalPanel nav__user;
    private static StudenteServiceAsync studenteServiceAsync = GWT.create(StudenteService.class);
    private static DocenteServiceAsync docenteServiceAsync = GWT.create(DocenteService.class);
    private static SegreteriaServiceAsync segreteriaServiceAsync = GWT.create(SegreteriaService.class);
    private static FrequentaServiceAsync frequentaServiceAsync= GWT.create(FrequentaService.class);
    private static SostieneServiceAsync sostieneServiceAsync= GWT.create(SostieneService.class);


    //Metodo che parte quando si accede come admin
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

        //formStudenti();
        //
        //formSegreteria();
    }

    /*       ~ ~ Metodi per Studente ~ ~        */

    //visualizzazione della lista di studenti
    public void listaStudenti(){
        user__container.clear();
        studenteServiceAsync.getStudenti(new AsyncCallback<Studente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel visualizzare gli studenti "+caught);
            }

            @Override
            public void onSuccess(Studente[] result) {
                //Bottone per creare studente
                Button btn__creaStudente = new Button("Crea studente");
                btn__creaStudente.addStyleName("creaCorso__btn");
                btn__creaStudente.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formStudenti();
                    }
                });
                //Tabella con tutti gli stundenti
                CellTable <Studente> tabella__studenti= tabella__studenti(result,"Sembra non ci siano studenti");
                user__container.add(btn__creaStudente);
                user__container.add(tabella__studenti);
            }
        });
    }

    //Oggetto tabella con gli studenti
    private CellTable<Studente> tabella__studenti(Studente[] result, String msg) {
        List<Studente> studenti= new ArrayList<>();
        for(Studente studente: result){
            studenti.add(studente);
        }
        CellTable<Studente> tabellaStudente= new CellTable<>();
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
        colonna__modifica.setCellStyleNames("modificaStudente__btn");

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
                visualizzaCorsiStudente(object);
            }
        });
        tabellaStudente.addColumn(colonna__visualizzaCorsi,"");
        colonna__visualizzaCorsi.setCellStyleNames("visualizzCorsi__btn");

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
                visualizzaEsamiStudente(object);
            }
        });

        tabellaStudente.addColumn(colonna__visualizzaEsami,"");
        colonna__visualizzaEsami.setCellStyleNames("visualizzaEsami__btn");

        ButtonCell cella__elimina= new ButtonCell();
        Column<Studente, String> colonna__elimina=new Column<Studente, String>(cella__elimina) {
            @Override
            public String getValue(Studente object) {
                return "Elimina";
            }
        };
        colonna__elimina.setFieldUpdater(new FieldUpdater<Studente, String>() {
            @Override
            public void update(int index, Studente object, String value) {
                studenteServiceAsync.eliminaStudente(object.getMatricola(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore durante l'eliminazione dello studente "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Studente eliminato");
                        try{
                            listaStudenti();
                        }catch (Exception e){
                            Window.alert("oi"+ e);
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

        tabellaStudente.addColumn(colonna__elimina,"");
        colonna__elimina.setCellStyleNames("eliminaStudente__btn");
        tabellaStudente.setRowCount(studenti.size());
        tabellaStudente.setRowData(0,studenti);
        return tabellaStudente;
    }

    //form creazione di un nuovo studente
    public void formStudenti(){
        user__container.clear();
        FormPanel creaStudente=new FormPanel();
        creaStudente.setAction("/creanuvoStudente");
        creaStudente.setMethod(FormPanel.METHOD_POST);
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
        studenteContainer.add(data__label);
        final TextBox data__textBox = new TextBox();
        studenteContainer.add(data__textBox);

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
                        password__textBox.getText().length()==0 || data__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
            }
        });

        creaStudente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                studenteServiceAsync.creaStudente(nome__textBox.getText(), cognome__textBox.getText(), password__textBox.getText(), data__textBox.getText(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare lo studente: "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Studente creato");
                    }
                });
            }
        });
        studenteContainer.add(crea__btn);
        creaStudente.add(studenteContainer);

        user__container.add(creaStudente);
    }

    //form modifica di uno studente selezionato
    public void formModificaStudente(String nome, String cognome, String password, String mail, String dataNascita, int matricola){
        RootPanel.get("container").clear();
        FormPanel modificaStudente =new FormPanel();
        modificaStudente.setAction("/creanuvoStudente");
        modificaStudente.setMethod(FormPanel.METHOD_POST);
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
        studenteContainer.add(data__label);
        final TextBox data__textBox = new TextBox();
        data__textBox.setValue(dataNascita);
        studenteContainer.add(data__textBox);

        final Button crea__btn = new Button("Crea");
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
                        password__textBox.getText().length()==0 || data__textBox.getText().length()==0){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
            }
        });

        modificaStudente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                studenteServiceAsync.modificaStudente(nome__textBox.getText(), cognome__textBox.getText(), mail, password__textBox.getText(), data__textBox.getText(), matricola, new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare lo studente: "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Studente modificato");
                    }
                });
            }
        });
        studenteContainer.add(crea__btn);

        modificaStudente.add(studenteContainer);
        RootPanel.get("container").add(modificaStudente);
    }

    //visualizzazione degli esami di uno studente
    public void visualizzaEsamiStudente(Studente studente){
        RootPanel.get("container").clear();
        sostieneServiceAsync.getSostieneStudenteConVoto(studente.getMatricola(), new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare gli esami " + caught);

            }

            @Override
            public void onSuccess(Sostiene[] result) {
                FormPanel visualizzaEsami = new FormPanel();
                VerticalPanel esamiContainer = new VerticalPanel();
                CellTable<Sostiene> tabella__esami = tabella__esami(result, "Sembra non ci siano esami verbalizzati");
                esamiContainer.add(tabella__esami);
                visualizzaEsami.add(esamiContainer);
                RootPanel.get("container").add(visualizzaEsami);
            }
        });
    }

    //Oggetto tabella con gli esami di uno studente
    private CellTable<Sostiene> tabella__esami(Sostiene[] result, String msg) {
        List<Sostiene> sostiene = new ArrayList<>();
        for (Sostiene sostenuto : result) {
            sostiene.add(sostenuto);
        }
        CellTable<Sostiene> tabellaSostiene = new CellTable<>();
        tabellaSostiene.setEmptyTableWidget(new Label(msg));

        TextColumn<Sostiene> colonna__nome = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return null;
            }
        };
        tabellaSostiene.addColumn(colonna__nome,"Esami");
        tabellaSostiene.setRowCount(sostiene.size());
        tabellaSostiene.setRowData(0,sostiene);
        return tabellaSostiene;
    }

    //visualizzazione corsi di uno studente selezionato
    public void visualizzaCorsiStudente(Studente studente) {
        RootPanel.get("container").clear();
        frequentaServiceAsync.getMieiCorsi(studente.getMatricola(), new AsyncCallback<ArrayList<Frequenta>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare i corsi " + caught);

            }

            @Override
            public void onSuccess(ArrayList<Frequenta> result) {
                FormPanel visualizzaCorsi = new FormPanel();
                VerticalPanel corsiContainer = new VerticalPanel();
                CellTable<Frequenta> tabella__corsi = tabella__corsi(result, "Sembra non ci siano corsi ai cui è iscritto");
                corsiContainer.add(tabella__corsi);
                visualizzaCorsi.add(corsiContainer);
                RootPanel.get("container").add(visualizzaCorsi);
            }
        });
    }

    //Oggetto tabella con i corsi di uno studente
    private CellTable<Frequenta> tabella__corsi(ArrayList<Frequenta> result, String msg) {
        List<Frequenta> frequenta = new ArrayList<>();
        for (Frequenta freq : result) {
            frequenta.add(freq);
        }
        CellTable<Frequenta> tabellaFrequenta = new CellTable<>();
        tabellaFrequenta.setEmptyTableWidget(new Label(msg));

        TextColumn<Frequenta> colonna__nome = new TextColumn<Frequenta>() {
            @Override
            public String getValue(Frequenta object) {
                return object.getNomeCorso();
            }
        };
        tabellaFrequenta.addColumn(colonna__nome,"Corsi");
        tabellaFrequenta.setRowCount(frequenta.size());
        tabellaFrequenta.setRowData(0,frequenta);
        return tabellaFrequenta;
    }



    /*       ~ ~ Metodi per Docente ~ ~        */

    //Lista di tutti i docenti
    public void listaDocenti(){
        user__container.clear();
        docenteServiceAsync.getDocenti(new AsyncCallback<Docente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare i docenti "+caught);
            }
            @Override
            public void onSuccess(Docente[] result) {

                //Bottone per creare un docente
                Button btn__creaDocente = new Button("Crea docente");
                btn__creaDocente.addStyleName("creaCorso__btn");
                btn__creaDocente.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formDocenti();
                    }
                });

                //Tabella con tutti i docenti
                CellTable <Docente> tabella__docenti = tabella__docenti(result,"Sembra non ci siano docenti");
                user__container.add(btn__creaDocente);
                user__container.add(tabella__docenti);

            }
        });
    }

    //Oggetto tabella per i docenti
    private CellTable<Docente> tabella__docenti(Docente[] result, String msg) {
        List<Docente> docenti = new ArrayList<>();
        for (Docente docente : result) {
            docenti.add(docente);
        }
        CellTable<Docente> tabellaDocente = new CellTable<>();
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
        colonna__modifica.setCellStyleNames("modificaDocente__btn");

        ButtonCell cella__elimina= new ButtonCell();
        Column<Docente, String> colonna__elimina=new Column<Docente, String>(cella__elimina) {
            @Override
            public String getValue(Docente object) {
                return "Elimina";
            }
        };

        colonna__elimina.setFieldUpdater(new FieldUpdater<Docente, String>() {
            @Override
            public void update(int index, Docente object, String value) {
                docenteServiceAsync.eliminaDocente(object.getCodDocente(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore durante l'eliminazione del docente "+caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Docente eliminato");
                        try{
                            listaDocenti();
                        }catch (Exception e){
                            Window.alert("oi"+ e);
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        tabellaDocente.addColumn(colonna__elimina,"");
        colonna__elimina.setCellStyleNames("eliminaDocente__btn");

        tabellaDocente.setRowCount(docenti.size());
        tabellaDocente.setRowData(0,docenti);
        return tabellaDocente;
    }

    //form creazione di un docente
    public void formDocenti(){
        user__container.clear();
        FormPanel creaDocente =new FormPanel();
        creaDocente.setAction("/creanuvoDocente");
        creaDocente.setMethod(FormPanel.METHOD_POST);
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
        docenteContainer.add(crea__btn);

        creaDocente.add(docenteContainer);
        user__container.add(creaDocente);
    }

    //form per modificare un docente
    public void formModificaDocente(String nome, String cognome, String password, String mail, int codDocente){
        user__container.clear();
        FormPanel creaDocente =new FormPanel();
        creaDocente.setAction("/creanuvoDocente");
        creaDocente.setMethod(FormPanel.METHOD_POST);
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

        docenteContainer.add(crea__btn);
        creaDocente.add(docenteContainer);
        user__container.add(creaDocente);
    }



    /*       ~ ~ Metodi per Segreteria ~ ~        */

    //Lista con tutti i Segretari
    public void listaSegreteria(){
        user__container.clear();
        segreteriaServiceAsync.getSegreteria(new AsyncCallback<Segreteria[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel visualizzare la segreteria "+caught);

            }
            @Override
            public void onSuccess(Segreteria[] result) {
                //Bottone per creare una segreteria
                Button btn__creaSegreteria = new Button("Crea segreteria");
                btn__creaSegreteria.addStyleName("creaCorso__btn");
                btn__creaSegreteria.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formSegreteria();
                    }
                });
                //Tabella con tutti gli stundenti
                CellTable <Segreteria> tabella__segreteria= tabella__segreteria(result,"Sembra non ci siano segreterie");
                user__container.add(btn__creaSegreteria);
                user__container.add(tabella__segreteria);
            }
        });
    }

    //Tabella con le segreterie
   private CellTable<Segreteria> tabella__segreteria(Segreteria[] result, String msg) {
        List<Segreteria> segretari = new ArrayList<>();
        for(Segreteria segreteria : result) {
            segretari.add(segreteria);
        }

        CellTable<Segreteria> tabellaSegreteria = new CellTable<>();
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

       //Colonna elimina
       ButtonCell cella__elimina= new ButtonCell();
       Column<Segreteria, String> colonna__elimina = new Column<Segreteria, String>(cella__elimina) {
           @Override
           public String getValue(Segreteria object) {
               return "Elimina";
           }
       };

       colonna__elimina.setFieldUpdater(new FieldUpdater<Segreteria, String>() {
           @Override
           public void update(int index, Segreteria object, String value) {
               segreteriaServiceAsync.eliminaSegreteria(object.getMail(), new AsyncCallback<Boolean>() {
                   @Override
                   public void onFailure(Throwable caught) {

                   }

                   @Override
                   public void onSuccess(Boolean result) {
                        Window.alert("Segreteria elinata");
                       try{
                           listaSegreteria();
                       }catch (Exception e){
                           Window.alert("Errore nel ricaricare la pagina: "+ e);
                           throw new RuntimeException(e);
                       }
                   }
               });
           }
       });

       tabellaSegreteria.addColumn(colonna__elimina, "");
       //--> CSS

       tabellaSegreteria.setRowCount(segretari.size());
       tabellaSegreteria.setRowData(0, segretari);
       return tabellaSegreteria;
   }

    //form per creare un utente "segreteria"
    public void formSegreteria(){
        user__container.clear();
        FormPanel creaSegreteria =new FormPanel();
        creaSegreteria.setAction("/creanuovaSegreteria");
        creaSegreteria.setMethod(FormPanel.METHOD_POST);
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
        segreteriaContainer.add(crea__btn);

        creaSegreteria.add(segreteriaContainer);
        user__container.add(creaSegreteria);
    }
}
