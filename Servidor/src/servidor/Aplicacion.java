
package servidor;

import javax.swing.SwingUtilities;


public class Aplicacion {

    
    public static void main(String[] args) {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.iniciar();
    }
    
    public void iniciar(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mostrarInterfaz();
            }
        });
    }
    
    public void mostrarInterfaz(){
        Servidor gestorPrincipal = new Servidor();
        gestorPrincipal.iniciar();
    }
    
    
}
