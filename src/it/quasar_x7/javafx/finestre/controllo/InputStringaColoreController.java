package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.finestre.modello.VoceListaColore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;


/**
 * 
 *
 * @author Dr. Domenico della Peruta
 */
public class InputStringaColoreController extends InputController {

    @FXML
    private ColorPicker menuColore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        if(suggerimento != null){
            Object[] info = VoceListaColore.estraiInfo(suggerimento);
            input.setText((String) info[1]);
            menuColore.setValue((Color)info[0]);
        }else
            menuColore.setValue(Color.BLACK);
        
    }    
    
    /**
     * Aggiorna il testo in input quando viene selezionato il colore dal 'menuColore'.
     * 
     * @param event 
     */
    @FXML
    private void aggiornaInput(ActionEvent event) {
        if(event.getEventType().equals(ActionEvent.ACTION)){
            this.input.setStyle(
                    String.format(
                            "-fx-text-inner-color: %s;",
                            VoceListaColore.coloreWeb(menuColore.getValue())
                    )
            );
        }
        
    }
    
    /**
     * Salva l'input tramite il pulsante predefinito 'Ok'.
     * 
     * @param event 
     */
    @FXML
    @Override
    protected void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if(azione.esegui(new VoceListaColore(input.getText(),menuColore.getValue()).toString()))
               chiudi();
            else{
                Finestra.finestraAvviso(this, "Input non valido!");
            }
        }
    }
    
    
}
