package it.tylconsulting.vega.vegabackend.views.main;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import it.tylconsulting.vega.vegabackend.views.aziende.AziendeView;
import it.tylconsulting.vega.vegabackend.views.gruppoaziende.GruppoAziendeView;
import it.tylconsulting.vega.vegabackend.views.questeditor.QuestEditorView;

@Route(value="")
public class MainLayout extends AppLayout {

    Accordion accordion=new Accordion();
    AccordionPanel accordionPanelAssessment=new AccordionPanel();

    AccordionPanel accordionPanelAutovalutazioni=new AccordionPanel();
    AccordionPanel accordionPanelAssessors=new AccordionPanel();
    AccordionPanel accordionPanelAmministrazione=new AccordionPanel();

    public MainLayout() {
        createHeader();
        createMainMenuPanel();
    }

    private void createHeader() {
        H1 logo = new H1("Vega Backend");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createMainMenuPanel(){
        RouterLink gruppoaziendeLink = new RouterLink("Gruppi Aziende", GruppoAziendeView.class);
        gruppoaziendeLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink aziendeLink = new RouterLink("Aziende", AziendeView.class);
        aziendeLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink questeditorLink = new RouterLink("QuestEditor", QuestEditorView.class);
        questeditorLink.setHighlightCondition(HighlightConditions.sameLocation());


        AccordionPanel accordionPanelDestinatari=new AccordionPanel("Aziende e Destinatari",
                new VerticalLayout(gruppoaziendeLink,aziendeLink, questeditorLink));
        accordion.addClassName("accordionMenu");
        accordionPanelDestinatari.addThemeVariants(DetailsVariant.FILLED);
        accordion.add(accordionPanelDestinatari);

        addToDrawer(accordion);
    }
}
