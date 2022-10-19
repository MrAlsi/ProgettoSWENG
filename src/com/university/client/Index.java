package com.university.client;

import com.google.gwt.user.client.ui.*;

public class Index implements Contenuto{

    final static HTML sfondo = new HTML("<div id=\"sfondo__div\"> <img src=\"img/homepage__bg.jpg\" class=\"sfondo__img\"> <div class=\"titolo\">UNIVERSITY</div> <div class=\"sottotitolo\">pioneers of the future</div> </div>");

    @Override
    public void aggiungiContenuto() throws Exception {

        RootPanel.get("container").clear();
        VerticalPanel homepage__panel = new VerticalPanel();
        homepage__panel.addStyleName("homepage__panel");

        // PRIMA SEZIONE SCHERMATA PRINCIPALE
        homepage__panel.add(sfondo);

        // SECONDA SEZIONE SCHERMATA PRINCIPALE

        RootPanel.get("container").add(homepage__panel);
    }
}

