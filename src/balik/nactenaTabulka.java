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
 */
public class nactenaTabulka {
    String jmenoMaterskeDatabaze = "";
    String jmenoTabulky = "default";
    String alias = "";
    List<String> seznamSloupcu = new ArrayList<String>();
    Boolean tabulkaPouzita = false;
    
    public Boolean jePouzivana(){
        return this.tabulkaPouzita;
    }
    
    public String dejJmenoTabulky(){
        return this.jmenoTabulky;
    }
    
    public void nactiTabulku(String adresaSouboruDatab, String jmenoMatDat, String jmenoTab, String novyAlias){
        this.jmenoMaterskeDatabaze = jmenoMatDat;
        this.jmenoTabulky = jmenoTab;
        this.tabulkaPouzita = true;
        this.alias = novyAlias;
        nactiSloupceTabulky(adresaSouboruDatab);
    }
    
    private Connection connect(String adresa) {
        // pripojeni v databazi
        String url = "jdbc:sqlite:" + adresa;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void nactiSloupceTabulky(String adresa){ //na nacteni sloupcu z tabulky bez provedeni select * dotazu
        
        try (Connection conn = this.connect(adresa))
        {
            ResultSet dataSloupcu = conn.getMetaData().getColumns(null, null, this.jmenoTabulky, null); //naplneni dat z tabulky
            this.seznamSloupcu.add("*");
            while (dataSloupcu.next()){  //iterovani po jednotlivych prvcich
               this.seznamSloupcu.add(dataSloupcu.getString("COLUMN_NAME"));  //prekopirovani nazvu sloupce do listu seznamSloupcu
            }
        } catch (SQLException e) {
            System.out.print("Pri pripojovani se k databazi vznikla chyba");
        }
         
    }
    
    public void nastavAlias(String novyAlias){
        this.alias = novyAlias;
    }
    
    public String dejAlias(){
        return this.alias;
    }
    
    public List<String> dejSeznamSloupcu(){
        return this.seznamSloupcu;
    }
}
