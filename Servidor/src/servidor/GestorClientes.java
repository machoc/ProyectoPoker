
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import modelo.Evento;


public class GestorClientes implements Observer,Runnable {
    
    public GestorClientes(Servidor nuevoGestor, Socket skt, int num) {
        gestorPrincipal = nuevoGestor;
        direccionCliente = skt.getInetAddress();
        nCliente = num;
        nEvento = 0;
        salida = null;
        entrada = null;
        this.socket = skt;
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada  = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void escribirEntrada(Evento e){
        try{
            salida.writeObject(e);
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void setearModeloTabla(Object modTabla){
        Evento e = new Evento(++nEvento,"",modTabla);
        escribirEntrada(e);
    }
    
    public void leerEntrada(){
        
    }

     public Observer leerVentanaTabla(){
        Evento e = null;
        Observer ventTabla =null;
        try {
              e =  (Evento)entrada.readObject();
              ventTabla= (Observer)e.getInfo();        
              System.out.println("Leyendo Observer Ventana de Cliente"+nCliente);
                           
        } catch (ClassNotFoundException ex) {
                    
                } 
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return ventTabla;
    }
     
     public Evento leerAccionCliente(){
        Evento e = null;
        try {
              e =  (Evento)entrada.readObject();
                  System.out.println("Leyendo Accion de Cliente "+nCliente);
                
         
                           
        } catch (ClassNotFoundException ex) {
                    
                } 
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return e;
    }
     
      public Object leerDatosJugador(){
        Evento e = null;
        try {
              e =  (Evento)entrada.readObject();
              System.out.println("Leyendo Datos Jugador de Cliente"+nCliente);
                           
        } catch (ClassNotFoundException ex) {
                    
                } 
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return e.getInfo();
    }

    @Override
    public void update(Observable mod, Object objeto) {
     
        if(objeto instanceof String){
           switch ((String)objeto){
              
               
               case "Apostar":{
                   String mensaje ="El Jugador Aposto";
                    escribirEntrada(new Evento(nEvento++,"Mensaje",mensaje));
                   
               }
               break;
               
               case "Pasar":{
                   String mensaje ="El  Jugador Paso";
                    escribirEntrada(new Evento(nEvento++,"Mensaje",mensaje));
                
               }
               break;    
                       
                case "NoIr":{
                   String mensaje ="El Jugador no va ir";
                  escribirEntrada(new Evento(nEvento++,"Mensaje",mensaje));
               }
               break; 
                    
           }
        }
           
    
    }

    @Override
    public void run() {
        String numCl = String.valueOf(nCliente);
         Evento e =  new Evento(++nEvento,numCl, null); 
         escribirEntrada(e);
    }
    
    public void cerrarGestorCliente (){
     try {
         salida.close();
         entrada.close();
         socket.close();
        
     }catch (Exception ex){
        System.err.println(ex.getMessage());
     }
    }
    
    public void escribirTerminarTurno(){
        Evento e = null;
        try {
          e = new Evento(++nEvento,"Terminar",null);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
    
     public int getnEvento() {
        return nEvento;
    }    

    public int getnCliente() {
        return nCliente;
    }
    
    
    //Atributos
    public static boolean aux = true;
    private Servidor gestorPrincipal;
    private InetAddress direccionCliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Socket socket;
    private int nCliente;
    private int nEvento;
    
}
