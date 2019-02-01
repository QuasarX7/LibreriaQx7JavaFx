package it.quasar_x7.javafx;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Dr della Peruta
 * @version 1.0.0 ultima modifica 06/03/2016
 */
public class Schermo {

    /**
     * Metodo statico che permette di accentrare nello schermo la vista della finestra.
     *
     * @param vista
     */
    public static void accentraFinestra(Stage vista) {
        if (vista != null) {
            Rectangle2D schermo = Screen.getPrimary().getVisualBounds();
            vista.setX((schermo.getWidth() - vista.getWidth()) / 2);
            vista.setY((schermo.getHeight() - vista.getHeight()) / 4);
        }
    }
    
}
