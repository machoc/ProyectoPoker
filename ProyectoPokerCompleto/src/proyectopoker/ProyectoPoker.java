
package proyectopoker;


import proyectopoker.vista.VentanaInicio;
import proyectopoker.vista.VentanaPrincipal;
import proyectopoker.vista.VentanaTablaJugadores;

/*En esta clase se crean las ventanas que seran necesarias par que el usuario interactue con la aplicacion
y se ubica el metodo main del proyecto.

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/

public class ProyectoPoker {

   
    public static void main(String[] args) {

        VentanaTablaJugadores ventanaTabla = new VentanaTablaJugadores();
        VentanaPrincipal ventana = new VentanaPrincipal(ventanaTabla);
        VentanaInicio inicio = new VentanaInicio(ventana);
        inicio.mostrar();
    }
    
}
