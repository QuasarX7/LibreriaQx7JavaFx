package it.quasar_x7.javafx;

import it.quasar_x7.javafx.finestre.controllo.AvvisoController;
import it.quasar_x7.javafx.finestre.controllo.BarraProgressiController;
import it.quasar_x7.javafx.finestre.controllo.ConfermaController;
import it.quasar_x7.javafx.finestre.controllo.FileController;
import it.quasar_x7.javafx.finestre.controllo.FinestraDebugController;
import it.quasar_x7.javafx.finestre.controllo.InputController;
import it.quasar_x7.javafx.finestre.controllo.InputMenuController;
import it.quasar_x7.javafx.finestre.controllo.InputStringaColoreController;
import it.quasar_x7.javafx.finestre.controllo.ListaColoreController;
import it.quasar_x7.javafx.finestre.controllo.ListaController;
import it.quasar_x7.javafx.finestre.controllo.ListaOrdinataController;
import it.quasar_x7.javafx.finestre.controllo.MultiListaController;
import it.quasar_x7.javafx.finestre.controllo.TabellaController;
import it.quasar_x7.javafx.finestre.controllo.TabellaRicercaController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Dr. Domenico della Peruta
 */
public class Finestra {
    
    public static Stage vistaCorrente;
    public static Scene finestraPrincipale;

    public static Stack<Scene> registroScene = new Stack<>();
    
    static public class Posizione{
        public static double x = 0;
        public static double y = 0;
    };
    

    
    
    public class R{
        static private final String PATH = "/it/quasar_x7/javafx/finestre/vista/";
        static public final String FINESTRA_AVVISO = PATH + "Avviso.fxml";
        static public final String FINESTRA_LISTA = PATH + "Lista.fxml";
        static public final String FINESTRA_MULTI_LISTA = PATH + "MultiLista.fxml";
        static public final String FINESTRA_CONFERMA = PATH + "Conferma.fxml";
        static public final String FINESTRA_INPUT_MENU = PATH + "InputMenu.fxml";
        static public final String FINESTRA_INPUT = PATH + "Input.fxml";
        static public final String FINESTRA_INPUT_COLORE = PATH + "InputStringaColore.fxml";
        static public final String FINESTRA_TABELLA =  PATH + "Tabella.fxml";
        static public final String FINESTRA_TABELLA_RICERCA =  PATH + "TabellaRicerca.fxml";
        static public final String FINESTRA_LISTA_ORDINATA = PATH + "ListaOrdinata.fxml";
        static public final String FINESTRA_LISTA_COLORATA = PATH + "ListaColore.fxml";
        static public final String FINESTRA_DEBUG = PATH + "FinestraDebug.fxml";
        public static final String FINESTRA_FILE = PATH + "File.fxml";
        public static final String FINESTRA_PROGRESSI = PATH + "BarraProgressi.fxml";
        
        public static final String INFO_FILE = "[*] nome file: `%s`\n[*] posizione:\n   `%s`";
        
        static public final String MODELLO_VOCE_SEMPLICE = "voce";
        static public final String MODELLO_ID_VOCE_ORDINATA = "id";
        static public final String MODELLO_ID_VOCE_COLORE = "colore";
        
        static public final String AVVISO_NESSUNA_VOCE_SELEZIONATA = "Non è stata selezionata nessuna voce valida dalla tabella!";
        
        static private final String PATH_RISORSE = "/it/quasar_x7/javafx/finestre/risorse/";
        
