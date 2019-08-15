package it.quasar_x7.javafx.finestre.controllo;

import it.quasar_x7.javafx.Finestra;
import it.quasar_x7.javafx.Finestra.R;
import it.quasar_x7.javafx.TipoFile;
import static it.quasar_x7.javafx.finestre.controllo.FileController.Stato.APRI;
import static it.quasar_x7.javafx.finestre.controllo.InputController.azione;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ninja
 */
public class FileController extends InputController {

    public enum Stato{
        SALVA,APRI
    }
    
    public interface CodiceFile{
        /**
         * 
         * @param file   da creare o leggere
         * 
         * @return Task  che implementa il processo di elaborazione in parallelo  
         *               dopo clic del pulsante predefinito e la comparsa della 
         *               barra dei progressi.
         */
        public Task esegui(Path file);
    }
    
    private static CodiceFile azioneIO = null;
    
    private static Stato stato = null;
    
    
    private static TreeItem<File> nodo = null;

    private static TipoFile estensioneFile = null;
    
    protected File fileCorrente = null;
   
    @FXML
    private CheckBox fileNascosti;

    @FXML
    private CheckBox tuttiFile;

    @FXML
    private TreeView<File> tabellaFile;
    
    
    @FXML
    private Label titolo;
    
    

    
    private final TreeItem<File> HOME = new TreeItem<>(new File(System.getProperty("user.home")));
    private final TreeItem<File> POSIZIONE_CORRENTE = new TreeItem<>(new File(System.getProperty("user.dir")));
    private final ArrayList<TreeItem<File>> DRIVER_HD = new ArrayList<>();
    private TreeItem<File> DESKTOP = null;
    private TreeItem<File> DOCUMENTI = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(estensioneFile == null)
            estensioneFile = TipoFile.TXT;
        
        Finestra.infoFinestreAperte(pulsanteChiusura);    
            
        if(stato == APRI){
            input.setEditable(false);
            domanda.setText(String.format("Apri file tipo `%s`",estensioneFile.estensione()));
            titolo.setText(String.format("Apri File «%s»",estensioneFile.nome()));
        }else{
            domanda.setText(String.format("Salva file `%s`",suggerimento));
            if(suggerimento != null)
                input.setText(suggerimento);
            titolo.setText(String.format("Salva File «%s»",estensioneFile.nome()));
        }
        
        fileNascosti.setSelected(false);
        tuttiFile.setSelected(false);
        nodo = null;
        TreeItem<File> fileSystem = new TreeItem<>();
        tabellaFile.setRoot(fileSystem);
        tabellaFile.setShowRoot(false);
        
        File[] fileUtente = HOME.getValue().listFiles();
        for(File dir:fileUtente){
            if(dir.isDirectory()){
                String nome = dir.getName();
                if(nome.equals("Desktop") || nome.equals("Scrivania") ){
                    DESKTOP = new TreeItem<>(dir);
                }else if(nome.equals("Documenti") || nome.equals("Documents") ){
                    DOCUMENTI = new TreeItem<>(dir);
                }
            }
        }
        if(DESKTOP != null)
            fileSystem.getChildren().add(DESKTOP);
        if(DOCUMENTI != null)
            fileSystem.getChildren().add(DOCUMENTI);
        fileSystem.getChildren().add(HOME);
        fileSystem.getChildren().add(POSIZIONE_CORRENTE);
        
        File[] roots = File.listRoots();
        if(roots != null)
            for(File root: roots)  {
                TreeItem<File> nodoRoot = new TreeItem<>(root);
                DRIVER_HD.add(nodoRoot);
                 fileSystem.getChildren().add(nodoRoot);
            }

        
        tabellaFile.setOnMouseClicked((MouseEvent event) -> {
                    nodo = tabellaFile.getSelectionModel().getSelectedItem();
                    if(nodo != null){
                        File file = nodo.getValue();
                        if(file != null){
                            if(file.isDirectory() ){
                                File[] lista;
                                if(!tuttiFile.isSelected()){
                                     lista = file.listFiles(//filtra i file con 'estensioneFile' es.: ".doc"
                                            (File dir, String name) -> {
                                                if(new File(dir,name).isDirectory())
                                                    return true;
                                                return name.endsWith(estensioneFile.estensione());
                                            }
                                     );
                                }else
                                    lista = file.listFiles();//tutti i tipi di file
                                if(lista != null){
                                    nodo.getChildren().clear();
                                    for(File nuovoFile : lista){
                                        if(!nuovoFile.isHidden() || fileNascosti.isSelected())
                                            nodo.getChildren().add(new TreeItem<>(nuovoFile) );
                                    }
                                }
                                
                                if(suggerimento != null)
                                    input.setText(suggerimento);
                                else
                                    input.setText("");
                                domanda.setText(
                                        String.format(R.INFO_FILE,
                                                input.getText(),
                                                new File(nodo.getValue(),input.getText()).getPath())
                                );
                                
                            }else{//file.isFile()
                                if(suggerimento != null)
                                        input.setText(suggerimento);
                                else
                                    input.setText("");
                                
                                
                                if(nodo.getValue().getName().endsWith(estensioneFile.estensione())){
                                    input.setText(nodo.getValue().getName());
                                    domanda.setText(
                                        String.format(R.INFO_FILE,
                                                input.getText(),
                                                nodo.getValue().getAbsolutePath())
                                    );
                                }else{
                                    domanda.setText(
                                        String.format(R.INFO_FILE,
                                                input.getText(),
                                                new File(
                                                        nodo.getValue().toPath().getParent().toFile(),
                                                        input.getText() 
                                                ).getAbsolutePath()
                                        )
                                    );
                                }
                                    
                                
                            }
                        }
                        nodo.setExpanded(!nodo.isExpanded());//commuta...
                    }
                }
        );
        
