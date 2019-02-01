package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Dr. Dommenico della Peruta
 */
public class TabellaRicercaController extends TabellaController {

    

    public interface CodiceRicerca {
        public ArrayList<ArrayList<String>> ricerca(String riga);
    }
    
    private static  CodiceRicerca ricerca;
    
    @FXML
    private Label nomeCampo;

    @FXML
    private ComboBox<String> campo;
    
    private static String etichettaInput = "";
    private static ArrayList<String> inputLista;
    private static String valoreInizialeCampo = "";
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeCampo.setText(etichettaInput);
        if(inputLista != null)
            campo.getItems().addAll(inputLista);
        
        if(valoreInizialeCampo != null)
            campo.setValue(valoreInizialeCampo);
        super.initialize(url,rb);
    } 

    public void impostaValoreCampo(String valore){
        valoreInizialeCampo = valore;
        campo.setValue(valoreInizialeCampo);
    }

    @FXML
    void cerca(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            tabella.getColumns().clear();
            tabella.getItems().clear();
            righe = ricerca.ricerca(campo.getValue());
            if(righe != null){
                if(righe.size() > 0){
                    caricaTabella();
                }
            
            }
        }
    }

       
    public static void input(String titolo, String nomeCampo, ArrayList<String> inputCampo, ArrayList<String> colonne, ArrayList<Integer> dimColonne, Codice azione,CodiceRicerca cerca) {
        TabellaController.input(titolo, colonne, dimColonne, null, azione);
        etichettaInput = nomeCampo;
        inputLista = inputCampo;
        ricerca = cerca;
        TabellaRicercaController.azione = azione;
    }
    
    
    @Override
    protected void chiudi(){
        super.chiudi();
        inputLista = null;
        etichettaInput = "";
    }
    
    
     /**
     * Riduci la finestra ad icona.
     * @param event 
     */
    @FXML
    private void riduciFinestra(ActionEvent event) {
        Finestra.riduciFinestra();
    }
    
    
    @FXML
    private void schermoIntero(ActionEvent event){
        Finestra.schermoIntero();
    }
    
    public String valoreCampo() {
        return campo.getValue();
    }
    
    
    @Override
    public void caricaTabella(){
        tabella.getColumns().clear();
        tabella.getItems().clear();
        if(campo.getValue() != null)
            if(campo.getValue().length() > 0)
                super.caricaTabella();
    }
}
