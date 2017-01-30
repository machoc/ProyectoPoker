
package modelo;

import java.io.Serializable;


public class Carta implements Serializable,Comparable<Carta>{

    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    //Atributos
    private String palo;
    private String valor;

    @Override
    public int compareTo(Carta t) {
            if (Integer.parseInt(valor) < Integer.parseInt(t.getValor())) {
                return -1;
            }
            if (Integer.parseInt(valor) > Integer.parseInt(t.getValor())) {
                return 1;
            }
            return 0;
        }
    
    
}
