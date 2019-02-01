package it.quasar_x7.javafx.finestre.modello;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dr. Domenico della Peruta
 */
public class VoceSempliceLista {
    protected final StringProperty voce;

    public VoceSempliceLista(String voce) {
        this.voce = new SimpleStringProperty(voce);
    }

    
    public String getVoce() {
        return voce.get();
    }

    @Override
    public String toString() {
        return voce.get();
    }
}
