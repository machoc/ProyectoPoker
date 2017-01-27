package modelo;

import java.io.Serializable;
import java.util.Observable;
import javax.swing.table.TableModel;
import modelo.Jugador;
import modelo.Jugadores;
import modelo.JugadoresTablaModelo;

/* La clase modelo almacena los datos de la aplicacion y informa a las ventanas para que se actualicen

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/

public class Modelo extends Observable implements Serializable{
    //---------------METODOS------------------------
    public Modelo(){
        jugadores = new Jugadores();
        modeloTabla = new JugadoresTablaModelo(jugadores);
    }
    
    public void agregarJugadorMesa(Jugador nuevoJugador){
        jugadores.agregarJugadorMesa(nuevoJugador);
        actualizar(null);
    }
    
    public void cargarJugadores(){
        jugadores.guardarJugadores();
        actualizar(null);
    }
    
    public TableModel modeloTabla(){
        return modeloTabla;
    }
    
     public void pasar(Evento e){
       actualizar("Pasar");
    }
     
     public void noIr(Evento e){
       actualizar("NoIr");
    }
     public void apostar(Evento e){
       actualizar("Apostar");
    }
    
    public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
    }
  
    
    //----------------------------------------------
    
    
    
    //----------------ATRIBUTOS----------------------
    private Jugadores jugadores;
    private JugadoresTablaModelo modeloTabla;
    
    public static final int MAX_JUGADORES = 2;
    
    //----------------------------------------------
}
