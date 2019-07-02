package it.quasar_x7.javafx;

import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Errore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Classe che fornisce metodi statici per la manipolazione del testo interno ai campi
 * di input javafx.
 * 
 * @author Dr Domenico della Peruta
 * @version 1.2.0 ultima modifica 18/09/2016
 */
public class CampoTesto {

    

    private static final HashMap<TextInputControl,ChangeListener<String>> listaProprietaTesto = new HashMap<>();
    private static final HashMap<TextInputControl,ChangeListener<Boolean>> listaProprietaFocus = new HashMap<>();
    private static final HashMap<TextInputControl,EventHandler<KeyEvent>> listaFiltraEventoTastiera = new HashMap<>();
    ;
    
    public class Colore{
        public static final String NERO         = "-fx-text-fill: black;";
        public static final String VERDE        = "-fx-text-fill: yellowgreen;";
        public static final String ARANCIONE    = "-fx-text-fill: orange;";
        public static final String ROSSO        = "-fx-text-fill: red;";
        public static final String BLU          = "-fx-text-fill: blue;";
        public static final String GIALLO       = "-fx-text-fill: yellow;";
        public static final String BIANCO       = "-fx-text-fill: #FFF;";
        public static final String CIANO        = "-fx-text-fill: #0FF;";
        public static final String VIOLA        = "-fx-text-fill: #7E587E;";
        public static final String MAGENTA      = "-fx-text-fill: #FF00FF;";
        
        
    }

   
    
