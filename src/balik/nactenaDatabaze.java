/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NewNotebook
 * Tato trida se pouziva primarne na nacteni tabulek v databazi
 */
public class nactenaDatabaze {
    List<String> tabulkyVDatabazi = new ArrayList<String>();
    List<String> aliasyTabulekVDatabazi = new ArrayList<String>();
    String kratkeJmenoDatab = "";
    String dlouheJmenoDatab = "";
 
    private Connection connect(String adresaDatabaze) {
        // pripojeni v databazi
        this.dlouheJmenoDatab = adresaDatabaze;
        String url = "jdbc:sqlite:" + adresaDatabaze;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void nactiDatabaze(String jmenoDatabaze){ //na nacteni sloupcu z tabulky bez provedeni select * dotazu
        
        try (Connection conn = this.connect(jmenoDatabaze))
        {
            ResultSet tabVDat = conn.getMetaData().getTables(null, null, "%", null); //naplneni dat z tabulky
            while (tabVDat.next()){  //iterovani po jednotlivych prvcich
               this.tabulkyVDatabazi.add(tabVDat.getString(3));  //prekopirovani nazvu sloupce do listu seznamSloupcu
               this.aliasyTabulekVDatabazi.add("");             //nastavi se alias "", aby kazda tabulka mela svuj vlastni (traba i prazdny) alias
               this.kratkeJmenoDatab= jmenoDatabaze.substring(jmenoDatabaze.lastIndexOf('\\') + 1);
            }
        } catch (SQLException e) {
            System.out.print("Pri pripojovani se k databazi vznikla chyba");
        }
    }
    
    public List<String> dejTabulkyVDatabazi(){
            return this.tabulkyVDatabazi;
    }
} 