        public static final String IMMAGINE_CARTELLA = PATH_RISORSE + "cartella-mini.gif";
        public static final String IMMAGINE_FILE = PATH_RISORSE + "file.png" ;
        public static final String IMMAGINE_CASA = PATH_RISORSE + "casa.png";
        public static final String IMMAGINE_DOCUMENTI = PATH_RISORSE + "documenti.png";
        public static final String IMMAGINE_HD = PATH_RISORSE + "driver.png";
        public static final String IMMAGINE_DESKTOP = PATH_RISORSE + "desktop.png";
        public static final String IMMAGINE_CARTELLA_CORRENTE = PATH_RISORSE + "ricerca-documenti.png";
        public static final String IMMAGINE_FOGLIO_ELETTRONICO = PATH_RISORSE + "foglio-calcolo.png";
        public static final String IMMAGINE_FOTO = PATH_RISORSE + "immagine.png";
        public static final String IMMAGINE_MUSICA = PATH_RISORSE + "musica.png";
        public static final String IMMAGINE_VIDEO = PATH_RISORSE + "video.png";
        public static final String IMMAGINE_VIDEOSCRITTURA = PATH_RISORSE + "video-scrittura.png";
        public static final String IMMAGINE_DATI = PATH_RISORSE + "db.png";
        public static final String IMMAGINE_COMPRESSO = PATH_RISORSE + "compresso.png";
        public static final String IMMAGINE_PDF = PATH_RISORSE + "pdf.png";
        public static final String IMMAGINE_WEB = PATH_RISORSE + "web.png";
        public static final String IMMAGINE_PROGRAMMA = PATH_RISORSE + "app.png";
        public static final String IMMAGINE_PRESENTAZIONE = PATH_RISORSE + "presentazione.png";
        public static final String IMMAGINE_GRAFICA = PATH_RISORSE + "grafica.png";
        
        
    }
    
    private static void congelaFinestra(Scene scena) {

    	registroScene.add(scena);
    	 System.out.println("<- "+registroScene.size());
    }
    
    private static Scene resuscitaFinestra() {
  
    	Scene s =  registroScene.pop();
    	System.out.println("-> "+registroScene.size());
    	return s;
    }
    
    /**
     * Questo metodo permette di visualizzare una finestra al centro dello schermo e dotata della proprietà di trascinamento, 
     * purché  nella sua classe di controllo venga definita una variablie statica usata per memorizza la scena corrente.
     * Questa variabile di classe va implementata prima di chiamare il metodo.
     * 
     * @param controller
     * @param scena 
     */
    public static void caricaFinestra(Object controller,Scene scena){
    	Finestra.caricaFinestra(controller, scena,true);
    }
    
    private static void caricaFinestra(Object controller,Scene scena,boolean salva){
    	if(salva)salvaFinestraCorrente();
        Parent root = scena.getRoot();
        // imposta il trascinamento con il mouse
        root.setOnMousePressed(
                (MouseEvent event) -> {
                    Finestra.Posizione.x = event.getSceneX();
                    Finestra.Posizione.y = event.getSceneY();
                }
        );
        root.setOnMouseDragged(
                (MouseEvent event) -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                    double dx = event.getSceneX()-Finestra.Posizione.x;
                    double dy = event.getSceneY()-Finestra.Posizione.y;
                    Finestra.vistaCorrente.setX(Finestra.vistaCorrente.getX()+dx);
                    Finestra.vistaCorrente.setY(Finestra.vistaCorrente.getY()+dy);
                    
                }
                
        );
        Finestra.vistaCorrente.setScene(scena);
        
