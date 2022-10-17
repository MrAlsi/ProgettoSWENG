package com.university.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class University implements EntryPoint {

    FormPanel login;
    FormPanel creaStudente;
    private static UtenteServiceAsync utenteServiceAsync = GWT.create(UtenteService.class);
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        //caricaLogin();
        creaStudente();
    }

    public void caricaLogin(){
        /*HTML contenitore = new HTML("<div id=\"login\">");
        Label usernameLabel = new Label("Username");
        TextBox usernameTextBox = new TextBox();
        Label passwordLabel = new Label("Password");
        PasswordTextBox passwordTextBox = new PasswordTextBox();*/

        login = new FormPanel();
        login.setAction("/login");
        login.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelMail = new Label("Mail:");
        labelMail.getElement().setClassName("label");
        formPanel.add(labelMail);
        final TextBox mail = new TextBox();
        mail.getElement().setClassName("input");
        mail.setName("Mail");
        formPanel.add(mail);

        final Label labelPassword = new Label("Password:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.getElement().setClassName("input");
        password.setName("Password");
        formPanel.add(password);

        Button send = new Button("Login");
        send.getElement().setClassName("btn-login");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login.submit();
            }
        });

        login.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (mail.getText().length() == 0 || password.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi.");
                    submitEvent.cancel();
                }
            }
        });
        formPanel.add(send);

        login.add(formPanel);

        //Condizioni se username e password sono corretti

       /* login.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

            }
        })*/
        RootPanel.get("container").add(login);
    }

    public void creaStudente(){
        creaStudente = new FormPanel();
        creaStudente.setAction("creaUtente");
        creaStudente.setMethod(FormPanel.METHOD_POST);

        VerticalPanel formPanel = new VerticalPanel();
        final Label labelMail = new Label("Mail:");
        labelMail.getElement().setClassName("label");
        formPanel.add(labelMail);
        final TextBox mail = new TextBox();
        mail.getElement().setClassName("input");
        mail.setName("Username");
        formPanel.add(mail);
        final Label labelPassword = new Label("Password:");
        labelPassword.getElement().setClassName("label");
        formPanel.add(labelPassword);
        final TextBox password = new TextBox();
        password.getElement().setClassName("input");
        password.setName("Password");
        formPanel.add(password);
        Button creaButton = new Button("Crea");
        creaButton.getElement().setClassName("btn-login");

        creaButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaStudente.submit();
            }
        });

        creaStudente.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                utenteServiceAsync.creaUtente(mail.getText(), password.getText(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                       Window.alert("Non è stato possibile creare l'utente");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("Utente creato con successo");
                    }
                });
            }
        });

        Button studenti = new Button("Get studenti");
        studenti.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                utenteServiceAsync.getStudenti(new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("Oh yes");
                    }
                });
            }
        });



        formPanel.add(creaButton);
        creaStudente.add(formPanel);
        RootPanel.get("container").add(creaStudente);
        RootPanel.get("container").add(studenti);
    }
}
