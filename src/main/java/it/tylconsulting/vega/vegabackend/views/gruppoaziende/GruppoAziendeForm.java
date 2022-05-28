package it.tylconsulting.vega.vegabackend.views.gruppoaziende;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import it.tylconsulting.vega.vegamodel.db.model.GruppoAziende;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.InputStream;

public class GruppoAziendeForm extends FormLayout {
    private GruppoAziende gruppoAziende;
    TextField nomeGruppo = new TextField("Nome Gruppo");
    TextField descrizioneGruppo = new TextField("Descrizione Gruppo");
    Button save = new Button("Salva");
    Button delete = new Button("Cancella");
    Button cancel = new Button("Esci senza salvare");
    MemoryBuffer memoryBuffer= new MemoryBuffer();
    Upload singleFileUpload = new Upload(memoryBuffer);
    Image logo = new Image();
    Binder<GruppoAziende> binder = new BeanValidationBinder<>(GruppoAziende.class);

    /**
     * Setup base della form
     */
    public GruppoAziendeForm(){
        addClassName("gruppoAziende-form");
        binder.bindInstanceFields(this);
        singleFileUpload.addClassName("singleFileUpload");
        singleFileUpload.addSucceededListener(event ->{
            try {
                InputStream fileData = memoryBuffer.getInputStream();
                gruppoAziende.setLogoGruppo(Base64Utils.encodeToString(fileData.readAllBytes()));
            } catch (IOException ioe){
                // TODO da sviluppare
            }
        });
        add(logo, nomeGruppo,descrizioneGruppo, singleFileUpload, creeateButtonsLayout());
    }

    public void setupLogo(){
        String b64NullGif="R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
        String mimeType="image/png";
        String logoString=(gruppoAziende.getLogoGruppo()== null || gruppoAziende.getLogoGruppo().isEmpty()) ?
                b64NullGif :
                gruppoAziende.getLogoGruppo();
        logo.setSrc("data:"+mimeType+";base64," + logoString);
        logo.setMaxWidth(150, Unit.PIXELS);
    }

    /**
     * Setup della bottoniera con assegnazione di stile, shortcut e clickListener
     * @return
     */
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

    /**
     * set dell'entity e associazione al binder
     * @param gruppoAziende
     */
    public void setGruppoAziende(GruppoAziende gruppoAziende){
        this.gruppoAziende = gruppoAziende;
        binder.readBean(gruppoAziende);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(gruppoAziende);
            fireEvent(new SaveEvent(this, gruppoAziende));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //region Definizione eventi legati ai buttons

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
    //endregion
}
