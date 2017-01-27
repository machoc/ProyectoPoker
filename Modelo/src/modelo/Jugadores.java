
package modelo;

import java.io.Serializable;
import modelo.Jugador;
import java.util.ArrayList;


public class Jugadores implements Serializable {

    public Jugadores() {
        jugadores = new ArrayList<>();
    }
    
    public void agregarJugadorMesa(Jugador jugadorNuevo){
        jugadores.add(jugadorNuevo);
    }
    
    public Jugador recuperarDatos(int p){
        return jugadores.get(p);
    }
    
    public int cantidadJugadores(){
        return jugadores.size();
    }
    
    public void guardarJugadores(){
        agregarJugadorMesa(new Jugador("Luis Alejandro","Macho"));
        agregarJugadorMesa(new Jugador("Pablo","PabCL"));
        agregarJugadorMesa(new Jugador("Jennifer","Jenny"));
    }
    
    
    
    //Atributos
    private ArrayList<Jugador> jugadores;
    
}
