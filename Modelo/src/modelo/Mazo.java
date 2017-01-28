
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Mazo implements Serializable {

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
    
    public void resetearMazo() {
        mazo = new ArrayList<>();
        for (int i=0;i<4;i++){
                for (int j=0;j<13;j++){
                    mazo.add(new Carta(palos[i],ranks[j]));
                }
                cantidadCartas=52;
        }
    }
    
    public Carta devolverCarta(){
         Random  rnd = new Random();
         int x = (int)(rnd.nextDouble() * cantidadCartas + 0);
         Carta c = mazo.remove(x);
         cantidadCartas--;
         return c;
    }
    
    //Atributos
    private ArrayList<Carta> mazo;
    String[] palos = {"Diamante","Trebol","Corazon","Bastos"};
    String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","As"};
    int cantidadCartas=52;
    
}
