package it.tylconsulting.vega.vegabackend.views.aziende;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.tylconsulting.vega.vegabackend.views.main.MainLayout;

@PageTitle("Gruppo Aziende | Vega Backend")
@Route(value = "aziende", layout= MainLayout.class)
public class AziendeView extends VerticalLayout {
}
