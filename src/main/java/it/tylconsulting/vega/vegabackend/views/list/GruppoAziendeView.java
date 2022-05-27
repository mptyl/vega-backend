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
import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;

import java.util.Collections;

@PageTitle("Gruppo Aziende | Vega Backend")
@Route(value = "")
public class GruppoAziendeView extends VerticalLayout {
    Grid<GruppoAziende> grid = new Grid<>(GruppoAziende.class);
    TextField filterText=new TextField();
    
    GruppoAziendeForm form;
    
    public GruppoAziendeView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
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
        form = new GruppoAziendeForm(Collections.emptyList());
        form.setWidth("45em");
    }

    private void configureGrid() {
        grid.addClassNames("gruppoaziende-grid");
        grid.setSizeFull();
        grid.setColumns("id", "nomeGruppo", "descrizioneGruppo");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }


    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtra tramite nome...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Aggiungi gruppo aziende");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
