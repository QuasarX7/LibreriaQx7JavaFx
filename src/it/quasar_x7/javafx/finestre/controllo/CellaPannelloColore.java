package it.quasar_x7.javafx.finestre.controllo;

/**
 *
 * @author DR. DOMENICO DELLA PERUTA
 */
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class CellaPannelloColore<S> extends TableCell<S, Color> {
    private final Pane pannelloColore;

    public CellaPannelloColore() {
        pannelloColore = new Pane();
    }


    @Override
    protected void updateItem(Color item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            pannelloColore.setBackground(new Background(new BackgroundFill(item, null, null)));
            setGraphic(pannelloColore);
        }
    }

    public static <S> Callback<TableColumn<S, Color>, TableCell<S, Color>> disegna() {
        return column -> new CellaPannelloColore<>();
    }
}
