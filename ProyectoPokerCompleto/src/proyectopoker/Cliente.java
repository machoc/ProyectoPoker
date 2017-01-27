
package proyectopoker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import modelo.Evento;
import modelo.Jugador;
import modelo.Modelo;
import protocolo.Protocolo;
import proyectopoker.vista.VentanaPrincipal;
import proyectopoker.vista.VentanaInicio;
import proyectopoker.vista.VentanaTablaJugadores;


public class Cliente implements Runnable {  
    
    public Cliente(VentanaPrincipal ventana,VentanaInicio inicio, VentanaTablaJugadores pos){
        jugador = ventana;
        datosJugador=inicio;
        posiciones=pos;
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
          e = new Evento(++nEvento,"objeto",o);
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
                 setearModeloTabla(e);
                 registrarVentanaTabla(e);
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
                escribirObjetoServidor(jugAux);
              
               
                
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
                 mensaje = "";
              }
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
                   terminar = "";
                    }
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
    
    private void registrarVentanaTabla(Evento e) {
      String mod ="";
        try {                 
              mod = e.getMensaje();
              if (mod.equals("Ventana")&&e.getInfo()!= null){
             Modelo m = (Modelo)e.getInfo();
             m.addObserver(posiciones);
             System.out.println("Registrando Ventana"); 
             mod ="";
              }
        } 
            catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
      private void setearModeloTabla(Evento e) {
      String mod ="";
        try {                 
              mod = e.getMensaje();
              if (mod.equals("Modelo")){
              jugador.mandarVentTabla().configurarTabla((TableModel)e.getInfo());
              System.out.println("Configurando modelo tabla"); 
              mod ="";
              }
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
    private VentanaPrincipal jugador;
    private VentanaInicio datosJugador;
    private VentanaTablaJugadores posiciones;

  
    
}
