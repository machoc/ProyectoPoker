
package proyectopoker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import modelo.Evento;
import protocolo.Protocolo;
import proyectopoker.vista.VentanaPrincipal;


public class Cliente implements Runnable {  
    
    public Cliente(VentanaPrincipal ventana){
        jugador = ventana;
        nEvento = 0;
    }
    
    @Override
    public void run() {
        iniciar();        
        leerNumCliente();        
        
    }
    
    public void iniciar(){
        try {
            skt = new Socket("localhost", Protocolo.PUERTO_POR_DEFECTO);
            entrada = new ObjectInputStream(skt.getInputStream());
            salida = new ObjectOutputStream(skt.getOutputStream());
             
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String getnCliente() {
        return nCliente;
    }
    
  /*  public void asignarPuntos(int puntos){
        String p = String.valueOf(puntos);
        escribirMensajeServidor(p);
    }*/
    
    public void escribirMensajeServidor(String puntos){
        Evento e = null;
        try {
          e = new Evento(++nEvento,puntos,null);
          System.out.println("Puntos hacia servidor" + e.getMensaje());
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
    public void leer(){
        try {
            Evento e=null;
            while (true){
                 System.out.println("Iniciamos:");
                 e = (Evento)entrada.readObject(); 
                 leerTurnoCliente(e);
                 leerPuntosCliente(e);
                 Thread.sleep (15);
            }
        }
        catch (Exception ex){    
            ex.printStackTrace();    
        }
    }
    public void leerNumCliente(){
        String numCliente="";        
        try {          
                
                Evento e = (Evento)entrada.readObject(); 
                numCliente = e.getMensaje();
                nCliente = numCliente;
                System.out.println(numCliente);
                
                if(numCliente.equals("3")){
                    jugador.obtenerEtqJugador1().setText("Jugador# 3:");
                    jugador.obtenerEtqJugador2().setText("Jugador# 1:");
                    jugador.obtenerEtqJugador3().setText("Jugador# 2:");
                    
                }
                
                if(numCliente.equals("2")){
                    jugador.obtenerEtqJugador1().setText("Jugador# 2:");
                    jugador.obtenerEtqJugador2().setText("Jugador# 1:");
                    jugador.obtenerEtqJugador3().setText("Jugador# 3:");
                    
                }        
                leer();
        
        } catch (ClassNotFoundException ex) {
                    
                } 
            catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void leerTurnoCliente(Evento e){
        String turnoCliente="";
        try {                 
                turnoCliente = e.getMensaje();
                
                if(turnoCliente.equals("turno")){
                    jugador.obtenerBtnJugar().setEnabled(true);                    
                    turnoCliente = "";
                }
                
        } 
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
   /* public void leerPuntosCliente(Evento e){
        String prefijo="";
        String puntosCliente="";
        String numCliente="";
        try {       
             
                
                prefijo=e.getMensaje().substring(0,1);
                numCliente = e.getMensaje().substring(1,2);
                puntosCliente = e.getMensaje().substring(2,e.getMensaje().length());                
                
                if(prefijo.equals("p")){
                    System.out.println("Numero de cliente consola" +nCliente);
                    System.out.println("Numero de cliente" +numCliente);
                    if(!(numCliente.equals(nCliente)) && (nCliente.equals("1"))){    
                        if(numCliente.equals("2")){
                            jugador.obtenerValorEtqJugador2().setText(puntosCliente);
                        }
                        if(numCliente.equals("3")){
                            jugador.obtenerValorEtqJugador3().setText(puntosCliente);
                        }
                    }
                    
                    if(!(numCliente.equals(nCliente)) && (nCliente.equals("2"))){    
                        if(numCliente.equals("1")){
                            jugador.obtenerValorEtqJugador2().setText(puntosCliente);
                        }
                        if(numCliente.equals("3")){
                            jugador.obtenerValorEtqJugador3().setText(puntosCliente);
                        }
                    }
                    
                    if(!(numCliente.equals(nCliente)) && (nCliente.equals("3"))){    
                        if(numCliente.equals("1")){
                            jugador.obtenerValorEtqJugador2().setText(puntosCliente);
                        }
                        if(numCliente.equals("2")){
                            jugador.obtenerValorEtqJugador3().setText(puntosCliente);
                        }
                    }
                                     
                }                        
        
        }
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }*/
    
    public void cerrarCliente (){
     try {
         salida.close();
         entrada.close();
         skt.close();
       
     }catch (Exception ex){
        System.err.println(ex.getMessage());
     }
    }
    
    //Atributos
    ObjectInputStream entrada;
    ObjectOutputStream salida;
    int nEvento;
    String nCliente;
    Socket skt;
    VentanaPrincipal jugador;
    
}
