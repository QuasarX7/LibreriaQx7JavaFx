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
 * FXML Controller class
 *
 * @author Ninja
 */
public class AvvisoController implements Initializable {

    private static String testo;
    
    @FXML
    private TextArea messaggio;
    
    @FXML
    private Button pulsante;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Finestra.infoFinestreAperte(pulsante);
        if(testo != null)
            messaggio.setText(testo);
        else
            messaggio.setText("?");
    }    
    
    public static void avviso(String testo) {
        AvvisoController.testo = testo;
    }

    
    @FXML
    void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            chiudi();
        }
    }

    
    
    private void chiudi(){
        Finestra.ricaricaFinestra(this);
    }
    
     
    
}
