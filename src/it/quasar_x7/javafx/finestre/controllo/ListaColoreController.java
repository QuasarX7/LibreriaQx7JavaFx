package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import static it.quasar_x7.javafx.finestre.controllo.ListaController.testoTitolo;
import it.quasar_x7.javafx.finestre.modello.VoceListaColore;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

/**
 * 
 *
 * @author Dr. Domenico della Peruta
 */
public class ListaColoreController extends ListaController {

    @FXML
    private TableColumn<VoceListaColore, Color> colore;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Finestra.infoFinestreAperte(pulsanteChiusura);
        if(testoTitolo != null)
            titolo.setText(testoTitolo);
        
        colonna.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_VOCE_SEMPLICE));
        colore.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_ID_VOCE_COLORE));
        colore.setCellFactory(CellaPannelloColore.disegna());
        tabella.setItems(lista);
    }   
    
    public static void aggiungiVoce(String voce,Color colore){
        lista.add(new VoceListaColore(voce,colore));
    }
    
        
    public static void inputColore(String titolo, ArrayList<Object[]> voci, Codice azioni) {
        testoTitolo = titolo;
        if(voci != null){
            lista.clear();
            for(Object[] riga : voci){
                if((riga[0] instanceof String) && (riga[1] instanceof Color) )
                    lista.add( new VoceListaColore((String)riga[0],(Color)riga[1]) );
            }
        }
        ListaController.azione = azioni;
    }

    
    
}
