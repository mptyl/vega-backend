package it.tylconsulting.vega.vegabackend.views.questeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.tylconsulting.vega.vegabackend.views.main.MainLayout;

@Route(value="questeditor", layout= MainLayout.class)
@PageTitle("Quest Editor | Vega Backend")
public class QuestEditorView extends HorizontalLayout {

    QuestEditorPropertiesForm propertiesForm;

    public QuestEditorView(){
        setSizeFull();
        setupTreeGrid();
        setupShowSpace();
        setupPropertiesPanel();
    }

    public Component setupTreeGrid(){
        TreeGrid treeGrid= new TreeGrid<>();
        return treeGrid;

    }

    public Component setupShowSpace(){
        VerticalLayout showSpace= new VerticalLayout();
        return showSpace;
    }

    public Component setupPropertiesPanel(){
        propertiesForm = new QuestEditorPropertiesForm();
        propertiesForm.setWidth("45em");
        return  propertiesForm;
    }
}
