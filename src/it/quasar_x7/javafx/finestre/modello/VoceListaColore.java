package it.quasar_x7.javafx.finestre.modello;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Dr Domenico della Peruta 
 */
public class VoceListaColore extends VoceSempliceLista{
    
    protected ObjectProperty<Color> colore;
    /**
     * 
     * @param codifica      Es.: "[#FF0000] Stringa di errore"
     */
    public VoceListaColore(String codifica) {
        super(
                estraiInfo(codifica) != null ? (String)estraiInfo(codifica)[1] : codifica
        );
        if(estraiInfo(codifica) != null)
            this.colore = new SimpleObjectProperty<>((Color)estraiInfo(codifica)[0]);
        
    }
    public VoceListaColore(String voce,Color colore) {
        super(voce);
        this.colore = new SimpleObjectProperty<>(colore);
        
    }

    public Color getColore() {
        return colore.get();
    }
    
    @Override
    public String toString() {
        return String.format("[%s]\t%s",coloreWeb(colore.get()),voce.get());
    }

    
    /**
     * Metodo di classe che permette di codificare un colore nel formato WEB.
     * 
     * @param colore
     * @return Se colore è null restituisce #000000 (nero)
     */
    public static String coloreWeb(Color colore){
        if(colore != null)
            return String.format(
                    "#%02X%02X%02X",
                    (int)(colore.getRed()*255),
                    (int)(colore.getGreen()*255),
                    (int)(colore.getBlue()*255)
            );
        else
            return "#000000";
    }
    
    

    /**
     * Estrai i valori contenuti nella riga di tipo "[#colore_web] 'stringa'"
     * @param riga
     * @return Object[2] {Color, String}
     */
    public static Object[] estraiInfo(String riga){
        int parA = riga.indexOf("[")+1;
        int parC = riga.indexOf("]");
        if(parA < parC && parA == 1){
            try{
                Color colore = Color.web(riga.substring(parA, parC));
                int sp = riga.indexOf("\t")+1;
                if(sp > parC && sp < riga.length()){
                    String stringa = riga.substring(sp);
                    return new Object[]{colore,stringa};
                }
            }catch(IllegalArgumentException e){
            }
        }
        return null;
    }
    
}
