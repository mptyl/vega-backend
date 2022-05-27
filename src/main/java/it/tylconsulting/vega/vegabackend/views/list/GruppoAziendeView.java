package it.tylconsulting.vega.vegabackend.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
    
    public GruppoAziendeView(GruppoAziendeService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
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

    private void configureForm() {
        form = new GruppoAziendeForm();
        form.setWidth("45em");
        form.addListener(GruppoAziendeForm.SaveEvent.class, this::saveGruppoAziende);
        form.addListener(GruppoAziendeForm.DeleteEvent.class, this::deleteGruppoAziende);
        form.addListener(GruppoAziendeForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveGruppoAziende(GruppoAziendeForm.SaveEvent event){
        service.saveGruppoAziende(event.getGruppo());
        updateList();
        closeEditor();
    }

    private void deleteGruppoAziende(GruppoAziendeForm.DeleteEvent event){
        service.deleteGruppoAziende(event.getGruppo());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("gruppoaziende-grid");
        grid.setSizeFull();
        grid.setColumns("id", "nomeGruppo", "descrizioneGruppo");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editGruppoAziende(event.getValue()));
    }


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

    private void updateList() {
        grid.setItems(service.findAllGruppiziende(filterText.getValue()));
    }

    public void editGruppoAziende(GruppoAziende gruppo) {
        if (gruppo == null) {
            closeEditor();
        } else {
            form.setGruppoAziende(gruppo);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setGruppoAziende(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addGruppoAziende() {
        grid.asSingleSelect().clear();
        editGruppoAziende(new GruppoAziende());
    }

}
