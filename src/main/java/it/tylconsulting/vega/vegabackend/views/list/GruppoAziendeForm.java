package it.tylconsulting.vega.vegabackend.views.list;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;

public class GruppoAziendeForm extends FormLayout {
    private GruppoAziende gruppoAziende;
    TextField nomeGruppo = new TextField("Nome Gruppo");
    TextField descrizioneGruppo = new TextField("Descrizione Gruppo");
    Button save = new Button("Salva");
    Button delete = new Button("Cancella");
    Button cancel = new Button("Esci senza salvare");

    Binder<GruppoAziende> binder = new BeanValidationBinder<>(GruppoAziende.class);

    public GruppoAziendeForm(){
        addClassName("gruppoAziende-form");
        binder.bindInstanceFields(this);
        add(nomeGruppo,descrizioneGruppo, creeateButtonsLayout());

    }

    private HorizontalLayout creeateButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener((event -> fireEvent(new DeleteEvent(this, gruppoAziende))));
        cancel.addClickListener(event -> fireEvent((new CloseEvent(this))));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(gruppoAziende);
            fireEvent(new SaveEvent(this, gruppoAziende));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setGruppoAziende(GruppoAziende gruppoAziende){
        this.gruppoAziende = gruppoAziende;
        binder.readBean(gruppoAziende);
    }

    public static abstract class GruppoAziendeFormEvent extends ComponentEvent<GruppoAziendeForm> {
        private GruppoAziende gruppo;

        protected GruppoAziendeFormEvent(GruppoAziendeForm source, GruppoAziende gruppo) {
            super(source, false);
            this.gruppo = gruppo;
        }

        public GruppoAziende getGruppo() {
            return gruppo;
        }
    }

    public static class SaveEvent extends GruppoAziendeFormEvent {
        SaveEvent(GruppoAziendeForm source, GruppoAziende gruppo) {
            super(source, gruppo);
        }
    }

    public static class DeleteEvent extends GruppoAziendeFormEvent {
        DeleteEvent(GruppoAziendeForm source, GruppoAziende gruppo) {
            super(source, gruppo);
        }

    }

    public static class CloseEvent extends GruppoAziendeFormEvent {
        CloseEvent(GruppoAziendeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
