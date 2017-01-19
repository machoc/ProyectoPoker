
package servidor;

import java.net.ServerSocket;
import java.util.ArrayList;
import modelo.Modelo;


public class Servidor {
    
    //Atributos
    private Modelo datos;
    private Thread hiloAtencion;
    private ServerSocket srv;
    private ArrayList<GestorClientes> clientes;
    private int numClientes;
    private static Servidor instancia = null;
    
    
}
