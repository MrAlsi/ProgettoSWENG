package com.university.client.schermate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
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
}
