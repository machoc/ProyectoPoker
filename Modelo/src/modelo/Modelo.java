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
        bote=0;
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
     
     public void setApuestaMinima(int m){
         apuestaMinima=m;
     }
     
     public int getApuestaMaxima(int nCliente){
         return jugadores.recuperarDatos(nCliente-1).getDinero();
     }
     
      public void setBote(int m){
         bote +=m;
     }
      
      public Jugador getJugador(int n){
          return jugadores.recuperarDatos(n);
      }
      
      public void setApuestaJugador(int nCliente, int cant){
         jugadores.recuperarDatos(nCliente-1).setCantidadApuesta(cant);
     }
     
     public int getBote(){
         return bote;
     }
     
     public int getApuestaMinima(){
         return apuestaMinima;
     }
    
    public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
    }
  
    
    //----------------------------------------------
    
    
    
    //----------------ATRIBUTOS----------------------
    private Jugadores jugadores;
    private JugadoresTablaModelo modeloTabla;
    private int apuestaMinima = 50;
    private int bote;
    
    public static final int MAX_JUGADORES = 3;
    
    //----------------------------------------------
}
