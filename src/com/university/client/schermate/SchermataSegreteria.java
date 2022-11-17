package com.university.client.schermate;

import com.google.gwt.cell.client.ButtonCell;
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

public class SchermataSegreteria {
    Segreteria segreteria;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private StudenteServiceAsync serviceStudente = GWT.create(StudenteService.class);
    private SostieneServiceAsync serviceSostiene = GWT.create(SostieneService.class);
    private EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private FrequentaServiceAsync serviceFrequenta = GWT.create(FrequentaService.class);
    private CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);


    public void accesso(Segreteria segreteria) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
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

        nav__user.add(btn__studenti);
        nav__user.add(btn__valutazioni);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }

}