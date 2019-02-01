package it.quasar_x7.javafx;

import it.quasar_x7.javafx.Finestra.R;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Dr. Domenico della Peruta
 */
public class TipoFile {
    
    public enum Tipo{
        IMMAGINE,MUSICA,VIDEO,DATI,TESTO,ARCHIVIO,
        FOGLIO_ELETTRONICO,VIDEO_SCRITTURA,PDF,WEB,
        PROGRAMMA,MULTIMEDIALE,NON_DEFINITO,GRAFICA,PRESENTAZIONE
    }
    
    private String nome = null;
    private String estensione = null;
    private Tipo tipo = Tipo.NON_DEFINITO;
    private String risorse = null;

    public TipoFile(String nome,String estensione,Tipo tipo) {
        this.tipo = tipo;
        this.estensione = estensione;
        this.nome = nome;
    }

    public String nome() {
        return nome;
    }

    public String estensione() {
        return estensione.startsWith(".") ? estensione : "."+estensione;
    }

    public Tipo tipo() {
        return tipo;
    }
    
    public String immagine(){
        switch(tipo){
            case FOGLIO_ELETTRONICO: return R.IMMAGINE_FOGLIO_ELETTRONICO;
            case IMMAGINE: return R.IMMAGINE_FOTO;
            case MUSICA: return R.IMMAGINE_MUSICA;
            case VIDEO: return R.IMMAGINE_VIDEO;
            case TESTO: 
            case VIDEO_SCRITTURA: return R.IMMAGINE_VIDEOSCRITTURA;
            case DATI: return R.IMMAGINE_DATI;
            case ARCHIVIO: return R.IMMAGINE_COMPRESSO;
            case PDF: return R.IMMAGINE_PDF;
            case WEB: return R.IMMAGINE_WEB;
            case PROGRAMMA: return R.IMMAGINE_PROGRAMMA;
            case PRESENTAZIONE: return R.IMMAGINE_PRESENTAZIONE;
            case GRAFICA:
            case MULTIMEDIALE: return R.IMMAGINE_GRAFICA;
            default: return R.IMMAGINE_FILE;
        }
    }
    
    @Override
    public String toString(){
        return estensione();
    }
    
    
    public static final TipoFile XLS = new TipoFile("Excel",".xls",Tipo.FOGLIO_ELETTRONICO);
    public static final TipoFile XLSX = new TipoFile("Excel 2007",".xlsx",Tipo.FOGLIO_ELETTRONICO);
    public static final TipoFile ODS = new TipoFile("OpenDocument - foglio di calcolo",".ods",Tipo.FOGLIO_ELETTRONICO);
    
    public static final TipoFile DOC = new TipoFile("Wold",".doc",Tipo.VIDEO_SCRITTURA);
    public static final TipoFile DOCX = new TipoFile("Wold 2007",".docx",Tipo.VIDEO_SCRITTURA);
    public static final TipoFile ODT = new TipoFile("OpenDocument - documento di testo",".odt",Tipo.VIDEO_SCRITTURA);
    public static final TipoFile TXT = new TipoFile("testo ASCII",".txt",Tipo.VIDEO_SCRITTURA);
    
    public static final TipoFile ODP = new TipoFile("OpenDocument - presentazioni",".odp",Tipo.PRESENTAZIONE);
    public static final TipoFile PPT = new TipoFile("Power Point",".ppt",Tipo.PRESENTAZIONE);
    public static final TipoFile PPTX = new TipoFile("Power Point 2007",".pptx",Tipo.PRESENTAZIONE);
    public static final TipoFile PPS = new TipoFile("Power Point - solo presentazione",".pps",Tipo.PRESENTAZIONE);
    public static final TipoFile PPSX = new TipoFile("Power Point 2007 - solo presentazione",".ppsx",Tipo.PRESENTAZIONE);
    
    public static final TipoFile MDB = new TipoFile("Access",".mdb",Tipo.DATI);
    public static final TipoFile ACCDB = new TipoFile("Access 2007",".accdb",Tipo.DATI);
    public static final TipoFile ODB = new TipoFile("OpenDocument - database",".odb",Tipo.DATI);
    
    
    public static final TipoFile PDF = new TipoFile("PDF",".pdf",Tipo.PDF);
    
