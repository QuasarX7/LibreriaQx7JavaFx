package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Gestore finestra di conferma
 *
 * @author Dott. Domenico della Peruta
 */
public class ConfermaController implements Initializable {
    
    public interface Codice{
        public void esegui();
    }
    
    
    private static Codice azione;
    private static String testo;
    
    @FXML
    private TextArea domanda;

    @FXML
    private Button pulsanteChiusura;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Finestra.infoFinestreAperte(pulsanteChiusura);
        if(testo != null)
            domanda.setText(testo);
        else
            domanda.setText("Conferma l'azione?");
    }    
    
    /**
     * Metodo sta
     * @param testoDomanda
     * @param codice 
     */
    public static void conferma(String testoDomanda,Codice codice){
        azione = codice;
        testo = testoDomanda;
    } 
    
    @FXML
    private void chiudiSenzaSalvare(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            chiudi();
        }
    }

    @FXML
    private void chiudiSalva(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
        	if(azione instanceof Codice)
        		azione.esegui();
            chiudi();
            azione = null;
        }
    }
    
    private void chiudi(){
    	Finestra.ricaricaFinestra(this);
    }
    
    
}
