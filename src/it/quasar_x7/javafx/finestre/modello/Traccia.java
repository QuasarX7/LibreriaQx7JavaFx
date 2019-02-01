package it.quasar_x7.javafx.finestre.modello;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dr Domenico della Peruta 
 */
public class Traccia{
    
    protected IntegerProperty riga;
    protected StringProperty file;
    protected StringProperty metodo;
    
    
    public Traccia(int riga, String file, String metodo) {
        this.riga   = new SimpleIntegerProperty(riga);
        this.file   = new SimpleStringProperty(file);
        this.metodo = new SimpleStringProperty(metodo);
    }

    public int getRiga() {
        return riga.get();
    }
    
    public String getFile() {
        return file.get();
    }
    
    public String getMetodo() {
        return metodo.get();
    }
    
    @Override
    public String toString() {
        return String.format("[%d]\t%s\t%s\n",riga.get(),file.get(),metodo.get());
    }

    
}
