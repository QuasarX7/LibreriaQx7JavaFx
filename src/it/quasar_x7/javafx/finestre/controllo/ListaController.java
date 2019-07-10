package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.Finestra.R;
import it.quasar_x7.javafx.finestre.modello.VoceSempliceLista;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ninja
 */
public class ListaController implements Initializable {


    public interface Codice{
        /**
         * 
         * @param lista     riferimento alla lista da aggiornare se l'aggiunta
         *                  è riuscita, tramite il metodo 
         *                  <code>lista.add(new VoceSempliceLista(...))</code>.
         */
        public void aggiungiRiga(ObservableList<VoceSempliceLista> lista);
        /**
         * 
         * @param riga      contiene le informazioni dell'intera riga, 
         *                  codificate nel metodo <code>toString()</code> 
         *                  del modello corrispondente
         * @return 
         */
        public boolean eliminaRiga(String riga);
        /**
         * 
         * @param riga      contiene le informazioni dell'intera riga, 
         *                  codificate nel metodo <code>toString()</code> 
         *                  del modello corrispondente
         * 
         * @param idRiga    indice della riga nella lista.
         * 
         * @param lista     riferimento alla lista da aggiornare...
         */
        public void modifica(String riga,int idRiga,ObservableList<VoceSempliceLista> lista);
    }
    
    static public Codice azione;
    
    
    protected static ObservableList<VoceSempliceLista> lista = FXCollections.observableArrayList();
    
    @FXML
    protected ContextMenu menu;
        
    @FXML
    protected Label titolo;
    
    protected static String testoTitolo = "";

    @FXML
    protected TableColumn<VoceSempliceLista,String> colonna;

    @FXML
    protected TableView<VoceSempliceLista> tabella;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(testoTitolo != null)
            titolo.setText(testoTitolo);
        
        colonna.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_VOCE_SEMPLICE));
        
        tabella.setItems(lista);
        
    }  
 
    
    public static void aggiungiVoce(String voce){
        lista.add(new VoceSempliceLista(voce));
    }
    
    public static void input(String titolo, ArrayList<String> voci, Codice azioni) {
        testoTitolo = titolo;
        lista.clear();
        if(voci != null)
            for( String riga : voci){
                lista.add(new VoceSempliceLista(riga));

            }
        ListaController.azione = azioni;
    }
    
    
    

    @FXML
    protected void aggiungi(ActionEvent event) {
        azione.aggiungiRiga(lista);
    }

    @FXML
    protected void chiusuraSenzaSalvare(ActionEvent event) {
        chiudi();
    }

    @FXML
    protected void elimina(ActionEvent event) {

        final int id = tabella.getSelectionModel().getSelectedIndex();
        final int numeroElementi = tabella.getItems().size();
        
        if(id >= 0 && id < numeroElementi){
                String voce = tabella.getSelectionModel().getSelectedItem().toString();
                Finestra.finestraConferma(
                        this, 
                        String.format("Sei Sicuro di voler eliminare «%s» ?",voce),
                        () -> {
                            if(!azione.eliminaRiga(voce)){
                                    Finestra.finestraAvviso(
                                            this,
                                            String.format(
                                                    "Spiacente, impossibile eliminare «%s».",
                                                    voce
                                            )
                                    );
                                
                            }else
                                 lista.remove(id);
                        }
                );
                
            
        }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            
        }
    }


    @FXML
    protected void seleziona(ActionEvent event) {
        final int id = tabella.getSelectionModel().getSelectedIndex();
        final int numeroElementi = tabella.getItems().size();
        
        if(id >= 0 && id < numeroElementi){
            azione.modifica(
                tabella.getSelectionModel().getSelectedItem().toString(),id,lista
            );
        }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            
        }
    }


    
    private void chiudi(){
        Finestra.ricaricaFinestra(this);
    }
    
    
    @FXML
    protected void visualizzaMenu(MouseEvent event){
        if((event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) || event.getButton().equals(MouseButton.SECONDARY) ){
            if(event.getSource() instanceof TableView){
                TableView tabellaCorrente = (TableView) event.getSource();
                menu.show(tabellaCorrente, event.getScreenX(), event.getScreenY());
            }
            
        }
        event.consume();
    }
}
