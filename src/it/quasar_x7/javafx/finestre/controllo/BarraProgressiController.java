package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Dr. Domenico della Peruta
 */
public class BarraProgressiController implements Initializable {

    

    public interface  Codice {
        public Task creaTask();
        public void esci();
    }
    
    protected static Codice azione = null;
    
    
    private static String testo;
    
    @FXML
    private ProgressBar barra;

    @FXML
    private TextArea messaggio;

    @FXML
    private ProgressIndicator indicatore;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inizializzaBarra();
        
        if(azione != null){
            Task task = azione.creaTask();
            if(task != null){
                barra.progressProperty().bind(task.progressProperty());
                indicatore.progressProperty().bind(task.progressProperty());

                task.messageProperty().addListener(
                        (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                            messaggio.setText(newValue);
                        }
                );
                
                task.setOnSucceeded(
                        (Event event) -> {
                            azione.esci();
                        }
                );
                new Thread(task).start();
            }
        }else{
            messaggio.setText("?????");
        }
    }
    
    
    private void inizializzaBarra(){
        barra.setProgress(0);
        indicatore.setProgress(0);
        barra.progressProperty().unbind();
        indicatore.progressProperty().unbind();
    }
    
    public static void azione(Codice ok) {
        azione = ok;
    }

    @FXML
    private void chiudiSenzaSalvare(ActionEvent event) {
        if(event.getEventType().equals(ActionEvent.ACTION)){
            chiudi();
        }
    }

    private void chiudi(){
    	Finestra.ricaricaFinestra(this);
    }
    
}
