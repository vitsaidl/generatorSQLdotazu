/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balik;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NewNotebook
 */
public class dotaz {
    char typDotazu = 'S';  //pripustne S, U a D
    List<String> podminkyVDotazu = new ArrayList<String>();
    String databaze = "";
    String tabulka = "";
    List<sloupecVDotazu> sloupce2 = new ArrayList<sloupecVDotazu>();
    List<String> pouziteTabulky = new ArrayList<String>();
    List<String> aliasPouziteTabulky = new ArrayList<String>();
    List<String> seznamSpojeni = new ArrayList<String>();
    List<String> typySpojeni = new ArrayList<String>();
    List<String> podminkySpojeni = new ArrayList<String>();
    
    public void vymazVse(){
        podminkyVDotazu.clear();
        sloupce2.clear();
        pouziteTabulky.clear();
        aliasPouziteTabulky.clear();
        seznamSpojeni.clear();
        typySpojeni.clear();
        podminkySpojeni.clear();
    }
    
    public void zmenTypDotazu(char zmena){
        this.typDotazu = zmena;
    }
    
    public char dejTypDotazu(){
        return this.typDotazu;
    }
    
    public void nastavJmenoTabulky(String jmenoTab){
        this.tabulka = jmenoTab;
    }
    
    public void pridejDoSeznamuSpojeni(String spojeni){
        this.seznamSpojeni.add(spojeni);
    }
    
    public void vymazSeznamSpojeni(){
        this.seznamSpojeni.clear();
    }
        
    public List<String> dejSeznamSpojeni(){
        return this.seznamSpojeni;
    }
    
    public void pridejTypSpojeni(String spojeni){
        this.typySpojeni.add(spojeni);
    }
    
    public void pridejPodminkuSpojeni(String podminka){
        this.podminkySpojeni.add(podminka);
    }
    
    public void nastavTypSpojeni(String spojeni, int poradi){
    //zmeni prvek v listu typy spojeni sedici na poradi-tem miste na spojeni
        this.typySpojeni.set(poradi, spojeni);
    }
    
    public void nastavPodminkuSpojeni(String podminka, int poradi){
        this.podminkySpojeni.set(poradi, podminka);
    }
    
    
    public List<String> dejTypySpojeni(){
        return this.typySpojeni;
    }
    
    public List<String> dejPodminkySpojeni(){
        return this.podminkySpojeni;
    }
    
    public void smazVsechnyTypySpojeni(){
        this.typySpojeni.clear();
    }
    
    public void smazVsechnyPodminkySpojeni(){
        this.podminkySpojeni.clear();
    }
            
    public void nastavPouziteTabulky(){
        pouziteTabulky.clear();
        aliasPouziteTabulky.clear();
        for(sloupecVDotazu element : sloupce2){                    
            if (!this.aliasPouziteTabulky.contains(element.dejAlias())){  
                pouziteTabulky.add(element.dejZdrojovouTabulku());
                this.aliasPouziteTabulky.add(element.dejAlias());
            }
        }
    }
    
    public String stringPouzitychTabulek(List<nactenaTabulka> predanyListTabulek2){
        //pouziva se pro generovani veci za FROM a pred GORUP BY; separovane od hlavniho tela vytvareni dotazu je kvuli prehlednosti
        String stringTabulek = "";
        Boolean prvniTabulka = true;
        int indexSpojeni = 0;
        String spojeniTyp = "DEFAULT";
        String spojeniPodm = "DEFAULT2";
        
        for(nactenaTabulka element : predanyListTabulek2){                    
            if(prvniTabulka == true){
                prvniTabulka = false;
                stringTabulek = stringTabulek + /*this.databaze + "." +*/ element.dejJmenoTabulky() + " " + element.dejAlias();
            }
            else{
                spojeniTyp = this.typySpojeni.get(indexSpojeni);
                spojeniPodm = " ON " + this.podminkySpojeni.get(indexSpojeni);
                indexSpojeni = indexSpojeni + 1;
                stringTabulek = stringTabulek + "\n " + spojeniTyp +" "+ /*this.databaze + "." +*/ element.dejJmenoTabulky() + " "+ element.dejAlias() + spojeniPodm;
            }
        }
        return stringTabulek;
     
    }
    
    public void nastavJmenoDatabaze(String jmenoDat){
        this.databaze = jmenoDat;
    }
    
    public void pridejSloupecDoDotazu(String jmenoSloupce, String aliasTab, String jmenoTabulky, String updHodnota){
        sloupecVDotazu sloupecek = new sloupecVDotazu(jmenoTabulky, jmenoSloupce, aliasTab, updHodnota);
        this.sloupce2.add(sloupecek);
        
    }
    
