/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balik;

/**
 *
 * @author NewNotebook
 */
public class sloupecVDotazu {
    String zdrojovaTabulka = "";
    String alias = "";
    String nazevSloupce = "";
    String updatovaciHodnota = "";
    
    sloupecVDotazu(String zdroj, String nazevSl, String ali, String updHodnota){
        this.zdrojovaTabulka = zdroj;
        this.nazevSloupce = nazevSl;
        this.alias = ali;
        this.updatovaciHodnota = updHodnota;
    }
    
    public void nastavAlias(String ali){
        this.alias = ali;
    }
    
    public String dejAlias(){
        return this.alias;
    }
    
    public String dejSloupecSAliasem(){
        if (this.alias == "") {
            return this.nazevSloupce;
        }
        else{
            return (this.alias + "." + this.nazevSloupce);
        }
    }
    
    public String dejSloupecSUpdatem(){
        return (this.alias + "." + this.nazevSloupce + " = " + this.updatovaciHodnota);
    }
    
    public String dejSloupecBezAliasu(){
        return this.nazevSloupce;
    }
    
    public String dejZdrojovouTabulku(){
        return this.zdrojovaTabulka;
    }
    
}
