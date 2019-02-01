package it.quasar_x7.javafx;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
* Created by tiwari on 15/6/14.
*/
public class Orologio {
    
    public Orologio(Pane pannello){
        // Create a orologio and a label
        final PannelloOrologio orologio = new PannelloOrologio();
        
        final Label etichettaOrario = new Label(ora(orologio));
        etichettaOrario.setFont(new Font("Arial Black", 26));
        final Label etichettaData = new Label(orologio.stampaData());
        etichettaData.setFont(new Font("Arial Black", 22));
        BorderPane pane = new BorderPane();
        pane.setCenter(orologio);
        pane.setTop(etichettaData);
        pane.setBottom(etichettaOrario);
        BorderPane.setAlignment(etichettaData, Pos.TOP_CENTER);
        BorderPane.setAlignment(etichettaOrario, Pos.TOP_CENTER);
        pannello.getChildren().add(pane);
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    orologio.setCurrentTime();
                    etichettaOrario.setText(ora(orologio));
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }).start();
        
        
        
    }
    
    private String ora(PannelloOrologio orologio){
        return 
                dueCifre(orologio.getHour()) + ":" + 
                dueCifre(orologio.getMinute()) + ":" + 
                dueCifre(orologio.getSecond());
        
    }
    
    private String dueCifre(int i){
        String s ="";
        if(i <= 9){
            s += "0" + i;
        }else{
            s += i;
        }
        return s;
    }
    
    class PannelloOrologio extends Pane {
        private int ora;
        private int minuti;
        private int secondi;
        
        private int giorno,meese,anno;
        private int settimana;
        // Clock pane's width and height
        private double w = 250;
        private double h = 250;
        // Construct a default orologio with current time
        public PannelloOrologio() {
            setCurrentTime();
        }
        // Construct a orologio with specified ora, minuti, secondi
        public PannelloOrologio(int gg, int mm, int aaaa, int h, int min, int sec) {
            this.ora = h;
            this.minuti = min;
            this.secondi = sec;
            this.giorno = gg;
            this.meese = mm;
            this.anno = aaaa;
            
            disegnaOrologio();
        }
        public int getHour() {
            return ora;
        }
        public void setHour(int hour) {
            this.ora = hour;
            disegnaOrologio();
        }
        public int getMinute() {
        return minuti;
        }
        public void setMinute(int minute) {
            this.minuti = minute;
            disegnaOrologio();
        }
        public int getSecond() {
            return secondi;
        }
        public void setSecond(int second) {
            this.secondi = second;
            disegnaOrologio();
        }
        public double getW() {
            return w;
        }
        public void setW(double w) {
            this.w = w;
            disegnaOrologio();
        }
        public double getH() {
            return h;
        }
        public void setH(double h) {
            this.h = h;
            disegnaOrologio();
        }
        public void setCurrentTime() {
            // Construct a calendar for current date and time
            Calendar calendar = new GregorianCalendar();
            // Set current ora, minuti, and secondi
            this.ora = calendar.get(Calendar.HOUR_OF_DAY);
            this.minuti = calendar.get(Calendar.MINUTE);
            this.secondi = calendar.get(Calendar.SECOND);
            this.giorno = calendar.get(Calendar.DAY_OF_MONTH);
            this.meese = calendar.get(Calendar.MONTH)+1;
            this.anno = calendar.get(Calendar.YEAR);
            this.settimana = calendar.get(Calendar.DAY_OF_WEEK);
            disegnaOrologio();
        }
        // Paint the orologio
        protected void disegnaOrologio() {
            // Initialize orologio parameters
            double clockRadius = Math.min(w, h) * 0.8 * 0.5;
            double centerX = w / 2;
            double centerY = h / 2;
            Circle circle = new Circle(centerX, centerY, clockRadius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
            Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
            Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
            Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");
            // Draw secondi hand
            double sLength = clockRadius * 0.8;
            double secondX = centerX + sLength *
            Math.sin(secondi * (2 * Math.PI / 60));
            double secondY = centerY - sLength *
            Math.cos(secondi * (2 * Math.PI / 60));
            Line sLine = new Line(centerX, centerY, secondX, secondY);
            sLine.setStroke(Color.RED);
            // Draw minuti hand
            double mLength = clockRadius * 0.65;
            double xMinute = centerX + mLength *
            Math.sin(minuti * (2 * Math.PI / 60));
            double minuteY = centerY - mLength *
            Math.cos(minuti * (2 * Math.PI / 60));
            Line mLine = new Line(centerX, centerY, xMinute, minuteY);
            mLine.setStroke(Color.BLUE);
            // Draw ora hand
            double hLength = clockRadius * 0.5;
            double hourX = centerX + hLength *
            Math.sin((ora % 12 + minuti / 60.0) * (2 * Math.PI / 12));
            double hourY = centerY - hLength *
            Math.cos((ora % 12 + minuti / 60.0) * (2 * Math.PI / 12));
            Line hLine = new Line(centerX, centerY, hourX, hourY);
            hLine.setStroke(Color.GREEN);
            getChildren().clear();
            getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
        }

        public String stampaData() {
            String giornoSettimana = "";
            switch(this.settimana){
                case 1: giornoSettimana = "Domenica "; break;
                case 2: giornoSettimana = "Lunedì   "; break;
                case 3: giornoSettimana = "Martedì  "; break;
                case 4: giornoSettimana = "Mercoledì"; break;
                case 5: giornoSettimana = "Giovedì  "; break;
                case 6: giornoSettimana = "Venerdì  "; break;
                case 7: giornoSettimana = "Sabato   "; break;
            }
            return giornoSettimana + " "+ dueCifre(giorno) + "/" + dueCifre(meese) + "/" +anno;
        }
    }

   
}