package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.Finestra.R;
import it.quasar_x7.javafx.finestre.modello.VoceSempliceLista;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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
public class MultiListaController implements Initializable {

    


    public interface Codice{
        public void aggiungi();
        public void elimina(String riga);
        public void seleziona(String riga);
    }
    
    private static Codice azione;
    
    public static Scene scenaCorrente;
    
    public static HashMap<String,ArrayList<String>> dati = null;
    
    public static String nomeEtichettaMenu = null;
    
    public static final ObservableList<VoceSempliceLista> lista = FXCollections.observableArrayList();
    
        
    @FXML
    protected ContextMenu menuLista;
    
    @FXML
    private Label etichettaMenu;
    
    @FXML
    private ChoiceBox<String> menu;
    
    @FXML
    private Label titolo;
    
    private static String testoTitolo = null;

    @FXML
    private TableColumn<VoceSempliceLista,String> colonna;

    @FXML
    private TableView<VoceSempliceLista> tabella;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inizializzaLista();
        if(testoTitolo != null)
            titolo.setText(testoTitolo);
        if(nomeEtichettaMenu != null)
            etichettaMenu.setText(nomeEtichettaMenu);
        if(dati != null)
            menu.getItems().addAll(dati.keySet());
        
        menu.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String vecchiaVoce, String nuovaVoce) -> {
                    lista.clear();
                    for(String voce : dati.get(nuovaVoce)){
                        lista.add(new VoceSempliceLista(voce));
                    }
                }
        );
        colonna.setCellValueFactory(new PropertyValueFactory<>(Finestra.R.MODELLO_VOCE_SEMPLICE));
        tabella.setItems(lista);
        
    }   
    
    public static void input(String titolo, String nomeInput, HashMap<String,ArrayList<String>> voci, Codice azioni) {
        testoTitolo = titolo;
        nomeEtichettaMenu = nomeInput;
        lista.clear();
        dati = voci;
        MultiListaController.azione = azioni;
    }
    
    
    

    @FXML
    void aggiungi(ActionEvent event) {
        azione.aggiungi();
    }

    @FXML
    void chiusuraSenzaSalvare(ActionEvent event) {
        chiudi();
        
    }

    @FXML
    void elimina(ActionEvent event) {

        final int id = tabella.getSelectionModel().getSelectedIndex();
        final int numeroElementi = tabella.getItems().size();
        
        if(id >= 0 && id < numeroElementi){
                String voce = tabella.getSelectionModel().getSelectedItem().getVoce();
                Finestra.finestraConferma(
                        this, 
                        String.format("Sei Sicuro di voler eliminare «%s» ?",voce),
                        () -> {
                           azione.elimina(voce);
                           lista.remove(id);
                        }
                );
            
        }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            
        }
    }


    @FXML
    void seleziona(ActionEvent event) {
        final int id = tabella.getSelectionModel().getSelectedIndex();
        final int numeroElementi = tabella.getItems().size();
        
        if(id >= 0 && id < numeroElementi){
            azione.seleziona(
                tabella.getSelectionModel().getSelectedItem().getVoce()    
            );
        }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            
        }
    }
    
    public static void inizializzaLista() {
       // lista.clear();
        //ListaController.azione = null;
        //testoTitolo = "";
    }

    
    private void chiudi(){
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
        nomeEtichettaMenu = null;
        testoTitolo = null;
    }
    
    @FXML
    private void selezionaMenu(ActionEvent event) {
        if(event.getSource() instanceof ChoiceBox<?>){
            if(dati != null){
                String voceMenu = menu.getSelectionModel().getSelectedItem();
                if(voceMenu != null){
                    lista.clear();
                    for(String voce : dati.get(voceMenu)){
                        lista.add(new VoceSempliceLista(voce));
                    }
                }
            }
        }

    }
    
    
    
    @FXML
    void caricaLista(MouseEvent event) {
        if(dati != null)
            if(menu.getItems().isEmpty())
                menu.getItems().addAll(dati.keySet());
    }
    
    
    @FXML
    protected void visualizzaMenu(MouseEvent event){
        if((event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) || event.getButton().equals(MouseButton.SECONDARY) ){
            if(event.getSource() instanceof TableView){
                TableView tabellaCorrente = (TableView) event.getSource();
                menuLista.show(tabellaCorrente, event.getScreenX(), event.getScreenY());
            }
            
        }
        event.consume();
    }
}
