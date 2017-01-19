
package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Modelo;
import protocolo.Protocolo;


public class Servidor {
    
    public Servidor(Modelo nuevoModelo) {
        datos = nuevoModelo;
        clientes = new ArrayList<>();
        numClientes = 0;
    }

    public Servidor() {
        this(new Modelo());
    }
    
    public void iniciar(){
        hiloAtencion = new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        hiloAtencion.start();
    }
    
    public void registrar(Observer nuevoObservador) {
        datos.addObserver(nuevoObservador);
    }
    
     public void atenderClientes() {        
        System.out.println("Atendiendo clientes..");
        try {
            srv = new ServerSocket(Protocolo.PUERTO_POR_DEFECTO);
            srv.setSoTimeout(1000);
        
            while (numClientes < Modelo.MAX_JUGADORES && hiloAtencion == Thread.currentThread()) {
                try {
                    Socket skt = srv.accept();
                    numClientes++;
                    GestorClientes nuevoCliente = new GestorClientes(this, skt,numClientes);
                    clientes.add(nuevoCliente);           
                    System.out.println("Agregando: " + nuevoCliente + "Cliente" + numClientes);
                    registrar(nuevoCliente);
                    Thread hiloCliente= new  Thread  (nuevoCliente);
                    hiloCliente.start();
                    
                        
                } catch (SocketTimeoutException e) {
                    // No se ha conectado ningún cliente.
                    // Se mantiene esperando conexiones..
                    
                }             
            }
            JOptionPane.showMessageDialog(null,"El máximo de jugadores es 3");
            while (true)  { 
                controlarTurnos(); 
            }
        } catch (Exception e) {            
            System.err.println(e.getMessage());
        }
    }
     
     public void controlarTurnos(){
         
     }
     
      public void eliminarClientes() {
        for(GestorClientes cliente: clientes){
           clientes.remove(cliente);
           datos.deleteObserver(cliente);
        }
    }
    
    public void retirarCliente(GestorClientes cliente) {
        clientes.remove(cliente);
        datos.deleteObserver(cliente);
        System.out.println("Cliente eliminado: " + cliente);
    }
    
    public void cerrarServidor(){            
           try{
               srv.close();
               for(GestorClientes cliente: clientes){
                   if(cliente!=null){
                       //cliente.cerrarGestorCliente();
                   }
                }
                
           }
           catch (Exception ex)
           {   
               System.err.println(ex.getMessage());
           }
    }
    
    public static Servidor obtenerInstancia(){
        if(instancia==null){
            instancia = new Servidor();
        }
        return instancia;
    }
    
    //Atributos
    private Modelo datos;
    private Thread hiloAtencion;
    private ServerSocket srv;
    private ArrayList<GestorClientes> clientes;
    private int numClientes;
    private static Servidor instancia = null;
    
    
}