        if(!(controller instanceof Application)) {
            Finestra.adattaFinestra();
        }
        Finestra.vistaCorrente.show();
        
    }
    
    /**
     * Ripristina la vecchia scena salvata.
     * 
     * @param controller
     */
    public static void ricaricaFinestra(Object controller){
    	final boolean NON_REGISTRARE_STACK = false;
    	if(finestreIbernate())
            caricaFinestra(controller,resuscitaFinestra(),NON_REGISTRARE_STACK);
        
        else if(finestraPrincipale != null)
            caricaFinestra(controller,finestraPrincipale);
    }
    
    public static void eliminaUltimaFinestraRegistrata() {
    	registroScene.pop();
    }
    
    /**
     * Questo metodo permette di visualizzare una finestra al centro dello schermo e dotata della proprietà di trascinamento, 
     * purché  nella sua classe di controllo venga definita una variablie statica usata per memorizza la scena corrente.
     * Questa variabile di classe va implementata prima di chiamare il metodo, come
     * mostrato nell'esempio seguente.
     * <pre>
     * Esempio:
     * <code>
     * @FXML
     * public void creaNuovoResponsabile(ActionEvent event) {
     *      FinestraResponsabileController.scenaCorrente = Finestra.scenaCorrente();
     *      Finestra.caricaFinestra(this, R.FXML.FINESTRA_RESPONSABILE);
     * }
     * </code>
     * </pre>
     * @param controller
     * @param fileFXML 
     * @return
     */
    public static Scene caricaFinestra(Object controller,String fileFXML){
    	Scene scena = null;
    	try {
            Parent root = FXMLLoader.load(controller.getClass().getResource(fileFXML));
            scena = new Scene(root);
            
            caricaFinestra(controller,scena);
        } catch (IOException ex) {
            Logger.getLogger(Finestra.class.getName()).log(Level.SEVERE, null, ex);
        }
    	return scena;
    }
    
    /**
     * Metodo che fa visualizzare la finestra di conferma.
     * 
     * @param controller        classe corrente in cui viene chiamato il metodo
     * @param domanda           
     * @param ok                classe astratta che gestisce il metodo 'esegui' legato 
     *                          all'evento click sul pulsante Sì 
     */
    public static void finestraConferma(Object controller, String domanda, ConfermaController.Codice ok){
        ConfermaController.conferma(domanda, ok);
        caricaFinestra(controller,R.FINESTRA_CONFERMA);
    }
    /**
     * Metodo che fa visualizzare una finestra dialogo di input con menu a tendina.
     * @param controller
     * @param domanda
     * @param risposte
     * @param modificaInput
     * @param ok 
     */
    public static void finestraInputMenu(Object controller, String domanda,  ArrayList<String> risposte, boolean modificaInput, InputMenuController.Codice ok){
        InputMenuController.input(domanda, risposte.toArray(new String[risposte.size()]),modificaInput, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT_MENU);
    }
    
    public static void finestraTabellaRicerca(Object controller, String titolo, String nomeCampo,  ArrayList<String> inputCampo, ArrayList<String> colonne, ArrayList<Integer> dimColonne, boolean totali, TabellaController.Codice azione, TabellaRicercaController.CodiceRicerca ricerca){
        if(totali)TabellaRicercaController.abilitaTotali();
        TabellaRicercaController.input(titolo,nomeCampo,inputCampo,colonne,dimColonne,azione,ricerca);
        caricaFinestra(controller,R.FINESTRA_TABELLA_RICERCA);
    }
    
    public static void finestraInputColore(Object controller, String domanda,  InputController.Codice ok){
        InputStringaColoreController.input(domanda, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT_COLORE);
    }
    
    public static void finestraInputColore(Object controller, String domanda,  String suggerimento,  InputController.Codice ok){
        InputStringaColoreController.input(domanda, suggerimento, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT_COLORE);
    }
    
    public static void finestraInput(Object controller, String domanda,  InputController.Codice ok){
        InputController.input(domanda, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT);
    }
    
    public static void finestraInput(Object controller, String domanda, String suggerimento,  InputController.Codice ok){
        InputController.input(domanda, suggerimento, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT);
    }
    
    public static void finestraInput(Object controller, String domanda, ArrayList<String> risposte,  InputController.Codice ok){
        InputController.input(domanda,risposte, ok);
        caricaFinestra(controller,R.FINESTRA_INPUT);
    }
    
    public static void finestraSalvaFile(Object controller, String nome, TipoFile estenzione,  FileController.CodiceFile ok){
        FileController.inputFile(nome,estenzione, ok);
        caricaFinestra(controller,R.FINESTRA_FILE);
    }
    
    public static void finestraCaricaFile(Object controller, TipoFile estenzione,  FileController.CodiceFile ok){
        FileController.inputFile(null,estenzione, ok);
        caricaFinestra(controller,R.FINESTRA_FILE);
    }
    
    public static void finestraBarraProgressi(Object controller,  BarraProgressiController.Codice ok){
        BarraProgressiController.azione(ok);
        caricaFinestra(controller,R.FINESTRA_PROGRESSI);
    }
    
   
    /**
     *
     * @param controller
     * @param titolo
     * @param colonne
     * @param dimColonne
     * @param righe
     * @param totali
     * @param azione
     */
    public static void finestraTabella(Object controller, String titolo,ArrayList<String> colonne, ArrayList<Integer> dimColonne, ArrayList<ArrayList<String>> righe, boolean totali, TabellaController.Codice azione){
        if(totali)TabellaRicercaController.abilitaTotali();
        TabellaController.input(titolo,colonne,dimColonne,righe,azione);
        caricaFinestra(controller,R.FINESTRA_TABELLA);
    }
    
    
    
    public static void finestraMultiLista(Object controller, String titolo, String nomeInput,HashMap<String,ArrayList<String>> voci, MultiListaController.Codice azioni){
        MultiListaController.input(titolo,nomeInput,voci,azioni);
        caricaFinestra(controller,R.FINESTRA_MULTI_LISTA);;
    }
    
    public static void finestraListaSempice(Object controller, String titolo, ArrayList<String> voci, ListaController.Codice azioni) {
        ListaController.input(titolo,voci,azioni);
        caricaFinestra(controller,R.FINESTRA_LISTA);
    }
    
    public static void finestraListaOrdinata(Object controller, String titolo, ArrayList<Object[]> voci, ListaController.Codice azioni){
        ListaOrdinataController.inputOrdinato(titolo,voci,azioni);
        caricaFinestra(controller,R.FINESTRA_LISTA_ORDINATA);
    }
    
    public static void finestraListaColorata(Object controller, String titolo, ArrayList<Object[]> voci, ListaController.Codice azioni){
        ListaColoreController.inputColore(titolo,voci,azioni);
        caricaFinestra(controller,R.FINESTRA_LISTA_COLORATA);
    }
    
    public static void finestraAvviso(Object controller, String avviso){
        AvvisoController.avviso(avviso);
        caricaFinestra(controller,R.FINESTRA_AVVISO);
    }
    
    public static void finestraDebug(Object controller, java.lang.Exception errore){
        FinestraDebugController.errore = errore;
        caricaFinestra(controller,R.FINESTRA_DEBUG);
    }
    
    public static Scene scenaCorrente(){
        if(vistaCorrente != null)
            return vistaCorrente.getScene();
        return null;
    }
    
    public static void salvaFinestraCorrente(){
    	Scene scena = scenaCorrente();
        if(scena != null)
        	congelaFinestra(scena);
    }
    
    /**
     * Metodo che accentra la finestra, la rende trasparente e da un effetto animato..
     */
    public static void adattaFinestra(){
        if(vistaCorrente != null){
            Schermo.accentraFinestra(vistaCorrente);
            Scene scena = vistaCorrente.getScene();
            if(scena != null) {
                scena.setFill(null);
                effettoComparsa(scena);
            }
        }
    }
    
    static private void effettoComparsa(Scene scena) {
    	scena.getRoot().scaleYProperty().set(0);
        scena.getRoot().scaleXProperty().set(0);
        scena.getRoot().opacityProperty().set(0.0);
        //scena.getRoot().rotateProperty().set(0);
        final double DURATA = 500.0;
        Timeline azione =new Timeline();
        azione.getKeyFrames().add(
        		new KeyFrame(
        				Duration.millis(DURATA),
        				new KeyValue(scena.getRoot().scaleYProperty(),1,Interpolator.EASE_IN)
        		)
        );
        azione.getKeyFrames().add(
        		new KeyFrame(
        				Duration.millis(DURATA),
        				new KeyValue(scena.getRoot().scaleXProperty(),1,Interpolator.EASE_IN)
        		)
        );
        azione.getKeyFrames().add(
        		new KeyFrame(
        				Duration.millis(DURATA),
        				new KeyValue(scena.getRoot().opacityProperty(),1.0,Interpolator.EASE_IN)
        		)
        );
        /*
        azione.getKeyFrames().add(
        		new KeyFrame(
        				Duration.millis(DURATA),
        				new KeyValue(scena.getRoot().rotateProperty(),360.0,Interpolator.EASE_IN)
        		)
        );
        */
        azione.play();
    }
    
    public static void riduciFinestra() {
        if(Finestra.vistaCorrente != null)
            Finestra.vistaCorrente.setIconified(true);
    }
    
    public static void schermoIntero() {
        if(Finestra.vistaCorrente != null){
            Finestra.vistaCorrente.setMaximized(!Finestra.vistaCorrente.isMaximized());
        }
    }

	private static boolean finestreIbernate() {
		return !registroScene.empty();
	}
    
}
