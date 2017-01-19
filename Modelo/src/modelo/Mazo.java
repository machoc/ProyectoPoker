
package modelo;

import java.util.ArrayList;


public class Mazo {

    public Mazo() {
        mazo = new ArrayList<>();
        for (int i=0;i<4;i++){
                for (int j=0;j<13;j++){
                    mazo.add(new Carta(palos[i],ranks[j]));
                }
        }
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(ArrayList<Carta> mazo) {
        this.mazo = mazo;
    }
    
    //Atributos
    private ArrayList<Carta> mazo;
    String[] palos = {"Diamante","Trebol","Corazon","Bastos"};
    String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","As"};
    
}
