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
import com.university.client.model.Studente;
import com.university.client.services.*;

import java.util.ArrayList;
import java.util.List;

public class SchermataAdmin {

    private static StudenteServiceAsync studenteServiceAsync = GWT.create(StudenteService.class);
    private static DocenteServiceAsync docenteServiceAsync = GWT.create(DocenteService.class);
    private static SegreteriaServiceAsync segreteriaServiceAsync = GWT.create(SegreteriaService.class);


    public void accesso(){
        RootPanel.get("container").clear();
        //formStudenti();
        //formDocenti();
        //formSegreteria();
        listaStudenti();
    }

    public void listaStudenti(){
        RootPanel.get("container").clear();
        studenteServiceAsync.getStudenti(new AsyncCallback<Studente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel visualizzare gli studenti "+caught);
            }

            @Override
            public void onSuccess(Studente[] result) {
                FormPanel visualizzaStudenti= new FormPanel();
                VerticalPanel studentiContainer= new VerticalPanel();
                CellTable <Studente> tabella__studenti= tabella__studenti(result,"Sembra non ci siano studenti");
                studentiContainer.add(tabella__studenti);
                visualizzaStudenti.add(studentiContainer);
                RootPanel.get("container").add(visualizzaStudenti);
            }
        });
    }

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
        TextColumn<Studente> colonna__compleanno= new TextColumn<Studente>() {
            @Override
            public String getValue(Studente object) {
                return object.getDataNascita();
            }
        };
        tabellaStudente.addColumn(colonna__compleanno, "Compleanno");
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


    public void formSegreteria(){
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
                    }
                });
            }
        });
        segreteriaContainer.add(crea__btn);

        creaSegreteria.add(segreteriaContainer);
        RootPanel.get("container").add(creaSegreteria);
    }
    public void formDocenti(){
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
                    }
                });
            }
        });
        docenteContainer.add(crea__btn);

        creaDocente.add(docenteContainer);
        RootPanel.get("container").add(creaDocente);
    }

    public void formModificaDocente(String nome, String cognome, String password, String mail, int codDocente){
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
                        //lista docenti
                    }
                });
            }
        });
        docenteContainer.add(crea__btn);
        creaDocente.add(docenteContainer);
        RootPanel.get("container").add(creaDocente);
    }

    public void formStudenti(){
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
        RootPanel.get("container").add(creaStudente);
    }

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
}
