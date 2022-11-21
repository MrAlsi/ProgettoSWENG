package com.university.client.schermate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.university.client.services.*;

public class Index implements Contenuto {
    private static CorsoServiceAsync corsoServiceAsync = GWT.create(CorsoService.class);
    private static DocenteServiceAsync docenteServiceAsync = GWT.create(DocenteService.class);
    private static StudenteServiceAsync studenteServiceAsync = GWT.create(StudenteService.class);
    final static HTML sfondo = new HTML("" +
            "<section id=\"sfondo__div\">" +
                "<img src=\"img/homepage__bg.jpg\" class=\"sfondo__img\">" +
                "<div class=\"titolo\">UNIVERSITY</div>" +
                "<div class=\"sottotitolo\">pioneers of the future</div> " +
            "</section>"
    );
    final static HTML paragrafo__1 = new HTML("" +
            "<section id=\"paragrafo__1\">" +
                "<div class=\"card__p1\">" +
                    "<img src=\"img/card1.jpg\" class=\"card__img\">" +
                    "<div class=\"card__titolo\">10-14 gennaio: torna il Job Day</div>" +
                    "<div class=\"card__descrizione\">Tradizionale appuntamento per l’incontro di studentesse, studenti e laureate/i con il mondo del lavoro. Presenti 118 realtà tra aziende ed enti istituzionali</div>" +
                "</div> " +
                "<div class=\"card__p1\">" +
                    "<img src=\"img/card2.jpg\" class=\"card__img\">" +
                    "<div class=\"card__titolo\">20-22 gennaio: Esperienze di Ingegneria Offshore e Marina</div>" +
                    "<div class=\"card__descrizione\">In Aula Magna l’edizione annuale degli studi di aggiornamento dell’Associazione di Ingegneria Offshore e Marina</div>" +
                "</div> " +
                "<div class=\"card__p1\">" +
                    "<img src=\"img/card3.jpg\" class=\"card__img\">" +
                    "<div class=\"card__titolo\">28 gennaio: Blockchain, NFT, Metaverso & proprietà intellettuale</div>" +
                    "<div class=\"card__descrizione\">Nuove opportunità per le imprese e criticità da gestire, tra innovazione tecnica e innovazione giuridica</div>" +
                "</div> " +
            "</section>"
    );
    final static HTML paragrafo__3 = new HTML("" +
            "<section id=\"paragrafo__3\">" +
                "<h2>I NOSTRI DIPARTIMENTI</h2>" +
                "<h5>scienze matematiche, fisiche e informatiche - scienze economiche e aziendali - ingegneria e architettura - medicina e chirurgia</h5>" +
            "<div class=\"container__p3\">" +
                "<div class=\"dipartimenti__img__container\">" +
                  "<div class=\"dipartimenti__img\">" +
                    "<img class=\"dipartimenti__img__tag\" src=\"img/dipartimenti__home.jpg\" alt=\"I nostri dipartimenti\" />" +
                  "</div>" +
                "</div>" +
                "<div class=\"dipartimenti__text__container\">" +
                    "<p>Essendo un campus universitario dove tutto è vicino, tutti gli edifici compreso l'Ospedale Universitario e le biblioteche specializzate sono facilmente raggiungibili.</p>" +
                    "<p>I nostri dipartimenti universitari godono di un'eccellente reputazione grazie a un numero eccezionalmente elevato di centri di ricerca collaborativa.</p>" +
                    "<p>Inoltre, la zona in cui risiediamo offre un ambiente attraente con un'elevata qualità della vita.</p>" +
                "</div>" +
              "</div>" +
            "</section>" +
            "");

    final static HTML footer = new HTML("" +
            "<section id=\"footer\">" +
            "<div class=\"footer__container__left\">" +
            "<div class=\"footer__title\"><b>UNIVERSITY</b></div>" +
            "<div class=\"footer__description\">University.com <i>L'UNIVERSITA' DEL FUTURO</i> un insieme di insegnamenti all'avanguardia sviluppati per formare grandi menti che possano contribuire allo sviluppo globale di tutti i settori trattati nei nostri percorsi di insegnamento suddivisi in quattro grandi dipartimenti forniti di tuttui i comfort necessari al raggiungimento del nostro fine ultimo. </div>" +
            "</div>" +
            "<div class=\"footer__container__right\">" +
            "<div class=\"footer__contacts\"><b>CONTATTI</b></div>" +
            "<div class=\"footer__details\">+39 111 222 333</div>" +
            "<div class=\"footer__details\">university@gmail.com</div>" +
            "<div class=\"footer__details\">Via Marco Rossi, ABCDE (AB), 12345</div>" +
            "</div>" +
            "</section>" +
            "");


    @Override
    public void aggiungiContenuto() throws Exception {

        int[] infoPaginaIniziale = new int[3];

        RootPanel.get("container").clear();
        VerticalPanel homepage__panel = new VerticalPanel();
        homepage__panel.addStyleName("homepage__panel");

        corsoServiceAsync.getNumeroCorsi(new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error: corsoServiceAsync.getNumeroCorsi " + caught);
            }

            @Override
            public void onSuccess(Integer result) {
                infoPaginaIniziale[0] = result;
                docenteServiceAsync.getNumeroDocenti(new AsyncCallback<Integer>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Error: docenteServiceAsync.getNumeroDocenti " + caught);

                    }

                    @Override
                    public void onSuccess(Integer result) {
                        infoPaginaIniziale[1] = result;
                        studenteServiceAsync.getNumeroStudenti(new AsyncCallback<Integer>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Error: studenteServiceAsync.getNumeroStudenti " + caught);
                            }
                            @Override
                            public void onSuccess(Integer result) {
                                infoPaginaIniziale[2] = result;
                                // SFONDO SCHERMATA PRINCIPALE
                                homepage__panel.add(sfondo);

                                // PARAGRAFO 1 SCHERMATA PRINCIPALE
                                homepage__panel.add(paragrafo__1);

                                // PARAGRAFO 2 SCHERMATA PRINCIPALE
                                HTML paragrafo__2 = new HTML("" +
                                        "<section id=\"paragrafo__2\">" +
                                        "<div class=\"title__p2\"><b>CORSI: </b></div>" +
                                        "<div id=\"dipartimenti__p2\">" +  infoPaginaIniziale[0] + "</div>" +
                                        "<div class=\"title__p2\"><b>INSEGNANTI: </b></div>" +
                                        "<div id=\"insegnanti__p2\">" +  infoPaginaIniziale[1] + "</div>" +
                                        "<div class=\"title__p2\"><b>STUDENTI: </b></div>" +
                                        "<div id=\"studenti__p2\"> " +  infoPaginaIniziale[2] + "</div>" +
                                        "</section>" +
                                        "");

                                homepage__panel.add(paragrafo__2);

                                // PARAGRAFO 3 SCHERMATA PRINCIPALE
                                homepage__panel.add(paragrafo__3);

                                // FOOTER SCHERMATA PRINCIPALE
                                homepage__panel.add(footer);

                                RootPanel.get("container").add(homepage__panel);
                            }
                        });
                    }
                });


            }
        });
    }
}

