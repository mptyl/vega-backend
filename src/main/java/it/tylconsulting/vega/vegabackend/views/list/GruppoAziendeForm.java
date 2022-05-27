package it.tylconsulting.vega.vegabackend.views.list;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;

import java.util.List;

public class GruppoAziendeForm extends FormLayout {
    TextField nomeGruppo = new TextField("Nome Gruppo");
    TextField descrizioneGruppo = new TextField("Descrizione Gruppo");
    Button save = new Button("Salva");
    Button delete = new Button("Cancella");
    Button cancel = new Button("Esci senza salvare");

    public GruppoAziendeForm(List<GruppoAziende> gruppiAziende){
        addClassName("gruppoAziende-form");

        add(nomeGruppo,descrizioneGruppo, creeateButtonLayout());

    }

    private HorizontalLayout creeateButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }
}
