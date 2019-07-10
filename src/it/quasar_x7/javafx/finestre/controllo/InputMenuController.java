package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.CampoTesto;
import it.quasar_x7.javafx.Finestra;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * Gestore della finestra di dialoga a input menu.
 *
 * @author Dott. Domenico della Peruta
 */
public class InputMenuController implements Initializable {

    
    public interface Codice{
        public boolean esegui(String risposta);
    }
    
    private static Codice azione;

    private static String testo;
    
    private static boolean modifica = false;
    
    private static final  ObservableList<String> listaInput = FXCollections.observableArrayList();

    
    @FXML
    private ComboBox<String> input;

    @FXML
    private TextArea domanda;

  
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        input.setItems(listaInput);
        if(testo != null)
            domanda.setText(testo);
        else
            domanda.setText("Seleziona input");
        input.setEditable(modifica);
            
    }  
    
    public static void input(String testoDomanda,String[] risposte, boolean modifica, Codice ok){
        listaInput.clear();
        testo = testoDomanda;
        listaInput.addAll(risposte);
        azione = ok;
        InputMenuController.modifica = modifica;
    } 
    
    @FXML
    private void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if(azione.esegui(input.getValue()))
                chiudi();
            else
                Finestra.finestraAvviso(this, "Input non valido!");
                
        }
    }

    @FXML
    private void chiudiSenzaSalvare(ActionEvent event) {
        chiudi();
    }
    
    private void chiudi(){
    	Finestra.ricaricaFinestra(this);
    }
    
}