        tabellaFile.setCellFactory(
                (TreeView<File> tv) -> new TreeCell<File>() {
                    final Image immagineCartella = new Image(R.IMMAGINE_CARTELLA,30,30,true,true);
                    final Image immagineFile = new Image(R.IMMAGINE_FILE,20,20,true,true);
                    final Image immagineDocumenti = new Image(R.IMMAGINE_DOCUMENTI,30,30,true,true);
                    final Image immagineHome = new Image(R.IMMAGINE_CASA,30,30,true,true);
                    final Image immagineHD = new Image(R.IMMAGINE_HD,30,30,true,true);
                    final Image immagineDesktop = new Image(R.IMMAGINE_DESKTOP,30,30,true,true);
                    final Image immagineCartellaCorrente = new Image(R.IMMAGINE_CARTELLA_CORRENTE,30,30,true,true);

                    
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setText(item.getName());
                            //setGraphic(getTreeItem().getGraphic());
                            if(item.isDirectory()){
                                if(item.equals(POSIZIONE_CORRENTE.getValue())){
                                    setGraphic(new ImageView(immagineCartellaCorrente));
                                    
                                }else if(item.equals(HOME.getValue())){
                                    setGraphic(new ImageView(immagineHome));
                                    
                                }else if(item.equals(DESKTOP.getValue())){
                                    setGraphic(new ImageView(immagineDesktop));
                                    
                                }else if(item.equals(DOCUMENTI.getValue())){
                                    setGraphic(new ImageView(immagineDocumenti));
                                    
                                }else if(root(item)){
                                    setGraphic(new ImageView(immagineHD));
                                }else
                                    setGraphic(new ImageView(immagineCartella));
                            }else{
                                TipoFile tipoFile = TipoFile.tipo(item);
                                if(tipoFile != null){
                                    setGraphic(
                                            new ImageView(new Image(tipoFile.immagine(),20,20,true,true))
                                    );
                                }else
                                    setGraphic(new ImageView(immagineFile));
                            }
                        }
                    }
                }
        );
        
    }
    
    @FXML
    protected void compattaAlbero(ActionEvent event) { 
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            nodo = null;
            TreeItem<File> fileSystem = tabellaFile.getRoot();
            if(fileSystem != null){
                if(fileSystem.getChildren() != null)
                    for(TreeItem<File> nodo : fileSystem.getChildren()){
                        nodo.setExpanded(false);

                    }
            }
        }
    }
    
    private boolean root(File file){
        for(TreeItem<File> root:DRIVER_HD){
            File r = root.getValue();
            if(r != null)
                if(r.equals(file))
                    return true;
        }
        return false;
    }
    
    public static void inputFile(String nome,TipoFile estenzione, CodiceFile ok){
        suggerimento = nome;
        stato = nome != null ? Stato.SALVA : Stato.APRI;
        estensioneFile = estenzione;
        azioneIO = ok;
        
    }
    
    
    @FXML
    @Override
    protected void pulsanteOK(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
            if(nodo != null){
                if(input.getText().length() > 0){
                    File file = nodo.getValue();
                    
                    if(nodo.getValue().isDirectory()){
                        fileCorrente = new File(
                                file.getAbsolutePath(),
                                fileInputConEstensione()
                        );
                    }else{

                        fileCorrente = new File(
                                file.toPath().getParent().toFile(),
                                fileInputConEstensione()
                        );

                    }
                    
                                    
                    Finestra.finestraBarraProgressi(
                            this, 
                            new BarraProgressiController.Codice() {
                                
                                @Override
                                public Task creaTask() {
                                    
                                    Path path = fileCorrente.toPath();
                                    Task task = azioneIO.esegui(path);
                                    return task;
                                }

                                @Override
                                public void esci() {
                                	Finestra.eliminaUltimaFinestraRegistrata();
                                    chiudi();
                                }
                            }
                    );
                }
                else
                    Finestra.finestraAvviso(this,"Nessun file specificato");
            }
            else
                Finestra.finestraAvviso(this,"Seleziona la posizione di file");
                
        }
    }
    
    
    @FXML
    private void aggiornaInfoInput(KeyEvent event){
        if( event.getEventType().equals(KeyEvent.KEY_RELEASED)){
            if(stato == Stato.SALVA)
                domanda.setText(
                     String.format(
                            "Salva file `%s`\nposizione:\n`%s`",
                            input.getText(),
                            new File(
                                    nodo.getValue().toPath().getParent().toFile(),
                                    fileInputConEstensione()
                            ).getAbsolutePath()
                    )
                );
        }
    }
      
    
    private String fileInputConEstensione(){
        return input.getText().endsWith(estensioneFile.estensione()) 
                ? input.getText() 
                : input.getText() + estensioneFile.estensione();
    }
    
    
}
