
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import modelo.Carta;
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
    
    public void registrarVentanaTabla(Object mod){
        Evento e = new Evento(++nEvento,"Ventana",mod);
        escribirEntrada(e);
    }
    
    public void setearModeloTabla(Object modTabla){
        Evento e = new Evento(++nEvento,"Modelo",modTabla);
        escribirEntrada(e);
    }
    
    public void leerEntrada(){
        
    }

     public Object leerVentanaTabla(){
        Evento e = null;
        try {
              e =  (Evento)entrada.readObject();
      
              System.out.println("Leyendo Observer Ventana de Cliente"+nCliente);
                           
        } catch (ClassNotFoundException ex) {
                    
                } 
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return e.getInfo();
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
     
     public void escribirManos(ArrayList<String> manos){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,"Manos",manos);
          System.out.println("Enviando Manos" );
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
     public void escribirFlop(ArrayList<String> flop){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,"Flop",flop);
          System.out.println("Enviando Flop" );
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
     public void escribirTurn(ArrayList<String> turn){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,"Turn",turn);
          System.out.println("Enviando Turn" );
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
     public void escribirRiver(ArrayList<String> river){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,"River",river);
          System.out.println("Enviando Flop" );
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
    
    public void escribirApuestas(ArrayList<String> apuestas){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,"DatosApuestas",apuestas);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
    
    public void escribirDeshabilitarPasar(){
         Evento e = null;
        try {
     
          e = new Evento(++nEvento,"DeshabilitarPasar",null);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
    }
    
    public void escribirEstado(String estado){
        Evento e = null;
        try {
     
          e = new Evento(++nEvento,estado,null);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     public void escribirApuestaMinima(int apuesta){
        Evento e = null;
        try {
          e = new Evento(++nEvento,"Minimo",apuesta);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
     public void escribirApuestaMaxima(int apuesta){
        Evento e = null;
        try {
          e = new Evento(++nEvento,"Maximo",apuesta);
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
      public void escribirNombresJugadores(ArrayList<String> nombres){
        Evento e = null;
        try {
          e = new Evento(++nEvento,"Nombres",nombres);
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
 
    private Servidor gestorPrincipal;
    private InetAddress direccionCliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Socket socket;
    private int nCliente;
    private int nEvento;
    
}
