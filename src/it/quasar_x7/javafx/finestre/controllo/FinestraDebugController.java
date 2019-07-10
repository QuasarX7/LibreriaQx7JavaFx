package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.finestre.modello.Traccia;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Dr Domenico della Peruta
 */
public class FinestraDebugController implements Initializable {

    public static java.lang.Exception errore = null;
    
    
    @FXML
    private TextArea messaggio;

    
    @FXML
    private Accordion schede;
    
    @FXML
    private TableColumn<Traccia, String> colonnaMetodo;

    @FXML
    private TableColumn<Traccia, Integer> colonnaRiga;

    @FXML
    private TableColumn<Traccia, String> colonnaFile;

    @FXML
    private TableView<Traccia> tabella;


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(errore != null){
            schede.setExpandedPane(schede.getPanes().get(0));
            messaggio.setText(errore.getMessage());
            
            colonnaRiga.setCellValueFactory(new PropertyValueFactory<>("riga"));
            colonnaFile.setCellValueFactory(new PropertyValueFactory<>("file"));
            colonnaMetodo.setCellValueFactory(new PropertyValueFactory<>("metodo"));
        
        
            StackTraceElement[] lista = errore.getStackTrace();
            if(lista != null){
                for(StackTraceElement traccia:lista){
                    if(traccia != null)
                        tabella.getItems().add(
                                new Traccia(
                                        traccia.getLineNumber(),
                                        traccia.getFileName(),
                                        traccia.getMethodName()
                                )
                        );
                }
            }
        }
    }    
    
    
    @FXML
    void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            chiudi();
        }
    }

    
    
    private void chiudi(){
    	Finestra.ricaricaFinestra(this);
        errore = null;
    }
    
}
