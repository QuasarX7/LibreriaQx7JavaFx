package it.quasar_x7.javafx.finestre.modello;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Dr Domenico della Peruta 
 */
public class VoceOrdinataLista extends VoceSempliceLista implements Comparable<VoceOrdinataLista> {
    
    protected IntegerProperty id;
    
    public VoceOrdinataLista(int id, String voce) {
        super(voce);
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId() {
        return id.get();
    }
    
    @Override
    public String toString() {
        return String.format("[%d]\t%s",id.get(),voce.get());
    }

    
    
    @Override
    public int compareTo(VoceOrdinataLista o) {
        return new Integer(getId()).compareTo(o.getId());
    }
    
    /**
     * Estrai i valori contenuti nella riga di tipo "[nr] 'stringa'"
     * @param riga
     * @return 
     */
    public static Object[] estraiInfo(String riga){
        int parA = riga.indexOf("[")+1;
        int parC = riga.indexOf("]");
        if(parA < parC && parA == 1){
            try{
                int numero = Integer.parseInt(riga.substring(parA, parC));
                int sp = riga.indexOf("\t")+1;
                if(sp > parC && sp < riga.length()){
                    String stringa = riga.substring(sp);
                    return new Object[]{numero,stringa};
                }
            }catch(NumberFormatException e){
            }
        }
        return null;
    }
    
}
