
package controlador;

import java.util.Observer;
import javax.swing.table.TableModel;
import modelo.Jugador;
import modelo.Modelo;


public class Controlador {
    
 public Controlador(Modelo mod){
        this.modelo = mod;
    }

    public Controlador() {
        this(new Modelo());
    }
    
    public void registrar(Observer nuevo){
        modelo.addObserver(nuevo);
    }
    
    public void cargarjugadores(){
        modelo.cargarJugadores();
    }
    
    public void agregarJugador(Jugador nuevoJugador){
        modelo.agregarJugadorMesa(nuevoJugador);
    }
    
    public TableModel modeloTabla(){
        return modelo.modeloTabla();
    }
    
    
    //----------------------------------------------
    
    
    
    //----------------ATRIBUTOS----------------------
    
    private Modelo modelo;
    //----------------------------------------------
   
    
}
