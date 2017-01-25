
package proyectopoker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import modelo.Evento;
import modelo.Jugador;
import protocolo.Protocolo;
import proyectopoker.vista.VentanaPrincipal;
import proyectopoker.vista.VentanaInicio;


public class Cliente implements Runnable {  
    
    public Cliente(VentanaPrincipal ventana,VentanaInicio inicio){
        jugador = ventana;
        datosJugador=inicio;
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
    
    
    public void escribirObjetoServidor(Object o){
        Evento e = null;
        try {
          e = new Evento(++nEvento," ",o);
          System.out.println("Enviando Objeto a servidor");
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
                 leerMensaje(e);
                 leerTerminarTurno(e);
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
               // escribirObjetoServidor(jugAux);
               // setearModeloTabla();
                //escribirObjetoServidor(jugador.mandarVentTabla());
               
                
                leer();
        
        } catch (ClassNotFoundException ex) {
                    
                } 
            catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void leerMensaje(Evento e){
         String mensaje="";
          try {   
              mensaje=e.getMensaje();
              if(mensaje.equals("Mensaje")){
                 JOptionPane.showMessageDialog(null, (String)e.getInfo());
              }
               mensaje = "";
                }
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
          }
    
       public void leerTerminarTurno(Evento e){
        String terminar="";
        try {                 
               terminar = e.getMensaje();
                
                if(terminar.equals("Terminar") ){
                    
                   jugador.deshabilitarBotones();
                    }
                    terminar = "";
                }    
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    public void leerTurnoCliente(Evento e){
        String turnoCliente="";
        try {                 
                turnoCliente = e.getMensaje();
                
                if(turnoCliente.equals("turno")){
                     JOptionPane.showMessageDialog(null,"Es tu turno");
//                     if (aux){
//                        //escribirObjetoServidor(jugAux);
//                        setearModeloTabla();
//                        escribirObjetoServidor(jugador.mandarVentTabla());
//                        aux =false;
//                     }
                    jugador.habilitarBotones();
                    turnoCliente = "";
                }
                
        } 
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    
    public void pasar(){
        Evento e = null;
        try {
          e = new Evento(Integer.parseInt(nCliente),"Pasar",null);
          System.out.println("Enviando accion a Servidor");
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
    
    
    
     public void apostar(String apuesta){
        Evento e = null;
        try {
          e = new Evento(Integer.parseInt(nCliente),"Apostar",apuesta);
          System.out.println("Enviando accion a Servidor");
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
        }
   }
     
      public void noIr(String apuesta){
        Evento e = null;
        try {
          e = new Evento(Integer.parseInt(nCliente),"NoIr",apuesta);
          System.out.println("Enviando accion a Servidor");
          salida.writeObject(e);
        }
        catch (Exception ex)
        { ex.printStackTrace();
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
    
    public void recibirDatosCliente(Jugador j){
        jugAux=j;
         System.out.println("Jugador cargado correctamente");
    }
    
      private void setearModeloTabla() {
       Evento e =null;
        try {                 
              e = (Evento)entrada.readObject(); 
              jugador.mandarVentTabla().configurarTabla((TableModel)e.getInfo());
        } 
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    //Atributos
     public static boolean aux = true;
    ObjectInputStream entrada;
    ObjectOutputStream salida;
    int nEvento;
    Jugador jugAux;
    String nCliente;
    Socket skt;
    VentanaPrincipal jugador;
    VentanaInicio datosJugador;

  
    
}
