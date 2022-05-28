package it.tylconsulting.vega.vegabackend.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.tylconsulting.vega.vegabackend.service.GruppoAziendeService;
import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;

@PageTitle("Gruppo Aziende | Vega Backend")
@Route(value = "")
public class GruppoAziendeView extends VerticalLayout {
    Grid<GruppoAziende> grid = new Grid<>(GruppoAziende.class);
    TextField filterText=new TextField();
    
    GruppoAziendeForm form;

    GruppoAziendeService service;

    /**
     * Setup della View, composta da una Grid, da una Form
     * e da una toolbar generale (non della Form)
     *
     * Il setup comprende il caricamento della Grid e la chiusura della Form
     * che appare solo al click di una riga
     *
     * @param service
     */
    public GruppoAziendeView(GruppoAziendeService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        // configura gli elementi costituenti la view
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        // esegui le azioni di fine setup
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    /**
     * Configurazione della Form
     */
    private void configureForm() {
        form = new GruppoAziendeForm();
        form.setWidth("45em");
        form.addListener(GruppoAziendeForm.SaveEvent.class, this::saveGruppoAziende);
        form.addListener(GruppoAziendeForm.DeleteEvent.class, this::deleteGruppoAziende);
        form.addListener(GruppoAziendeForm.CloseEvent.class, e -> closeEditor());
    }

    /**
     * Configurazione della Grid
     */
    private void configureGrid() {
        String b64NullGif="R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7";
        grid.addClassNames("gruppoaziende-grid");
        grid.setSizeFull();
        grid.setColumns("id", "nomeGruppo", "descrizioneGruppo");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addColumn(LitRenderer.<GruppoAziende>of("<img style='height: 30px;'                       src='data:image/png;base64,${item.logo}' alt='logo'/>")
                        .withProperty("logo",item->
                                ((item.getLogoGruppo()==null || item.getLogoGruppo().isEmpty())?
                                                b64NullGif : item.getLogoGruppo())))
                .setHeader("Logo Gruppo");

        grid.asSingleSelect().addValueChangeListener(event -> editGruppoAziende(event.getValue()));
    }

    /**
     * Configurazione della toolbar della view
     * @return
     */
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtra tramite nome...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateList());

        Button addGruppoButton = new Button("Aggiungi gruppo aziende");
        addGruppoButton.addClickListener(click -> addGruppoAziende());

        HorizontalLayout toolbar = new HorizontalLayout(filterText,addGruppoButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    //region Actions della View
    private void addGruppoAziende() {
        grid.asSingleSelect().clear();
        editGruppoAziende(new GruppoAziende());
    }

    private void saveGruppoAziende(GruppoAziendeForm.SaveEvent event){
        service.saveGruppoAziende(event.getGruppo());
        form.singleFileUpload.clearFileList();
        updateList();
        closeEditor();
    }
    private void deleteGruppoAziende(GruppoAziendeForm.DeleteEvent event){
        service.deleteGruppoAziende(event.getGruppo());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(service.findAllGruppiziende(filterText.getValue()));
    }

    public void editGruppoAziende(GruppoAziende gruppo) {
        if (gruppo == null) {
            closeEditor();
        } else {
            form.setGruppoAziende(gruppo);
            form.setupLogo();
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setGruppoAziende(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    //endregion
}
