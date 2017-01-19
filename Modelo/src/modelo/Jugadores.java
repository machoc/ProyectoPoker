
package modelo;

import modelo.Jugador;
import java.util.ArrayList;


public class Jugadores {

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
        agregarJugadorMesa(new Jugador("Luis Alejandro","Macho",5000.00));
        agregarJugadorMesa(new Jugador("Pablo","PabCL",5000.00));
        agregarJugadorMesa(new Jugador("Jennifer","Jenny",5000.00));
    }
    
    
    
    //Atributos
    private ArrayList<Jugador> jugadores;
    
}
