package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.finestre.modello.VoceOrdinataLista;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Dr. Domenico della Peruta
 */
public class ListaOrdinataController extends ListaController {
    
    
    @FXML
    private TableColumn<VoceOrdinataLista, Integer> id;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Finestra.infoFinestreAperte(pulsanteChiusura);
        if(testoTitolo != null)
            titolo.setText(testoTitolo);
        
        id.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_ID_VOCE_ORDINATA));
        colonna.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_VOCE_SEMPLICE));
        
        tabella.setItems(lista);
        
    } 

        
    public static void aggiungiVoce(String voce,int indice){
        lista.add(new VoceOrdinataLista(indice,voce));
    }
    
        
    public static void inputOrdinato(String titolo, ArrayList<Object[]> voci, Codice azioni) {
        testoTitolo = titolo;
        lista.clear();
        TreeSet<VoceOrdinataLista> listaOrdinata = new TreeSet<>();
        for(Object[] riga : voci){
            if((riga[0] instanceof Integer) && (riga[1] instanceof String) )
                listaOrdinata.add( new VoceOrdinataLista((Integer)riga[0],(String)riga[1]) );
        }
        lista.addAll(listaOrdinata);
        ListaController.azione = azioni;
    }
    
}
