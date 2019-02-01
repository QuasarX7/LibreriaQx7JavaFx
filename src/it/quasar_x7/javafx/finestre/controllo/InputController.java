package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.CampoTesto;
import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.finestre.modello.VoceListaColore;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Gestore della finestra di dialoga a input menu.
 *
 * @author Dott. Domenico della Peruta
 */
public class InputController implements Initializable {

    static public ArrayList<String> elencoInput = null;

    static public String suggerimento = null;

    
    
    public interface Codice{
        /**
         * Compie un'azione sul valore immesso nel campo dell'input
         * @param risposta 
         * @return true se il valore immesso è corretto
         */
        public boolean esegui(String risposta);
    }
    
    protected static Codice azione;

    public static Scene scenaCorrente;
    
    private static String testo;
    
   
    
    @FXML
    protected TextField input;

    @FXML
    protected TextArea domanda;
    
   

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(suggerimento != null){
            input.setText(suggerimento);
        }
        if(testo != null)
            domanda.setText(testo);
        else
            domanda.setText("Inserisci il valore nel campo");
        if(elencoInput != null)
            if(elencoInput.size() > 0)
                CampoTesto.autoCompletamento(input, elencoInput);
    }  
    
    public static void input(String testoDomanda, Codice ok){
        testo = testoDomanda;
        azione = ok;
    } 
    
    public static void input(String testoDomanda, ArrayList<String> rispostePossibili, Codice ok){
        testo = testoDomanda;
        azione = ok;
        elencoInput = rispostePossibili;
    } 
    
    public static void input(String testoDomanda, String suggerimento, Codice ok) {
        input(testoDomanda,ok);
        InputController.suggerimento = suggerimento;
    }
    
    @FXML
    protected void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if(azione.esegui(input.getText()))
                chiudi();
            else{
                Finestra.finestraAvviso(this, "Input non valido!");
            }
        }
    }
    
    @FXML
    protected void chiudiSenzaSalvare(ActionEvent event) {
        chiudi();
    }

    
    
    protected void chiudi(){
        if(scenaCorrente != null)
            Finestra.vistaCorrente.setScene(scenaCorrente);
        
        else if(Finestra.finestraPrincipale != null)
            Finestra.vistaCorrente.setScene(Finestra.finestraPrincipale);
        else{
            Platform.exit();
            System.exit(0);
        }   
        Finestra.adattaFinestra();
        Finestra.vistaCorrente.show();
        scenaCorrente = null;
        elencoInput = null;
        suggerimento = null;
    }
    
    
    
    
}
