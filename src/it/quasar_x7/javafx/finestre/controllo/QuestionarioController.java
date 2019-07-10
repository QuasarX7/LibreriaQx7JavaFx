package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Dr. Domenico della Peruta
 */
public class QuestionarioController implements Initializable {

    public interface Codice{
        public boolean esegui(ArrayList<String> risposte);
    }
    
    protected static Codice azione;

    protected static ArrayList<String> domande;
    
    protected static boolean modifica = false;
    
    
    @FXML
    protected Label titolo;

    @FXML
    protected Button pulsanteChiusura;

    @FXML
    protected ComboBox<String> menuRisposta;

    @FXML
    protected TextArea domanda;
    
    private static final ObservableList<ObservableList<String>> listaInput = FXCollections.observableArrayList();
    
    protected int indice;
    
    protected static ArrayList<String> risposteDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        indice=0;
        if(listaInput.size() > 0)
            menuRisposta.setItems(listaInput.get(indice));
        
        if(domande != null)
            if(domande.size() > 0)
                domanda.setText(domande.get(indice));
        else
            domanda.setText("Seleziona input");
        
        domanda.setEditable(modifica);
        aggiornaTitolo();
        risposteDate = new ArrayList<>();
        
    }  
    
    private void aggiornaTitolo(){
        titolo.setText(String.format("Domanda [%d]", indice+1));
    }
    
    public static void input(String[] testoDomande,ArrayList<String[]> listaRisposte, boolean modifica, Codice ok){
        listaInput.clear();
        if(testoDomande != null){
            domande = new ArrayList<>();
            for(String testoDomanda : testoDomande){
                if(testoDomanda != null)
                    domande.add(testoDomanda);
            }
        }
        if(listaRisposte != null){
            for(String[] risposte : listaRisposte){
                if(risposte != null){
                    ObservableList<String> input = FXCollections.observableArrayList();
                    input.addAll(risposte);
                    listaInput.add(input);
                }
            }
        }
        azione = ok;
        QuestionarioController.modifica = modifica;
    }

    @FXML
    protected void avanti(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            final int limite = listaInput.size() < domande.size() ? listaInput.size() : domande.size();
            
            // [1] acquisisci dati
            if(indice >= limite){ // FINE
                if(azione.esegui(risposteDate))
                    chiudi();
                else
                    Finestra.finestraAvviso(this, "Input non valido!");
            }else if(indice == 0){ // inizio
                if(risposteDate != null){
                    risposteDate.clear();
                }else{
                    risposteDate = new ArrayList<>();
                }
                risposteDate.add(menuRisposta.getValue());
                
            }else{
                
            }
            indice++;
            
            // [2] aggiorna schermo successivo
            if(listaInput.size() > 0)
                menuRisposta.setItems(listaInput.get(indice));
            menuRisposta.getSelectionModel().select(-1);
            
            if(domande != null)
                if(domande.size() > 0)
                    domanda.setText(domande.get(indice));
            
            aggiornaTitolo();
        }
    }

    @FXML
    protected void chiudiSenzaSalvare(ActionEvent event) {
        chiudi();
    }

    @FXML
    protected void indietro(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            final int limite = listaInput.size() < domande.size() ? listaInput.size() : domande.size();
        
            if(indice <= 0 || indice >= limite){ // USCITA
                chiudi();
            }else{
                
            }
            indice--;
        }
    }
    
    private void chiudi(){
    	Finestra.ricaricaFinestra(this);
    }
    
}