    public static final TipoFile MP3 = new TipoFile("MP3",".mp3",Tipo.MUSICA);
    public static final TipoFile WAVE = new TipoFile("WAVE",".vaw",Tipo.MUSICA);
    public static final TipoFile OGG = new TipoFile("OGG Media",".ogg",Tipo.MUSICA);
    
    public static final TipoFile EXE = new TipoFile("Eseguibile",".exe",Tipo.PROGRAMMA);
    public static final TipoFile DLL = new TipoFile("Libreria dimamica programma",".dll",Tipo.PROGRAMMA);
    public static final TipoFile JAVA = new TipoFile("Java",".jar",Tipo.PROGRAMMA);
    
    public static final TipoFile ZIP = new TipoFile("Archivio Zip",".zip",Tipo.ARCHIVIO);
    public static final TipoFile RAR = new TipoFile("Archivio Rap",".rar",Tipo.ARCHIVIO);
    public static final TipoFile _7Z = new TipoFile("Archivio 7z",".7z",Tipo.ARCHIVIO);

    public static final TipoFile BITMAP = new TipoFile("Bitmap",".bmp",Tipo.IMMAGINE);
    public static final TipoFile JPG = new TipoFile("JPG",".jpg",Tipo.IMMAGINE);
    public static final TipoFile JPEG = new TipoFile("JPEG",".jpeg",Tipo.IMMAGINE);
    public static final TipoFile PNG = new TipoFile("Portable Network Graphics",".png",Tipo.IMMAGINE);
    public static final TipoFile GIF = new TipoFile("Graphics Interchange Format",".gif",Tipo.IMMAGINE);
    public static final TipoFile ICO = new TipoFile("ICON",".ico",Tipo.IMMAGINE);

    public static final TipoFile MPG = new TipoFile("MPG",".mpg",Tipo.VIDEO);
    public static final TipoFile MPEG = new TipoFile("MPEG",".mpeg",Tipo.VIDEO);
    public static final TipoFile AVI = new TipoFile("Audio Video Interleave",".avi",Tipo.VIDEO);
    public static final TipoFile WMV = new TipoFile("Windows Media Video",".wmv",Tipo.VIDEO);
    public static final TipoFile MOV = new TipoFile("QuickTime",".mov",Tipo.VIDEO);
    public static final TipoFile FLV = new TipoFile("Flash Video",".flv",Tipo.VIDEO);
    public static final TipoFile MKV = new TipoFile("Matroska",".mkv",Tipo.VIDEO);
    public static final TipoFile MP4 = new TipoFile("MP4",".mp4",Tipo.VIDEO);
    public static final TipoFile _3GP = new TipoFile("Mobile",".3gp",Tipo.VIDEO);

    
    
    
    public static TipoFile tipo(File file){
        ArrayList<TipoFile> lista = new  ArrayList<>();
        lista.add(XLS);
        lista.add(XLSX);
        lista.add(ODS);
        lista.add(DOC);
        lista.add(DOCX);
        lista.add(ODT);
        lista.add(TXT);
        lista.add(ODP);
        lista.add(PPT);
        lista.add(PPTX);
        lista.add(PPS);
        lista.add(PPSX);
        lista.add(PDF);
        lista.add(MP3);
        lista.add(WAVE);
        lista.add(OGG);
        lista.add(MDB);
        lista.add(ODB);
        lista.add(ACCDB);
        lista.add(EXE);
        lista.add(DLL);
        lista.add(JAVA);
        lista.add(ZIP);
        lista.add(RAR);
        lista.add(_7Z);
        lista.add(ICO);
        lista.add(PNG);
        lista.add(BITMAP);
        lista.add(JPG);
        lista.add(GIF);
        lista.add(MPG);
        lista.add(AVI);
        lista.add(WMV);
        lista.add(MOV);
        lista.add(FLV);
        lista.add(MKV);
        lista.add(MP4);
        lista.add(_3GP);
        for(TipoFile voce: lista)
            if(file.getName().endsWith(voce.estensione()))
                return voce;
        
        return null;
    }
}
