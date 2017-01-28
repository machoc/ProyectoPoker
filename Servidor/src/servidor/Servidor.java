
package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Evento;
import modelo.Jugador;
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
                atenderClientes();
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
                    agregarJugador(nuevoCliente.leerDatosJugador());  
                    nuevoCliente.setearModeloTabla(datos.modeloTabla());
                    nuevoCliente.registrarVentanaTabla(datos);
                 
                  
                  
                        
                } catch (SocketTimeoutException e) {
                    // No se ha conectado ningún cliente.
                    // Se mantiene esperando conexiones..
                    
                }             
            }
            JOptionPane.showMessageDialog(null,"El máximo de jugadores es 3");
               setNombresJugadores();
            while (true)  { 
                controlarTurnos(); 
            }
        } catch (Exception e) {            
            System.err.println(e.getMessage());
        }
    }
     
     public void setNombresJugadores(){
         ArrayList<String> nombres=new ArrayList<>();
         for(int i=0; i<3;i++)
             nombres.add(datos.getJugador(i).getNombre());
         
          for(GestorClientes cliente: clientes){
              cliente.escribirNombresJugadores(nombres);
              
          }
         
        
     }
     
     
     public ArrayList<String> escribirApuestas(){
         ArrayList<String> puntos=new ArrayList<>();
         for(int i=0; i<3;i++)
             puntos.add(String.valueOf(datos.getJugador(i).getCantidadApuesta()));
         
         puntos.add(String.valueOf(datos.getBote()));
          
        return puntos;
     }
    
     
     public void controlarTurnos(){
        Evento e = null;
        Evento apuesta= null;
        boolean aux=true;
        for(GestorClientes cliente: clientes){
            e = new Evento(cliente.getnEvento(),"turno",null);
            cliente.escribirEntrada(e);
            cliente.escribirApuestaMinima(datos.getApuestaMinima());
            cliente.escribirApuestaMaxima(datos.getApuestaMaxima(cliente.getnCliente()));
            
            
            
            apuesta= cliente.leerAccionCliente();
            if(apuesta.getMensaje().equals("Pasar")){
                datos.pasar(apuesta);
            }
            else if(apuesta.getMensaje().equals("NoIr")){
                datos.noIr(apuesta);
            }
            else if(apuesta.getMensaje().equals("Apostar")){
                int cant=(Integer)apuesta.getInfo();
              
                datos.setApuestaJugador(cliente.getnCliente(),cant);
                datos.setBote(cant);
                datos.setApuestaMinima(datos.getApuestaMinima()+cant);  
                datos.apostar(apuesta);
            } 
            ArrayList<String> puntos=escribirApuestas();
            for(GestorClientes client: clientes){
               
           client.escribirApuestas(puntos);
            }
            cliente.escribirTerminarTurno();
    }
        
     }
     
     public void agregarJugador(Object jug){
         datos.agregarJugadorMesa((Jugador)jug);
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
                       cliente.cerrarGestorCliente();
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