    /**
    * Metodo statico che permette di impostare una maschera input, in un campo associato.
    * Es.:
    * Programma.aggiungiMascheraInput(campo,"****.**",'*');
    * 
    * @param campo
    * @param maschera  maschera input es.: "##/##/####"
     * @param carattereOscurato cioè quel carattere che occupa inizialmente il valore da immettere
     * es.: '#'
     * @param eccezioni 
    */
    public static void aggiungiMascheraInput(final TextField campo, final String maschera,final char carattereOscurato,final String eccezioni) {
    	inizializzaCampo(campo);
    	campo.setText(maschera);
        
        EventHandler<KeyEvent>  limitaLunghezzaInput = (KeyEvent e) -> {
            if (campo.getText().length() >= maschera.length() ) {                    
                 e.consume();
            }
        };
        aggiornaFiltroTastiera(campo,limitaLunghezzaInput);
        
        ChangeListener<Boolean>  posizionaCursoreInizioCampo = (ov, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (campo.isFocused() && !campo.getText().isEmpty()) {
                    campo.positionCaret(0);
                }
            });
        };
        aggiornaProprietaFocus(campo, posizionaCursoreInizioCampo);
        
        campo.setOnKeyPressed((final KeyEvent e) -> {
            if(campo.getText().length() != maschera.length()){
                campo.setText(maschera);
                return;
            }
            int cursore = campo.caretPositionProperty().get();
            if(cursore > -1 && cursore <= maschera.length()){
                final String nuovoTesto = campo.getText();
                
                String carattere,testoDopo,testoPrima;
                boolean limite = true;
                if(eccezioni != null)
                    limite = e.getText().matches(eccezioni);
                if(limite && e.getCode() != KeyCode.RIGHT && e.getCode() != KeyCode.LEFT && e.getCode() != KeyCode.UP && e.getCode() != KeyCode.DOWN && e.getCode() != KeyCode.BACK_SPACE){
                    if(cursore < maschera.length()){
                        if(maschera.charAt(cursore) != carattereOscurato){
                            cursore++;
                        }
                        testoPrima = nuovoTesto.substring(0, cursore);
                        testoDopo = cursore < maschera.length() -1 ? nuovoTesto.substring(cursore+1) : "";
                        campo.setText(testoPrima+testoDopo);
                    }
                }else if(e.getCode() == KeyCode.BACK_SPACE){
                    if(cursore > 0){
                        testoPrima = nuovoTesto.substring(0, cursore-1);
                        carattere = maschera.charAt(cursore-1)+"";
                        testoDopo = nuovoTesto.substring(cursore);
                        campo.setText(testoPrima+carattere+carattere+testoDopo);
                    }
                }else{
                    campo.setText(campo.getText());
                }
                
                campo.positionCaret(cursore);
            }
            
        });
        
    }
    
   public static void inizializzaCampo(final TextField campo) {
	   campo.setText("");
	   EventHandler<KeyEvent> filtro = listaFiltraEventoTastiera.get(campo);
       if(filtro != null){
           campo.removeEventFilter(KeyEvent.KEY_TYPED, filtro);
       }
       
       ChangeListener<Boolean> proprieta = listaProprietaFocus.get(campo);
       if(proprieta != null){
           campo.focusedProperty().removeListener(proprieta);
       }
       campo.setOnKeyPressed(null);
       
       ChangeListener<String> proprieta2 = listaProprietaTesto.get(campo);
       if(proprieta2 != null){
           campo.textProperty().removeListener(proprieta2);
       }
       listaProprietaTesto.put(campo, proprieta2);
   }
    
    public static void aggiungiMascheraInput(final TextField campo, Maschera maschera){
        aggiungiMascheraInput(campo,maschera.maschera,maschera.charattere,maschera.input);
    }
    

    
    public static void aggiungiMascheraInput(final TextField campo, final String maschera, final char carattereOscurato){
        aggiungiMascheraInput(campo,maschera,carattereOscurato,null);
    }
    
    
    
    public static int posizioneCorrenteUltimoCarattere(final String input, final char carattereOscurato){
        int risultato=0;
        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) != carattereOscurato)
                risultato++;
            else
                break;
        }
        return risultato;
    }

    /**
     * Imposta limite di caratteri.
     * 
     * @param campo
     * @param limite 
     */
    public static void aggiungiLimiteTesto(final TextInputControl campo, final int limite) {
        ChangeListener<String> limiteTesto = (final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (campo.getText().length() > limite) {
                String s = campo.getText().substring(0, limite);
                campo.setText(s);
            }
        };
        aggiornaProprietaTesto(campo,limiteTesto);
    }
    
    public static void soloNumeri(final TextField campo, final int limite) {
    	EventHandler<KeyEvent> filtroTastiera = (KeyEvent e) -> {
            if (campo.getText().length() >= limite) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[0-9]")){ 
            }else{
                e.consume();
            }
        };
        aggiornaFiltroTastiera(campo,filtroTastiera);
    }
    
    public static void soloNumeri(final TextField campo, final int limite,final char separatore) {
    	EventHandler<KeyEvent> filtroTastiera = (KeyEvent e) -> {
            if (campo.getText().length() >= limite) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[0-9"+separatore+"]")){ 
                if(campo.getText().contains(""+separatore) && e.getCharacter().matches("["+separatore+"]")){
                    e.consume();
                }else if(campo.getText().length() == 0 && e.getCharacter().matches("["+separatore+"]")){
                    e.consume(); 
                }
            }else{
                e.consume();
            }
        };
        
        aggiornaFiltroTastiera(campo,filtroTastiera);
    }
    
    public static void soloNumeriEsadecimali(TextField campo,final int limite) {
    	EventHandler<KeyEvent> filtroTastiera = (KeyEvent e) -> {
            if (campo.getText().length() >= limite) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[:\\_0-9A-Fa-f]")){ 
            }else{
                e.consume();
            }
        };
        aggiornaFiltroTastiera(campo,filtroTastiera);
    }

    
    
    /**
     * 
     * @param campo
     * @param limite
     * @param eccezioni Es.: " " (spazio); ",;." (punteggiatura)  
     *                  N.B.: nelle eccezioni evitare di mettere il carattere ['] alla fine della stringa
     */
    public static void soloCaratteri(final TextField campo, final int limite,String eccezioni) {
    	EventHandler<KeyEvent> filtroTastiera =  (KeyEvent e) -> {
            if (campo.getText().length() >= limite) {                    
                e.consume();
            }
            if(e.getCharacter().matches("["+eccezioni+"A-Za-z]")){ 
            }else{
                e.consume();
            }
        };
        aggiornaFiltroTastiera(campo,filtroTastiera);
    }
    
    public static void soloCaratteri(final TextField campo, final int limite) {
        soloCaratteri(campo,limite,"");
    }
    
    /**
     * Commuta i caratteri in input in maiuscolo.
     * 
     * @param campo 
     */
    public static void maiuscolo(final TextInputControl campo) {
        ChangeListener<String> cambiamentoTesto = (ov, oldValue, newValue) -> {
            campo.setText(newValue.toUpperCase());
        };
        aggiornaProprietaTesto(campo,cambiamentoTesto);
    }
    /**
     * Commuta i caratteri in input in minuscolo.
     * @param campo 
     */
    public static void minuscolo(final TextInputControl campo) {
        ChangeListener<String> cambiamentoTesto = (ov, oldValue, newValue) -> {
            campo.setText(newValue.toLowerCase());
        };
        aggiornaProprietaTesto(campo,cambiamentoTesto);
    }
    /**
     * Metodo che aggiorna la mappa 'listaProprietaTesto' 
     * @param campo
     * @param cambiamentoTesto 
     */
    private static void aggiornaProprietaTesto(final TextInputControl campo,ChangeListener<String> cambiamentoTesto) {
        ChangeListener<String> vecchiaProprieta = listaProprietaTesto.get(campo);
        if(vecchiaProprieta != null){
            campo.textProperty().removeListener(vecchiaProprieta);
        }
        listaProprietaTesto.put(campo, cambiamentoTesto);
        campo.textProperty().addListener(cambiamentoTesto);
    }
    
    /**
     * Metodo che aggiorna la mappa 'listaProprietaFocus'
     * @param campo
     * @param cambiamentoTesto 
     */
    private static void aggiornaProprietaFocus(final TextInputControl campo,ChangeListener<Boolean> cambiamentoTesto) {
        ChangeListener<Boolean> vecchiaProprieta = listaProprietaFocus.get(campo);
        if(vecchiaProprieta != null){
            campo.focusedProperty().removeListener(vecchiaProprieta);
        }
        listaProprietaFocus.put(campo, cambiamentoTesto);
        campo.focusedProperty().addListener(cambiamentoTesto);
    }
    
    
    private static void aggiornaFiltroTastiera(final TextInputControl campo,EventHandler<KeyEvent> filtroTastiera) {
        EventHandler<KeyEvent> vecchioFiltro = listaFiltraEventoTastiera.get(campo);
        if(vecchioFiltro != null){
            campo.removeEventFilter(KeyEvent.KEY_TYPED, vecchioFiltro);
        }
        listaFiltraEventoTastiera.put(campo, filtroTastiera);
        campo.addEventFilter(KeyEvent.KEY_TYPED, filtroTastiera);
    }
    
    
    /**
     * Commuta i caratteri in input in minuscolo inpostando il primo di una parola a maiuscolo.
     * @param campo 
     */
    public static void primoMaiuscolo(final TextInputControl campo) {
        ChangeListener<String> cambiamentoTesto = (ov, oldValue, newValue) -> {
            String s = "";
            boolean maiuscolo = true;
            for(int i=0; i < newValue.length(); i++){
                String c = newValue.charAt(i) + "";
                s += (maiuscolo) ? c.toUpperCase() : c.toLowerCase();
                maiuscolo = (" ".equals(c) || "\n".equals(c) || "\t".equals(c));
            }
            campo.setText(s);
        };
        aggiornaProprietaTesto(campo,cambiamentoTesto);
    }
    /**
     * Forza l'input al formato dell'indirizzo IP v.6
     * @param campo 
     */
    public static void indirizzoIP(final TextInputControl campo) {
        ChangeListener<String> inputIP = (ov, oldValue, newValue) -> {
            int punti = 0;
            String s = "";
            for(int i=0; i < newValue.length(); i++){
                String c = newValue.charAt(i) + "";
                if(".".equals(c))
                    punti++;
                if(max255(s+c) && punti < 4)
                    s += c;
                else
                    break;
                
            }
            if(!newValue.contains("..") && zeroPrima(newValue))
                campo.setText(s);
            else
                campo.setText(s.substring(0, s.length()-1));
        };
        aggiornaProprietaTesto(campo,inputIP);
    }
    /**
     * Non vi deve essere un valore decimale preceduto dallo zero. 
     * Es.: ".034"
     * @param s
     * @return 
     */
    private static boolean zeroPrima(String s){
        for(int i=0; i < 10; i++){
            if(s.contains(".0"+String.valueOf(i)))
                return false;
        }
        return true;
            
    }
    /**
     * Controlla che tra i punti (.) vi siano valori decimali inferiori a 256.
     * 
     * @param testo
     * @return 
     */
    private static boolean max255(String testo){
        StringTokenizer stringaIP = new StringTokenizer(testo,".");
        while(stringaIP.hasMoreTokens()){
            try{
                int numero = Integer.parseInt(stringaIP.nextToken());
                if(numero < 0 || numero > 255){
                    return false;
                }
            }catch(NumberFormatException e){
                return false;
            }
        }
        return true;
    }
    
    
    public static void indirizzoMAC(final TextInputControl campo) {
    	aggiungiMascheraInput((TextField) campo,Maschera.MAC,'_',"[:\\\\_0-9A-Fa-f]");
    }
    
    /**
     * Imposta una maschera di tipo data al campo. Se vi è un errore il testo diventa rosso.
     * 
     * @param campo
     * @return 
     */
    public static void data(final TextInputControl campo) {
    	CampoTesto.aggiungiMascheraInput((TextField)campo, "**/**/****", '*',"[0-9]");
        
        campo.focusedProperty().addListener(
        		new ChangeListener<Boolean>(){

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(CampoTesto.controllaData((TextField)campo))
							campo.setStyle(CampoTesto.Colore.NERO);
						else
							campo.setStyle(CampoTesto.Colore.ROSSO);
					}
				}
        );
    }
    
    
    
    /**
     * Cancella il testo del campo quando selezionato.
     * 
     * @param campo 
     */
    public static void cancellaAlFocus(final TextInputControl campo) {
       
        ChangeListener<Boolean> cancellaTesto = (ov, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (campo.isFocused() && !campo.getText().isEmpty()) {
                    campo.setText("");
                }
            });
        };
        aggiornaProprietaFocus(campo,cancellaTesto);
    }
    
    public static boolean controllaData(final TextField campo) {
    	try {
	        String formato = campo.getText();
	        int gg = Integer.valueOf(formato.substring(0, 2));
	        int mm = Integer.valueOf(formato.substring(3, 5));
	        int aaaa = Integer.valueOf(formato.substring(6, 10));
	
	        if(gg > 0 && gg <= DataOraria.giorniMese(mm, aaaa) && mm > 0 && mm <= 12 && aaaa >= 1900 && aaaa <= new DataOraria().anno()){
	            campo.setStyle(Colore.VERDE);
	            return true;
	        }else{
	            campo.setStyle(Colore.ARANCIONE);
	            return false;
	        }
    	}catch(NumberFormatException e) {
    		return false;
    	}
        
        
    }
    
    public static void autoCompletamento(TextField campo, Collection<String> elenco){
        final SortedSet<String>voci = new TreeSet<>();
        voci.addAll(elenco);
        ContextMenu vociMenu = new ContextMenu();
        

        ChangeListener<String> autoCompletamento = new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (campo.getText().length() == 0){
                    vociMenu.hide();
                }else{
                    LinkedList<String> listaMenu = new LinkedList<>();
                    listaMenu.addAll(voci.subSet(campo.getText().toLowerCase(), campo.getText().toLowerCase() + Character.MAX_VALUE));
                    listaMenu.addAll(voci.subSet(campo.getText().toUpperCase(), campo.getText().toUpperCase() + Character.MAX_VALUE));
                    
                    if (voci.size() > 0){
                        popolaMenu(listaMenu);
                        if (!vociMenu.isShowing()){
                            vociMenu.show(campo, Side.BOTTOM, 0, 0);
                        }
                    }else{
                        vociMenu.hide();
                    }
                }
            }
            

            private void popolaMenu(LinkedList<String> searchResult) {
                List<CustomMenuItem> menuItems = new LinkedList<>();
                // If you'd like more voci, modify this line.
                int maxEntries = 10;
                int count = Math.min(searchResult.size(), maxEntries);
                for (int i = 0; i < count; i++){
                    final String result = searchResult.get(i);
                    Label entryLabel = new Label(result);
                    CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                    item.setOnAction((ActionEvent actionEvent) -> {
                        campo.setText(result);
                        vociMenu.hide();
                    });
                    menuItems.add(item);
                }
                vociMenu.getItems().clear();
                vociMenu.getItems().addAll(menuItems);

            }
        };
        
        
        aggiornaProprietaTesto(campo,autoCompletamento);
        
        
        ChangeListener<Boolean> nascondiMenu = (ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) -> {
            vociMenu.hide();
        };
        aggiornaProprietaFocus(campo,nascondiMenu);
    }
    
    
    
    public static boolean verificaIndirizzoMAC(final TextField campo){
        boolean valore = regex("^([0-9a-fA-F]?[0-9a-fA-F]:){5}([0-9a-fA-F]?[0-9a-fA-F])$",campo.getText());
        if(!valore){
            campo.setStyle(Colore.ARANCIONE);
        }
        return valore;
    }
    
     public static boolean verificaIndirizzoMAC(final ComboBox campo){
         return verificaIndirizzoMAC(campo.getEditor());
     }
    
    public static boolean verificaIndirizzoIP(final TextField campo){
        boolean valore = regex("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b",campo.getText());
        if(!valore){
            campo.setStyle(Colore.ARANCIONE);
        }
        return valore;
    }
    
    public static boolean verificaIndirizzoIP(final ComboBox campo){
         return verificaIndirizzoIP(campo.getEditor());
     }
    
    private static boolean regex(String regola,String input){
        return Pattern.matches(regola, input);
    }
    
    public static boolean verificaStringaEsadescimale(TextArea campo) {
        boolean valore = regex("^([ \\t\\n\\r\\f\\v]{0,2}[0-9A-Fa-f][0-9A-Fa-f]){0,}$",campo.getText());
        if(!valore){
            campo.setStyle(Colore.ARANCIONE);
        }
        return valore;
    }
    
}
