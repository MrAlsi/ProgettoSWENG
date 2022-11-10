package com.university.client.schermate;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.university.client.model.Docente;
import com.university.client.model.Studente;
import com.university.client.model.Utente;
import com.university.client.services.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class University implements EntryPoint {

    FormPanel login__panel;
    FormPanel creaStudente;
    private static UtenteServiceAsync utenteServiceAsync = GWT.create(UtenteService.class);
    private static StudenteServiceAsync studenteServiceAsync = GWT.create(StudenteService.class);
    private static DocenteServiceAsync docenteServiceAsync = GWT.create(DocenteService.class);
    private static AdminServiceAsync adminServiceAsync = GWT.create(AdminService.class);

    final HTML login__background = new HTML("" +
            "<div class=\"login__background\">" +
            "</div>" +
            "");

    final HTML login__img = new HTML("" +
            "<img src=\"img/ragazza__login.png\" class=\"login__img\">" +
            "");

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        Navbar();

        // Di default mostro il contenuto della homepage
        try {
            Index index = new Index();
            index.aggiungiContenuto();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //creaStudente();
    }

    public void Navbar() {

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setSpacing(5);

        Button home__btn = new Button("");
        home__btn.getElement().setId("home__GWTbtn");
        RootPanel.get("home__btn").add(home__btn);

        Button dipartimenti__btn = new Button("");
        dipartimenti__btn.getElement().setId("dipartimenti__GWTbtn");
        RootPanel.get("dipartimenti__btn").add(dipartimenti__btn);

        Button login__btn = new Button("");
        login__btn.getElement().setId("login__GWTbtn");
        RootPanel.get("login__btn").add(login__btn);

        Button contatti__btn = new Button("");
        contatti__btn.getElement().setId("contatti__GWTbtn");
        RootPanel.get("contatti__btn").add(contatti__btn);

        login__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                RootPanel.get("container").clear();
                try {
                    caricaLogin();
                    //creaStudente();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void caricaLogin() {

        login__panel = new FormPanel();
        login__panel.setAction("/login");
        login__panel.setMethod(FormPanel.METHOD_POST);

        VerticalPanel login__container = new VerticalPanel();
        login__container.add(login__img);
        login__container.getElement().setClassName("login__container");
        final Label email__label = new Label("Email:");
        email__label.getElement().setClassName("email__label");
        login__container.add(email__label);
        final TextBox email__input = new TextBox();
        email__input.getElement().setClassName("email__input");
        email__input.setName("Email");
        login__container.add(email__input);

        final Label password__label = new Label("Password:");
        password__label.getElement().setClassName("password__label");
        login__container.add(password__label);
        final PasswordTextBox password__input = new PasswordTextBox();
        password__input.getElement().setClassName("password__input");
        password__input.setName("Password");
        login__container.add(password__input);

        Button login__btn = new Button("Login");
        login__btn.getElement().setClassName("login__btn");
        login__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login__panel.submit();
            }
        });

        login__panel.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (email__input.getText().length() == 0 || password__input.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi.");
                    submitEvent.cancel();
                }
            }
        });
        adminServiceAsync.creaDocente("emanuel", "alsina", "password", new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Boolean result) {
                Window.alert("Docente creato");

            }
        });

        //Condizioni se username e password sono corretti
        login__panel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                utenteServiceAsync.login(email__input.getText(), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Utente o password sbagliati " + caught);
                    }

                    @Override
                    public void onSuccess(String result) {
                        //Window.alert("sei dentro");
                        //Carica account
                        switch (result) {
                            case "Studente":
                                //Carica pagina studente
                                studenteServiceAsync.loginStudente(email__input.getText(), password__input.getText(), new AsyncCallback<Studente>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("Utente o password sbagliati" + caught);
                                    }

                                    @Override
                                    public void onSuccess(Studente result) {
                                        if(result==null){
                                            Window.alert("Utente o password sbagliatiii");
                                        } else {
                                            SchermataStudente schermataStudente = new SchermataStudente();
                                            schermataStudente.accesso(result);
                                            Window.alert("studente");
                                        }
                                    }
                                });
                                break;
                            case "Docente":
                                docenteServiceAsync.loginDocente(email__input.getText(), password__input.getText(), new AsyncCallback<Docente>() {
                                    @Override
                                    public void onFailure(Throwable caught) {

                                    }

                                    @Override
                                    public void onSuccess(Docente result) {
                                        if(result==null){
                                            Window.alert("Utente o password sbagliati");
                                        } else {
                                            //SchermataDocente schermataDocente = new SchermataDocente();
                                            //schermataStudente.accesso(result);
                                            Window.alert("Docente");
                                        }
                                    }
                                });

                                break;
                            case "Segreteria":
                                //Carica segreteria
                                Window.alert("segreteria.");
                                break;
                            default:
                                //Carica admin
                                Window.alert("Admin");
                                break;


                        }
                        //Window.alert(String.valueOf(result.));
                        //Cambio pagina in PortaleStudente

                    }
                });
            }
        });

        login__container.add(login__btn);

        login__panel.add(login__container);
        RootPanel.get("body").add(login__background);
        RootPanel.get("container").add(login__panel);


    }
}