    public void odeberSloupecDotazu(String jmenoSloupce){
        int indexSloupce = 0;                                       //promenna na urceni indexu sloupce, ktery se vyradi; je to obezlicka, nebot odebirani v ramci for loopu blblo
        for(sloupecVDotazu element : sloupce2) {                    //for cyklus pres vsechny elementy (objekty typu sloupecVDotazu) v listu sloupce2
            if (element.dejSloupecSAliasem().equals(jmenoSloupce)){  //== nefungovalo, protoze porovnavalo neco docela jinaho - zda jde o stejnoy objekt (to ne), zatimco equals porovnava obsah
                indexSloupce = sloupce2.indexOf(element);           //zjisteni idnexu sloupce, ktery pujde pryc
            }
        }
        this.sloupce2.remove(indexSloupce);                         //odebrani sloupce
    }
    
    public void pridejPodminkuVDotazu(String podminka){
        this.podminkyVDotazu.add(podminka);
    }
    
    public void odeberPodminkuVDotazu(String podminka){
        this.podminkyVDotazu.remove(podminka);
    }
    
    public String vygenerujTextDotazu(List<nactenaTabulka> predanyListTabulek){
       String zacatekDotazu = "";
       String sloupce = "";
       String podminky = "";
       String zdroj = "";
       String vyslednyDotaz = "";
       nastavPouziteTabulky();
       
       
       if (this.typDotazu == 'S'){
           zacatekDotazu = "SELECT";
       }
       else if(this.typDotazu == 'U'){
           zacatekDotazu = "UPDATE " + this.databaze + "." + predanyListTabulek.get(0).dejJmenoTabulky() + " " + predanyListTabulek.get(0).dejAlias() + " SET ";
       }
       else if(this.typDotazu == 'D'){
           zacatekDotazu = "DELETE ";
       }
       
       if ( (this.typDotazu == 'S') || (this.typDotazu == 'D')){
           Boolean prvniSloupecVDotazu = true;                                  //na kontrolu, zda jde o prvni sloupec ci ne; kvuli carkam mezi nazvy sloupcu
           for(sloupecVDotazu element : sloupce2) {                             //pro kazdy element (objekt typu sloupecVDotazu) v listu sloupce2
                if(prvniSloupecVDotazu == true){                                //pokud se jedna o prvni sloupec v dotazu
                    prvniSloupecVDotazu = false;                                //nastavi se boolean pro dalsi iterace fo cyklu na false
                    sloupce = sloupce + element.dejSloupecSAliasem();           //a ke stringku sloupce se prida string alias.jmenoSloupce
                }
                else {
                sloupce = sloupce + ",\n " + element.dejSloupecSAliasem();      //jinak to same, ale s carkou a newLine znakem
                }
            } 
        }
       
       else if (this.typDotazu == 'U'){
           Boolean prvniSloupecVDotazu = true;                                  //na kontrolu, zda jde o prvni sloupec ci ne; kvuli carkam mezi nazvy sloupcu
           for(sloupecVDotazu element : sloupce2) {                             //pro kazdy element (objekt typu sloupecVDotazu) v listu sloupce2
                if(prvniSloupecVDotazu == true){                                //pokud se jedna o prvni sloupec v dotazu
                    prvniSloupecVDotazu = false;                                //nastavi se boolean pro dalsi iterace fo cyklu na false
                    sloupce = sloupce + element.dejSloupecSUpdatem();           //a ke stringku sloupce se prida string alias.jmenoSloupce
                }
                else {
                sloupce = sloupce + ",\n " + element.dejSloupecSUpdatem();      //jinak to same, ale s carkou a newLine znakem
                }
            } 
          
       }
       
       for (int i = 0; i < this.podminkyVDotazu.size(); i++ ) {  //na pridani sloupcu tabulky do comboboxu
           if (i == 0) {
              podminky = podminky + this.podminkyVDotazu.get(i);
           }
           else {
              podminky = podminky + " AND " + this.podminkyVDotazu.get(i); 
           }
       }
       
       zdroj = stringPouzitychTabulek(predanyListTabulek);
       if ( (podminkyVDotazu.size() == 0) && (this.typDotazu == 'S') ){
           vyslednyDotaz = zacatekDotazu + "\n " + sloupce + "\nFROM "+ zdroj + ";\n\n";
       }
       else if ( (podminkyVDotazu.size() == 0) && (this.typDotazu == 'U') ){
           vyslednyDotaz = zacatekDotazu + "\n " + sloupce + ";\n\n";
       }
       else if ( (podminkyVDotazu.size() == 0) && (this.typDotazu == 'D') ){
           vyslednyDotaz = zacatekDotazu + "\nFROM " + zdroj + ";\n\n";
       }
       else if (this.typDotazu == 'S'){
           vyslednyDotaz = zacatekDotazu + "\n " + sloupce + "\nFROM "+ zdroj + "\nWHERE " + podminky + ";\n\n";
       }
       else if (this.typDotazu == 'U'){
           vyslednyDotaz = zacatekDotazu + "\n " + sloupce + "\nWHERE " + podminky + ";\n\n";
       }
       else if (this.typDotazu == 'D'){
           vyslednyDotaz = zacatekDotazu + "\nFROM " + zdroj + "\nWHERE " + podminky + ";\n\n";
       }
       return vyslednyDotaz;
    }
    
    
}
