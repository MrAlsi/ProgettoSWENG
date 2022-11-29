package com.university.client.schermate;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.datepicker.client.DateBox;
import com.university.client.model.Docente;
import com.university.client.model.Sostiene;
import com.university.client.services.*;
import com.university.client.model.Corso;
import com.university.client.model.Esame;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

public class SchermataDocente {
    Docente docente;
    VerticalPanel user__container;
    VerticalPanel nav__user;
    private DocenteServiceAsync serviceDocente = GWT.create(DocenteService.class);
    private CorsoServiceAsync serviceCorso = GWT.create(CorsoService.class);
    private EsameServiceAsync serviceEsame = GWT.create(EsameService.class);
    private SostieneServiceAsync serviceSostiene = GWT.create(SostieneService.class);


    public void accesso(Docente docente) {
        RootPanel.get("container").clear();

        user__container = new VerticalPanel();
        user__container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        user__container.getElement().setId("user__container");
        user__container.addStyleName("user__container");

        nav__user = new VerticalPanel();
        nav__user.getElement().setId("nav__user");
        nav__user.addStyleName("nav__user");

        this.docente = docente;

        Button btn__profilo = new Button("Profilo");
        Button btn__corsi = new Button("Gestione corsi");
        Button btn__esami = new Button("Gestione esami");

        btn__profilo.addStyleName("nav__user__btn");
        btn__corsi.addStyleName("nav__user__btn");
        btn__esami.addStyleName("nav__user__btn");

        btn__profilo.addClickHandler(clickEvent -> {
            try {
                form__profilo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__corsi.addClickHandler(clickEvent -> {
            try {
                form__corsi();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn__esami.addClickHandler(clickEvent -> {
            try {
                form__esami();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nav__user.add(btn__profilo);
        nav__user.add(btn__corsi);
        nav__user.add(btn__esami);

        RootPanel.get("container").add(nav__user);
        user__container.add(new HTML("<div class=\"user__title\">my<b>University</b></div>"));
        RootPanel.get("container").add(user__container);

    }


    public void form__profilo(){
        serviceDocente.getDocente(docente.getCodDocente(), new AsyncCallback<Docente>() {
            @Override
            public void onFailure(Throwable caught) {

            }
            @Override
            public void onSuccess(Docente result) {
                user__container.clear();
                HTML user__info = new HTML("<div class=\"content__profilo\"><b>Nome: </b>" + result.getNome()
                        + "<br /><b>Cognome: </b>" + result.getCognome()
                        + "<br /><b>Codice Docente: </b>" + result.getCodDocente()
                        + "<br /><b>E-mail: </b>" + result.getMail()
                        + "</div>");
                user__container.add(new HTML("<div class=\"user__title\">Profilo</div>"));
                user__container.add(user__info);
            }
        });
        RootPanel.get("container").add(user__container);
    }



    public void form__corsi() throws Exception {
        user__container.clear();
        serviceCorso.getCorsiDocente(docente.getCodDocente(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiDocente: " + throwable.getMessage());
            }
            @Override
            public void onSuccess(Corso[] result) {

                user__container.add(new HTML("<div class=\"user__title\">Gestione Corsi</div>"));

                Button btnCreaCorso = new Button("Crea corso");
                btnCreaCorso.addStyleName("creaCorso__btn");
                btnCreaCorso.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        formCreaCorso();
                    }
                });
                user__container.add(btnCreaCorso);

                CellTable<Corso> tabella__corsi = tabella__corsi(result, "Sembra che tu non appartenga a nessun corso, creane uno!");
                user__container.add(tabella__corsi);

            }
        });
    }

    public void form__esami() throws Exception {
        user__container.clear();
        serviceCorso.getCorsiDocente(docente.getCodDocente(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Failure on getCorsiDocente: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Corso[] result) {

                serviceEsame.getEsamiCorso(result, new AsyncCallback<Esame[]>() {

                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure on getCorsiDocente: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Esame[] esami) {

                        user__container.add(new HTML("<div class=\"user__title\">Gestione Esami</div>"));

                        Button btnCreaEsame = new Button("Crea esame");
                        btnCreaEsame.addStyleName("creaEsame__btn");
                        btnCreaEsame.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                formCreaEsame();
                            }
                        });
                        user__container.add(btnCreaEsame);

                        CellTable<Esame> tabella__esami = tabella__esami(esami, "Sembra che tu non abbia ancora esami in programma, creane uno!");
                        user__container.add(tabella__esami);

                    }
                });
            }
        });
    }

