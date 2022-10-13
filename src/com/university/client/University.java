package com.university.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class University implements EntryPoint {

    FormPanel login;
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        caricaLogin();
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
        final Label labelUsername = new Label("Username:");
        labelUsername.getElement().setClassName("label");
        formPanel.add(labelUsername);
        final TextBox username = new TextBox();
        username.getElement().setClassName("input");
        username.setName("Username");
        formPanel.add(username);

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
        formPanel.add(send);

        login.add(formPanel);
        login.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent submitEvent) {
                if (username.getText().length() == 0 || password.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi.");
                    submitEvent.cancel();
                }
            }
        });

        //Condizioni se username e password sono corretti


        RootPanel.get("container").add(login);



    }
}
