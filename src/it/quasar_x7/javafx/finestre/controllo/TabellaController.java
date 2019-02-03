package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.CampoTesto.Colore;
import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.Finestra.R;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Dr. Domenico della Peruta
 */
public class TabellaController implements Initializable {

    
    
    
    public interface Codice{
        public void aggiungi(TabellaController delega);
        public boolean elimina(TabellaController delega, String primaCella);
        public void modifica(TabellaController delega, String primaCella);
        //public void seleziona(TabellaController delega, String[] celle);
    }
    
    protected static Codice azione;
    
    @FXML
    protected ContextMenu menuTabella;
        
    @FXML
    protected Label titolo;

    @FXML
    protected TableView<ObservableList<String>> tabella;

    public static Scene scenaCorrente;
    
    static private boolean totale = false;
    
    
    protected static String testoTitolo = "";
    protected static ArrayList<String> colonneTabella       = null;
    protected static ArrayList<ArrayList<String>> righe     = null;
    protected static ArrayList<Integer> dimensioneColonne   = null;

    protected int idColonna = -1, idRiga = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(righe == null)
            righe = new ArrayList<>();
        if(testoTitolo != null)
            titolo.setText(testoTitolo);
        tabella.getSelectionModel().setCellSelectionEnabled(true);
        
        final ObservableList<TablePosition> cellaSelezionata = tabella.getSelectionModel().getSelectedCells();
        cellaSelezionata.addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(Change change) {
                for (TablePosition pos : cellaSelezionata) {
                    idColonna = pos.getColumn();
                    idRiga = pos.getRow();
                }
            }
        
        });
                
        caricaTabella();
        

    } 
    
    
    
    @FXML
    protected void visualizzaMenu(MouseEvent event){
        if((event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) || event.getButton().equals(MouseButton.SECONDARY) ){
            if(event.getSource() instanceof TableView){
                TableView tabellaCorrente = (TableView) event.getSource();
                menuTabella.show(tabellaCorrente, event.getScreenX(), event.getScreenY());
            }
            
        }
        event.consume();
    }
    
    
    
    @FXML
    protected void aggiungiRiga(ActionEvent event) {
        if(azione != null){
            azione.aggiungi(this);
        }
        else
            Finestra.finestraAvviso(this, "Azione non definita !");
    }
    


    
    @FXML
    protected void modificaRiga(ActionEvent event) {
        if(azione != null){
            ObservableList<String> riga = tabella.getSelectionModel().getSelectedItem();
            if(riga != null){
                if(riga.size() > 0){
                    azione.modifica(this, riga.get(0));
                }
            }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            }
        }
        else
            Finestra.finestraAvviso(this, "Azione non definita !");
            
    }
  
    @FXML
    protected void eliminaRiga(ActionEvent event) {
        if(azione != null){
            ObservableList<String> riga = tabella.getSelectionModel().getSelectedItem();
            if(riga != null){
                if(riga.size() > 0){
                        Finestra.finestraConferma(this,
                                String.format(
                                        "Vuoi veramente eliminare \n« %s »",
                                        riga.get(0)
                                ), 
                                () -> {
                                    if(azione.elimina(this,riga.get(0))){
                                        tabella.getItems().remove(riga);
                                    }
                                }
                        );
                        
                    
                }
                
            }else{
                Finestra.finestraAvviso(this, R.AVVISO_NESSUNA_VOCE_SELEZIONATA);
            }
        }
        else
            Finestra.finestraAvviso(this, "Azione non definita !");
            
    }
   
    static public void abilitaTotali(){
        totale = true;
    }
    
    public String[] rigaSelezionata(){
        ObservableList<String> lista = tabella.getSelectionModel().getSelectedItem();
        if(lista != null)
            return lista.toArray(new String[lista.size()]);
        return null;
    }
    
    public int colonnaSelezionata(){
        return idColonna;
    }
    
    public int indiceRigaSelezionata(){
        return idRiga;
    }
    
    
    public void caricaTabella(){
        tabella.getColumns().clear();
        tabella.getItems().clear();
        if(colonneTabella != null && dimensioneColonne != null){
            //dimensiona tabella
            double totLunghezza = 100;
            for(int i=0; i < colonneTabella.size(); i++){
                if(i < dimensioneColonne.size()){
                    totLunghezza += dimensioneColonne.get(i);
                }else{
                    totLunghezza += (totLunghezza/(i+1)); // += valore medio
                }
            }
            
            double totAltezza = (righe != null)  ? righe.size()*25 + 80 : 45;
            Parent riquadro = tabella.getParent();
            if(riquadro != null){
                
                double maxH = riquadro.getLayoutBounds().getHeight();
                if(totAltezza < maxH)
                    totAltezza = maxH;
                
                double maxL = riquadro.getLayoutBounds().getWidth();
                if(totLunghezza < maxL)
                    totLunghezza = maxL;
                
            }
            tabella.setPrefWidth(totLunghezza);
            tabella.setPrefHeight(totAltezza);
            
            // aggiungi colonne
            for(int i=0; i < colonneTabella.size(); i++){
                final int indirizzo = i;
                TableColumn<ObservableList<String>, String> colonna = new TableColumn<>(colonneTabella.get(i));
                if(i == 0){
                    colonna.setStyle("-fx-text-fill: #ADD8E6;");
                }
                
                colonna.setCellValueFactory(
                        param -> new ReadOnlyObjectWrapper<>(param.getValue().get(indirizzo))
                );
                if(i < dimensioneColonne.size())
                    colonna.setPrefWidth(dimensioneColonne.get(i));
                tabella.getColumns().add(colonna);
            }
            if(totale){ // aggiungi colonna totale
                int ultimaColonna = colonneTabella.size();
                TableColumn<ObservableList<String>, String> colonna = new TableColumn<>("totale");
                colonna.setStyle(Colore.VERDE);
                
                colonna.setCellValueFactory(
                        param -> new ReadOnlyObjectWrapper<>(param.getValue().get(ultimaColonna))
                );
                
                tabella.getColumns().add(colonna);
            
            }
        
            
            //aggiungi righe

            if(righe != null){
                for(ArrayList<String> riga: righe){
                    tabella.getItems().add(
                            FXCollections.observableArrayList(
                                    riempiCelleVuote(riga)
                            )
                    );
           
                }
                if(totale){ 
                    // aggiungi valori alla "colonna totale"
                    int i=0;
                    for(ArrayList<String> riga: righe){
                        double somma = 0.0;
                        for(String cella : riga){
                            try {
                                somma += Double.parseDouble(cella);
                            } catch (NumberFormatException e) {}
                        }
                        tabella.getItems().get(i++).set(colonneTabella.size(), somma+"");
                    }
                    // aggiungi "riga totale"
                    ObservableList<String> rigaTotali = FXCollections.observableArrayList();
                    rigaTotali.add("totale");
                    for(int c=1; c < tabella.getColumns().size(); c++){
                        double somma = 0.0;
                        for(int r=0; r < tabella.getItems().size(); r++){
                            String cella = tabella.getItems().get(r).get(c);
                            try {
                                somma += Double.parseDouble(cella);
                            } catch (NumberFormatException e) {}
                        }
                        rigaTotali.add(""+somma);
                        
                        tabella.setRowFactory( param -> {
                            return  new TableRow<ObservableList<String>>() {
                                
                                @Override
                                protected void updateItem(ObservableList<String> riga, boolean empty) {
                                    super.updateItem(riga, empty);
                                    if(!empty)
                                        if(riga.equals(rigaTotali)){
                                            getStyleClass().add("important-row"); 
                                        }
                                    
                                }
                            };
                        });
                        
                        
                    }
                    tabella.getItems().add(rigaTotali);
                }
                
                
                
                    
            }
            
       
        }
    }
    
    
    private ArrayList<String> riempiCelleVuote(ArrayList<String> riga){
        int numeroVuoti = colonneTabella.size() - riga.size();
        for(int i=0; i < numeroVuoti && numeroVuoti > 0 ; i++)
            riga.add("");
        if(totale)
            riga.add("");
        return riga;
    }
   
    
    @FXML
    protected void chiusuraSenzaSalvare(ActionEvent event) {
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
        testoTitolo = null;
        colonneTabella = null;
        righe = null;
        dimensioneColonne = null;
        totale = false;
    }
    
    public static void input(String titolo, ArrayList<String> colonne,ArrayList<Integer> dimColonne, ArrayList<ArrayList<String>> righe, Codice azione) {
        testoTitolo = titolo;
        colonneTabella = colonne;
        dimensioneColonne = dimColonne;
        TabellaController.righe = righe;
        TabellaController.azione = azione;
        
    }
    
    
    
    public void modificaRighe(ArrayList<String> colonne,ArrayList<ArrayList<String>> righe){
        if(righe != null){
            if(righe.size() > 0){
                TabellaController.righe = righe;
                tabella.getColumns().clear();
                tabella.getItems().clear();
                colonneTabella = colonne;
                caricaTabella();
            }
        }
    }
    
    
    public void aggiungiRiga(ArrayList<String> riga){
        righe.add(riga);
        caricaTabella();
    }

    /**
     * Modifice riga a chiave singola
     * @param chiave
     * @param riga 
     */
    public void modificaRiga(String chiave, ArrayList<String> riga){
        int i =0;
        if(righe != null)
		    for(ArrayList<String> record : righe){
		        if(record.get(0).equals(chiave)){
		            righe.set(i, riga);
		            //righe.remove(i);
		            break;
		        }
		        i++;
		    }
        caricaTabella();
    }
    /**
     * Modifica riga a chiave multi-colonna
     * 
     * @param valoriChiave
     * @param colonneChiave
     * @param riga 
     */
    public void modificaRiga(ArrayList<String> valoriChiave, ArrayList<Integer>colonneChiave, ArrayList<String> riga){
        int i =0,j=0;
        for(ArrayList<String> record:righe){
            for(; j < valoriChiave.size() || j < colonneChiave.size(); j++){
                if(!record.get(colonneChiave.get(j)).equals(valoriChiave.get(j)))
                    break;
            }
            if(j == valoriChiave.size()){
                righe.set(i, riga);
                righe.remove(i);
                break;
            }
            i++;
        }
        caricaTabella();
    }

    
}