    private CellTable<Corso> tabella__corsi(Corso[] result, String msg) {

        CellTable<Corso> tabella__corsi = new CellTable<>();
        tabella__corsi.addStyleName("tabella__corsi");
        tabella__corsi.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__corsi.setEmptyTableWidget(new Label(msg));

        TextColumn<Corso> colonna__nome = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getNome();
            }
        };
        tabella__corsi.addColumn(colonna__nome, "Nome");

        TextColumn<Corso> colonna__periodo = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDataInizio() + " - " + object.getDataFine();
            }
        };
        tabella__corsi.addColumn(colonna__periodo, "Periodo");

        TextColumn<Corso> colonna__descrizione = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                return object.getDescrizione();
            }
        };
        tabella__corsi.addColumn(colonna__descrizione, "Descrizione");

        TextColumn<Corso> colonna__coDocente = new TextColumn<Corso>() {
            @Override
            public String getValue(Corso object) {
                if(object.getCoDocente()==-1){
                    return "Nessun codocente";
                } else {
                    return String.valueOf(object.getCoDocente());
                }
            }
        };
        tabella__corsi.addColumn(colonna__coDocente, "Co-Docente");

        ButtonCell cella__modifica = new ButtonCell();
        Column<Corso, String> colonna__modifica = new Column<Corso, String>(cella__modifica) {
            @Override
            public String getValue(Corso object) {
                return "Modifica";
            }
        };

        tabella__corsi.addColumn(colonna__modifica, "");
        colonna__modifica.setCellStyleNames("modificaCorso__btn");

        colonna__modifica.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(int index, Corso object, String value) {
                try {
                    formModificaCorso(object.getNome(), object.getDataInizio(),
                                        object.getDataFine(), object.getDescrizione(),
                                        object.getCoDocente(), object.getEsame());
                } catch (ParseException e) {
                    System.out.println("ERRORE:  " + e);
                    throw new RuntimeException(e);
                }
            }
        });
        /*
        colonna__modifica.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(Corso object) {
                user__container.clear();
                user__container.add(new HTML("<div class=\"user__title\">Modifica corso</div>"));
                user__container.add((new form__modificaCorso(docente, object)).getForm());
            }
        }); */

        ButtonCell cella__elimina = new ButtonCell();
        Column<Corso, String> colonna__elimina = new Column<Corso, String>(cella__elimina) {
            @Override
            public String getValue(Corso object) {
                return "Elimina";
            }
        };

        //Aggiunge il metodo eliminaCorso al bottone elimina
        colonna__elimina.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(int index, Corso object, String value) {
                serviceCorso.eliminaCorso(object.getNome(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel elimina "+ caught);
                    }
                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("Corso eliminato");
                        try {
                            form__corsi();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        tabella__corsi.addColumn(colonna__elimina, "");
        colonna__elimina.setCellStyleNames("eliminaCorso__btn");



        tabella__corsi.setRowCount(result.length, true);
        tabella__corsi.setRowData(0, Arrays.asList(result));
        return tabella__corsi;
    }

    private CellTable<Esame> tabella__esami(Esame[] result, String msg) {

        CellTable<Esame> tabella__esami = new CellTable<>();
        tabella__esami.addStyleName("tabella__esami");
        tabella__esami.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabella__esami.setEmptyTableWidget(new Label(msg));

        TextColumn<Esame> colonna__corso = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getNomeCorso();
            }
        };
        tabella__esami.addColumn(colonna__corso, "Corso");


        TextColumn<Esame> colonna__data = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {
                return object.getData() + " - " + object.getOra();
            }
        };
        tabella__esami.addColumn(colonna__data, "Data");


        TextColumn<Esame> colonna__durata = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getDurata();
            }
        };
        tabella__esami.addColumn(colonna__durata, "Durata");


        TextColumn<Esame> colonna__aula = new TextColumn<Esame>() {
            @Override
            public String getValue(Esame object) {

                return object.getAula();
            }
        };
        tabella__esami.addColumn(colonna__aula, "Aula");



        ButtonCell cella__valutazioni = new ButtonCell();
        Column<Esame, String> colonna__valutazioni = new Column<Esame, String>(cella__valutazioni) {
            @Override
            public String getValue(Esame object) {
                return "Valutazioni";
            }
        };

        colonna__valutazioni.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                valutazioni(object);
            }
        });

        tabella__esami.addColumn(colonna__valutazioni, "");
        colonna__valutazioni.setCellStyleNames("valutazioni__btn");


        ButtonCell cella__modifica = new ButtonCell();
        Column<Esame, String> colonna__modifica = new Column<Esame, String>(cella__modifica) {
            @Override
            public String getValue(Esame object) {
                return "Modifica";
            }
        };

        tabella__esami.addColumn(colonna__modifica, "");
        colonna__modifica.setCellStyleNames("modificaEsame__btn");

        colonna__modifica.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                try{
                    formModificaEsame(object.getCodEsame(), object.getNomeCorso(), object.getData(), object.getOra(), object.getAula(), object.getDurata());

                }catch (ParseException e){
                    System.out.println("ERRORE:  " + e);
                    throw new RuntimeException(e);
                }
            }
        });


        /*
        colonna__modifica.setFieldUpdater(new FieldUpdater<Corso, String>() {
            @Override
            public void update(Corso object) {
                user__container.clear();
                user__container.add(new HTML("<div class=\"user__title\">Modifica corso</div>"));
                user__container.add((new form__modificaCorso(docente, object)).getForm());
            }
        }); */

        ButtonCell cella__elimina = new ButtonCell();
        Column<Esame, String> colonna__elimina = new Column<Esame, String>(cella__elimina) {
            @Override
            public String getValue(Esame object) {
                return "Elimina";
            }
        };

        //aggiungo il metodo eliminaEsame() al bottone elimina
        colonna__elimina.setFieldUpdater(new FieldUpdater<Esame, String>() {
            @Override
            public void update(int index, Esame object, String value) {
                serviceEsame.eliminaEsame(object.getCodEsame(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel elimina "+ caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        try {
                            //elimino tutti i sostiene relativi a questo esame
                            serviceSostiene.eliminaEsameSostiene(object.getCodEsame(), new AsyncCallback<Boolean>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert("Errore nel caricare Sostiene "+ caught);

                                }

                                @Override
                                public void onSuccess(Boolean result) {
                                    try {
                                        serviceCorso.getCorso(object.getNomeCorso(), new AsyncCallback<Corso>() {
                                            @Override
                                            public void onFailure(Throwable caught) {
                                                Window.alert("Errore nel caricare i dati del corso "+ caught);
                                            }

                                            @Override
                                            public void onSuccess(Corso result) {
                                                serviceCorso.modificaCorso(object.getNomeCorso(), object.getNomeCorso(), result.getDataInizio(), result.getDataFine(), result.getDescrizione(), result.getCoDocente(), result.getDocente(), -1, new AsyncCallback<Boolean>() {
                                                    @Override
                                                    public void onFailure(Throwable caught) {
                                                        Window.alert("Errore nel modificare il corso "+caught);
                                                    }

                                                    @Override
                                                    public void onSuccess(Boolean result) {
                                                        Window.alert("Esame eliminato");
                                                        try {
                                                            form__esami();
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

        tabella__esami.addColumn(colonna__elimina, "");
        colonna__elimina.setCellStyleNames("eliminaEsame__btn");

        tabella__esami.setRowCount(result.length, true);
        tabella__esami.setRowData(0, Arrays.asList(result));
        return tabella__esami;
    }


    //metodo per modificare un esame
    public void formModificaEsame(int codEsame, String nomeCorso, String data, String ora, String aula, String durata) throws  ParseException{
        user__container.clear();
        FormPanel modificaEsame= new FormPanel();
        modificaEsame.setAction("/modificaEsame");
        modificaEsame.setMethod(FormPanel.METHOD_POST);
        VerticalPanel modificaEsameContainer= new VerticalPanel();
        modificaEsame.addStyleName("form__crea-modifica");

        final Label nome__label= new Label("Corso: ");
        modificaEsameContainer.add(nome__label);

        final ListBox corso__list = new ListBox();
        serviceCorso.getCorsiDocente(docente.getCodDocente(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare i corsi del docente "+ caught);
            }

            @Override
            public void onSuccess(Corso[] result) {
                for (int i = 0; i < result.length; i++) {
                    //devo prendere solo i corsi del docente che non hanno già un esame associato
                    if(result[i].getEsame()==-1) {
                        corso__list.addItem(result[i].getNome());
                    }
                }
            }
        });
        modificaEsameContainer.add(corso__list);

        final Label data__label = new Label("Data: ");
        modificaEsameContainer.add(data__label);
        final DateBox data__dateBox = new DateBox();
        data__dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        data__dateBox.setValue(StringToDate(data));
        modificaEsameContainer.add(data__dateBox);

        final Label ora__label = new Label("Ora: ");
        modificaEsameContainer.add(ora__label);
        final ListBox ora__listBox = new ListBox();
        for(int i=9; i<=15; i++){
            ora__listBox.addItem((String.valueOf(i)));
        }
        modificaEsameContainer.add(ora__listBox);

        final Label durata__label = new Label("Durata: ");
        modificaEsameContainer.add(durata__label);
        final ListBox durata__listBox = new ListBox();
        for(int i=1; i<=4; i++){
            durata__listBox.addItem((String.valueOf(i)));
        }
        modificaEsameContainer.add(durata__listBox);

        final Label aula__label = new Label("Aula: ");
        modificaEsameContainer.add(aula__label);
        final TextBox aula__textBox = new TextBox();
        modificaEsameContainer.add(aula__textBox);

        final Button crea__btn = new Button("Aggiorna");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                modificaEsame.submit();
            }
        });

        modificaEsame.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                //controllo che tutti i campi siano pieni
                if(aula__textBox.getText().length()==0 || data__dateBox.getValue() == null  || ora__listBox.getSelectedValue()== null
                || durata__listBox.getSelectedValue()== null || corso__list.getSelectedValue()==null){
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
                //controllo che l'esame sia dopo la fine del corso
                serviceCorso.getCorso(corso__list.getSelectedValue(), new AsyncCallback<Corso>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel caricare i dati dell'esame " + caught);

                    }

                    @Override
                    public void onSuccess(Corso result) {
                        if (data__dateBox.getValue().before(StringToDate(result.dataFine))) {
                            Window.alert("l'esame deve essere dopo la fine del corso: " + result.getDataFine());
                            event.cancel();
                        }
                    }
                });
            }
        });

        modificaEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                serviceEsame.modificaEsame(codEsame, corso__list.getSelectedValue(), data__dateBox.getValue().toString(), ora__listBox.getSelectedValue(), durata__listBox.getSelectedValue(), aula__textBox.getText(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel modifcare l'esame "+ caught);

                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Window.alert("esame aggiornato");
                        try {
                            form__corsi();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        modificaEsameContainer.add(crea__btn);
        modificaEsame.add(modificaEsameContainer);
        user__container.add(new HTML("<div class=\"user__title\">Modifica l'esame</div>"));
        user__container.add(modificaEsame);
    }

    public void formCreaCorso() {
        user__container.clear();
        FormPanel creaCorso = new FormPanel();
        creaCorso.setAction("/creanuovoCorso");
        creaCorso.setMethod(FormPanel.METHOD_POST);
        creaCorso.addStyleName("form__crea-modifica");
        VerticalPanel corsoContainer = new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        corsoContainer.add(nome__label);
        final TextBox nome__textBox = new TextBox();
        corsoContainer.add(nome__textBox);
        final Label dataI__label = new Label("Data inzio corso: ");
        corsoContainer.add(dataI__label);
        final DateBox dataI__dateBox = new DateBox();
        dataI__dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        corsoContainer.add(dataI__dateBox);
        final Label dataF__label = new Label("Data fine corso: ");
        corsoContainer.add(dataF__label);
        final DateBox dataF__dataBox = new DateBox();
        dataF__dataBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        corsoContainer.add(dataF__dataBox);
        final Label descrizione__label = new Label("Descrizione: ");
        corsoContainer.add(descrizione__label);
        final TextArea descrizione__text = new TextArea();
        corsoContainer.add(descrizione__text);
        final Label codocente__label = new Label("Codocente: ");
        corsoContainer.add(codocente__label);
        final ListBox codocente__list = new ListBox();
        codocente__list.addItem("Nessun codocente");
        serviceDocente.getDocenti(new AsyncCallback<Docente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore in getDocentiService" + caught);
            }

            @Override
            public void onSuccess(Docente[] result) {
                for (int i = 0; i < result.length; i++) {
                    if (result[i].getCodDocente() != docente.getCodDocente()) {
                        codocente__list.addItem(result[i].codDocente + ": " + result[i].getNome() + " " + result[i].getCognome());
                    }
                }
            }
        });

        corsoContainer.add(codocente__list);

        final Button crea__btn = new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaCorso.submit();
            }
        });

        creaCorso.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                //Controllo su che tutti i campi siano pieni

                if (nome__textBox.getText().length() == 0 || descrizione__text.getText().length() == 0 || dataF__dataBox.getValue() == null || dataI__dateBox.getValue() == null ||
                        descrizione__text.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }

                //Controllo sulla data di fine e inizio
                if(dataI__dateBox.getValue().after(dataF__dataBox.getValue())) {
                    if (!(nome__textBox.getText().contains("viaggi nel tempo"))) {
                        //Window.alert("T1: " + dataI__dateBox.getValue().after(dataF__dataBox.getValue()) + "\nT2: " + !(nome__textBox.getText().contains("viaggi nel tempo")));
                        Window.alert("Data di fine corso non può essere prima di data inizio corso, a meno che non sia un" +
                            " corso sul come viaggiare nel tempo");
                        event.cancel();
                    }
                }
            }
        });

        creaCorso.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                int codocente;
                if(codocente__list.getSelectedValue().equals("Nessun codocente")){
                    codocente = -1;
                } else {
                    codocente = Integer.parseInt(codocente__list.getSelectedValue().split(":")[0]);
                }
                serviceCorso.creaCorso(nome__textBox.getText(), dataI__dateBox.getValue().toString(), dataF__dataBox.getValue().toString(),
                descrizione__text.getText(), codocente, docente.getCodDocente(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare il corso "+ caught);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        if(result==true){
                            Window.alert("corso creato");
                            try {
                                form__corsi();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Window.alert("Impossibile creare il corso: nome \""+nome__textBox.getText()+"\"  già esistente");
                        }
                    }
                });
            }
        });

        corsoContainer.add(crea__btn);

        creaCorso.add(corsoContainer);
        user__container.add(new HTML("<div class=\"user__title\">Crea un nuovo corso</div>"));
        user__container.add(creaCorso);
    }

    public void formModificaCorso(String nome, String dataInizio, String dataFine, String descrizione, int codocente, int esame) throws ParseException {
        user__container.clear();
        FormPanel modificaCorso = new FormPanel();
        modificaCorso.setAction("/modificaCorso");
        modificaCorso.setMethod(FormPanel.METHOD_POST);
        modificaCorso.addStyleName("form__crea-modifica");
        VerticalPanel modificaCorsoContainer = new VerticalPanel();
        final Label nome__label = new Label("Nome: ");
        modificaCorsoContainer.add(nome__label);

        final TextBox nome__textBox = new TextBox();
        nome__textBox.setText(nome);
        modificaCorsoContainer.add(nome__textBox);

        final Label dataI__label = new Label("Data inzio corso: ");
        modificaCorsoContainer.add(dataI__label);

        final DateBox dataI__dateBox = new DateBox();
        dataI__dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        dataI__dateBox.setValue(StringToDate(dataInizio));
        modificaCorsoContainer.add(dataI__dateBox);

        final Label dataF__label = new Label("Data fine corso: ");
        modificaCorsoContainer.add(dataF__label);

        final DateBox dataF__dataBox = new DateBox();
        dataF__dataBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        dataF__dataBox.setValue(StringToDate(dataFine));
        modificaCorsoContainer.add(dataF__dataBox);

        final Label descrizione__label = new Label("Descrizione: ");
        modificaCorsoContainer.add(descrizione__label);

        final TextArea descrizione__text = new TextArea();
        descrizione__text.setText(descrizione);
        modificaCorsoContainer.add(descrizione__text);

        final Label codocente__label = new Label("Codocente: " + codocente);
        modificaCorsoContainer.add(codocente__label);

        final ListBox codocente__list = new ListBox();
        codocente__list.addItem("Nessun codocente");

        serviceDocente.getDocenti(new AsyncCallback<Docente[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore in getDocentiService" + caught);
            }

            @Override
            public void onSuccess(Docente[] result) {
                for (int i = 0; i < result.length; i++) {
                    if (result[i].getCodDocente() != docente.getCodDocente()) {
                        codocente__list.addItem(result[i].codDocente + ": " + result[i].getNome() + " " + result[i].getCognome());
                        if(result[i].getCodDocente() == codocente){
                            codocente__list.setItemSelected(i+1, true);
                        }
                    }
                }
                //codocente__list.setValue(codocente, "pp" /*result[i].codDocente + ": " + result[i].getNome() + " " + result[i].getCognome()*/);
            }
        });



        modificaCorsoContainer.add(codocente__list);

        final Button crea__btn = new Button("Aggiorna");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                modificaCorso.submit();
            }
        });

        modificaCorso.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                //Controllo su che tutti i campi siano pieni
                if (nome__textBox.getText().length() == 0 || descrizione__text.getText().length() == 0 || dataF__dataBox.getValue() == null || dataI__dateBox.getValue() == null ||
                        descrizione__text.getText().length() == 0) {
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }

                //Controllo sulla data di fine e inizio
                if(dataI__dateBox.getValue().after(dataF__dataBox.getValue())) {
                    if (!(nome__textBox.getText().contains("viaggi nel tempo"))) {
                        //Window.alert("T1: " + dataI__dateBox.getValue().after(dataF__picker.getValue()) + "\nT2: " + !(nome__textBox.getText().contains("viaggi nel tempo")));
                        Window.alert("Data di fine corso non può essere prima di data inizio corso, a meno che non sia un" +
                                " corso sul come viaggiare nel tempo");
                        event.cancel();
                    }
                }
            }
        });

        modificaCorso.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                int codocente;
                if(codocente__list.getSelectedValue().equals("Nessun codocente")){
                    codocente = -1;
                } else {
                    codocente = Integer.parseInt(codocente__list.getSelectedValue().split(":")[0]);
                }
                serviceCorso.modificaCorso(nome, nome__textBox.getText(), dataI__dateBox.getValue().toString(), dataF__dataBox.getValue().toString(),
                        descrizione__text.getText(), codocente, docente.getCodDocente(), esame, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Errore nel creare il corso "+ caught);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                Window.alert("corso aggiornato");
                                try {
                                    form__corsi();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
            }
        });

        modificaCorsoContainer.add(crea__btn);

        modificaCorso.add(modificaCorsoContainer);
        user__container.add(new HTML("<div class=\"user__title\">Modifica il corso</div>"));
        user__container.add(modificaCorso);
    }

    public void formCreaEsame(){
        user__container.clear();
        FormPanel creaEsame = new FormPanel();
        creaEsame.setAction("/creanuovoEsame");
        creaEsame.setMethod(FormPanel.METHOD_POST);
        creaEsame.addStyleName("form__crea-modifica");
        VerticalPanel esameContainer = new VerticalPanel();
        final Label aula__label = new Label("Aula: ");
        esameContainer.add(aula__label);
        final TextBox aula__textBox = new TextBox();
        esameContainer.add(aula__textBox);
        final Label data__label = new Label("Data: ");
        esameContainer.add(data__label);
        final DateBox data__dataBox = new DateBox();
        data__dataBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        esameContainer.add(data__dataBox);
        final Label ora__label = new Label("Ora: ");
        esameContainer.add(ora__label);
        final ListBox ora__listBox = new ListBox();
        for(int i=9; i<=15; i++){
            ora__listBox.addItem((String.valueOf(i)));
        }
        esameContainer.add(ora__listBox);
        final Label durata__label = new Label("Durata: ");
        esameContainer.add(durata__label);
        final TextBox durata__textBox = new TextBox();
        esameContainer.add(durata__textBox);
        final Label corso__label = new Label("Corso: ");
        esameContainer.add(corso__label);
        final ListBox corso__list = new ListBox();
        serviceCorso.getCorsiDocente(docente.getCodDocente(), new AsyncCallback<Corso[]>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Errore nel caricare i corsi del docente "+ caught);
            }

            @Override
            public void onSuccess(Corso[] result) {
                for (int i = 0; i < result.length; i++) {
                    //devo prendere solo i corsi del docente che non hanno già un esame associato
                    if(result[i].getEsame()==-1) {
                        corso__list.addItem(result[i].getNome());
                    }
                }
            }
        });

        esameContainer.add(corso__list);
        final Button crea__btn= new Button("Crea");
        crea__btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                creaEsame.submit();
            }
        });

        creaEsame.addSubmitHandler(new FormPanel.SubmitHandler() {
            @Override
            public void onSubmit(FormPanel.SubmitEvent event) {
                //Controllo che tutti i campi siano pieni
                if (aula__textBox.getText().length() == 0 || durata__textBox.getText().length() == 0 || data__dataBox.getValue() == null ||
                        corso__list.getSelectedValue() == null || ora__listBox.getSelectedValue()== null) {
                    Window.alert("Compilare tutti i campi!");
                    event.cancel();
                }
                //controllo che l'esame sia dopo la fine del corso
                serviceCorso.getCorso(corso__list.getSelectedValue(), new AsyncCallback<Corso>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel caricare i dati dell'esame " + caught);

                    }

                    @Override
                    public void onSuccess(Corso result) {
                        if (data__dataBox.getValue().before(StringToDate(result.dataFine))) {
                            Window.alert("l'esame deve essere dopo la fine del corso: " + result.getDataFine());
                            event.cancel();
                        }
                    }
                });
            }
        });

        creaEsame.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                serviceEsame.creaEsame(corso__list.getSelectedValue(), data__dataBox.getValue().toString(),ora__listBox.getSelectedValue(), durata__textBox.getText(), aula__textBox.getText(), new AsyncCallback<Integer>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel creare l'esame "+caught);
                    }

                    @Override
                    public void onSuccess(Integer codEsame) {
                        try {
                            //aggiungo l'esame creato anche nella tabella dei corsi al corso a cui è stato associato
                            serviceCorso.getCorso(corso__list.getSelectedValue(), new AsyncCallback<Corso>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert("Errore nel caricare i dati dell'esame "+ caught);
                                }
                                @Override
                                public void onSuccess(Corso result) {
                                    serviceCorso.modificaCorso(result.getNome(), result.getNome(), result.getDataInizio(), result.getDataFine().toString(), result.getDescrizione(), result.getCoDocente(), result.getDocente(), codEsame, new AsyncCallback<Boolean>() {
                                        @Override
                                        public void onFailure(Throwable caught) {
                                            Window.alert("Errore nel modificare il corso "+caught);
                                        }

                                        @Override
                                        public void onSuccess(Boolean result) {
                                            Window.alert("Esame creato");
                                            try {
                                                form__esami();
                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    });
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        esameContainer.add(crea__btn);
        creaEsame.add(esameContainer);
        user__container.add(new HTML("<div class=\"user__title\">Crea un nuovo esame</div>"));
        user__container.add(creaEsame);
    }

    public void valutazioni(Esame esame){
        user__container.clear();
        serviceSostiene.getStudentiInserisciVoto(esame.getCodEsame(), new AsyncCallback<Sostiene[]>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Sostiene[] result) {
                Button btnIndietro= new Button("Back");
                btnIndietro.addStyleName("creaCorso__btn");
                btnIndietro.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        try {
                            form__esami();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                user__container.add(new HTML("<div class=\"user__title\">Valutazioni</div>"));
                user__container.add(btnIndietro);

                CellTable<Sostiene> tabella__sostiene = tabella__esamiSostenuti(result, "Nessun studente iscritto all'esame");

                user__container.add(tabella__sostiene);
            }
        });
    }

    private CellTable<Sostiene> tabella__esamiSostenuti(Sostiene[] result, String msg){
        CellTable<Sostiene> tabellaSostiene = new CellTable<>();
        tabellaSostiene.addStyleName("tabella__corsi");
        tabellaSostiene.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        tabellaSostiene.setEmptyTableWidget(new Label(msg));

        //Colonna per mostrare la matricola
        TextColumn<Sostiene> colonna__matricola = new TextColumn<Sostiene>() {
            @Override
            public String getValue(Sostiene object) {
                return "" + object.getMatricola();
            }
        };
        tabellaSostiene.addColumn(colonna__matricola, "Matricola");

        //TextInput per aggiungere voto
        TextInputCell inputVoto = new TextInputCell();

        Column<Sostiene, String> colonna__aggiungiVoto = new Column<Sostiene, String>(inputVoto) {
            @Override
            public String getValue(Sostiene object) {
                return null;
            }
        };

        colonna__aggiungiVoto.setFieldUpdater(new FieldUpdater<Sostiene, String>() {
            @Override
            public void update(int index, Sostiene object, String value) {
                try{
                    object.voto = Integer.parseInt(value);
                } catch(Exception NumberFormatException){
                    Window.alert("NO: "+ value + " non è un voto vero");
                }
            }
        });

        tabellaSostiene.addColumn(colonna__aggiungiVoto, "Aggiungi voto");

        //Tasto per mandare il voto alla segreteria
        ButtonCell cella__mandaVoto = new ButtonCell();
        Column<Sostiene, String> colonna__mandaVoto = new Column<Sostiene, String>(cella__mandaVoto) {
            @Override
            public String getValue(Sostiene object) {
                return "Manda";
            }
        };

        colonna__mandaVoto.setFieldUpdater(new FieldUpdater<Sostiene, String>() {
            @Override
            public void update(int index, Sostiene object, String value) {
                try{
                    //if(colonna__aggiungiVoto.getValue())
                    if((object.voto<31 && object.voto>-1) || object.voto==32){
                        int voto = object.voto;
                        serviceSostiene.inserisciVoto(object.codEsame, object.matricola, object.voto, new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Errore");
                            }
                            @Override
                            public void onSuccess(Boolean result) {
                                Window.alert("Voto inserito con successo");
                                serviceEsame.getEsame(object.codEsame, new AsyncCallback<Esame>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("KEK");
                                    }
                                    @Override
                                    public void onSuccess(Esame result) {
                                        valutazioni(result);
                                    }
                                });
                            }
                        });
                    } else {
                        Window.alert("Il voto deve essere compreso tra 0 e 30 oppure 32 che è 30 e lode");
                    }
                } catch(Exception NumberFormatException){
                    Window.alert("NO: "+ value + " non è un voto vero");
                }
            }
        });

        tabellaSostiene.addColumn(colonna__mandaVoto, "Manda voto");

        tabellaSostiene.setRowCount(result.length);
        tabellaSostiene.setRowData(0, Arrays.asList(result));
        return tabellaSostiene;
    }


    public Date StringToDate(String data){
        String[] amg = data.split(" ");
        int mese;
        //Window.alert(amg[2]);
        switch(amg[1]){
            case "Jan": mese = 0; break;
            case "Feb": mese = 1; break;
            case "Mar": mese = 2; break;
            case "Apr": mese = 3; break;
            case "May": mese = 4; break;
            case "Jun": mese = 5; break;
            case "Jul": mese = 6; break;
            case "Aug": mese = 7; break;
            case "Sep": mese = 8; break;
            case "Oct": mese = 9; break;
            case "Nov": mese = 10; break;
            default: mese = 11; break;
        }
        return new Date(Integer.parseInt(amg[2])-1900, mese, Integer.parseInt(amg[0]));
    }
}