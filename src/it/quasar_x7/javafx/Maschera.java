package it.quasar_x7.javafx;

/**
 *
 * @author Dr. Domenico della Peruta
 */
public class Maschera{
    public final static String MAC = "__:__:__:__:__:__";
    public final static String ORA_SEMPLICE = "__:__";
    public final static String ORA_COMPLETA = "__:__:__";
    public final static String DATA = "__/__/____";
    public final static String CODICE_FISCALE = "________________";

    public String maschera;
    public char charattere;
    public String input;
    
    public Maschera(String maschera, char carattere, String input){
        this(maschera,carattere);
        this.input = input;
    }
    
    public Maschera(String maschera, char carattere){
        this.maschera = maschera;
        this.maschera = this.maschera.replace('_', carattere);
        this.charattere = carattere;
        switch(maschera){
            case CODICE_FISCALE:
                input = "[A-Za-z0-9]"; break;
            case MAC:
                input = "[a-fA-F0-9]"; break;
            case ORA_SEMPLICE:
            case ORA_COMPLETA:
            case DATA:
                input = "[0-9]"; break;
            default:
                input = "[0-9]"; break;
                
        }
    }
}