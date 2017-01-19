
package proyectopoker.control;

import java.util.Observer;
import javax.swing.table.TableModel;
import proyectopoker.modelo.Jugador;
import proyectopoker.modelo.Modelo;

/* La clase control funcionara para enlazar las ventanas con el modelo.

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/
public class Control {
    
    //---------------METODOS------------------------
    
    public Control(Modelo mod){
        this.modelo = mod;
    }

    public Control() {
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
