
package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Carta;
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
            System.out.println("Se ha alcanzado el limite de jugadores");
               setNombresJugadores();
            while (true)  { 
                if(aux)
                controlarTurnos(); 
                else{
                 nuevaPartida();
                 
                }
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
     
     public boolean comprobarPasar(){
         for(int i=0;i<3;i++){
             if(datos.getJugador(clientes.get(i).getnCliente()-1).getEstado().equals("Jugando"))
                 return false;
         }
         return true;
     }
     
    
     
     public void controlarTurnos(){
        Evento e = null;
        Evento apuesta= null;
        
        if(comprobarPasar()&& datos.getEstadoMesa().equals("FinRonda")){
           datos.evaluarManos();
           int clienteGanador= datos.buscarGanador();
           
            for(GestorClientes cliente: clientes){
                datos.getJugador(cliente.getnCliente()-1).setDinero(-datos.getJugador(cliente.getnCliente()-1).getCantidadApuesta());
            }
            if(!datos.getJugador(clienteGanador).getEstado().equals("AllInMin")){
           datos.getJugador(clienteGanador).setDinero(datos.getBote());
           }
           else{
                int boteGanador = datos.getJugador(clienteGanador).getCantidadApuesta()*2;
                int botePerdedores =(datos.getBote()-boteGanador)/2;
                 datos.getJugador(clienteGanador).setDinero(boteGanador);
                for(GestorClientes cliente: clientes){
                    if(cliente.getnCliente()-1 != clienteGanador)
                        datos.getJugador(cliente.getnCliente()-1).setDinero(botePerdedores);
            }
           }
            datos.escribirGanadorRonda(clienteGanador);
           
           resetearRonda();
           if(datos.comprobarGanadorPartida()){
               int ganadorPartida = datos.buscarGanadorPartida();
                datos.escribirGanadorPartida(ganadorPartida);
                
                aux=false;
               return;
           }
           
       }
        
         if(datos.getEstadoMesa().equals("InicioRonda")){
           ArrayList<String> manos = new ArrayList<>();
           datos.setEstadoMesa("Flop");
           for(GestorClientes cliente: clientes){
               ArrayList<String> c = new ArrayList<>();
               if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Fuera")){
                   for(int i=0;i<4;i++){
                       c.add("Fuera");
                   }
               }
               else
                     c =cargarManos(cliente.getnCliente());
               
            for(int i=0;i<4;i++){
                manos.add(c.get(i));
            }
           }
           for(GestorClientes cliente: clientes){
           cliente.escribirManos(manos);
           }
       }
        
        
        
       if(comprobarPasar()&& datos.getEstadoMesa().equals("Flop")){
           ArrayList<String> c=cargarFlop();
           datos.setEstadoMesa("Turn");
           for(GestorClientes cliente: clientes){
           if  (!datos.getJugador(cliente.getnCliente()-1).getEstado().equals("NoIr")&& !datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Fuera")){
            datos.setEstadoJugador("Jugando",cliente.getnCliente()-1);   
           }
            cliente.escribirFlop(c);
           }
       }
       
       if(comprobarPasar()&& datos.getEstadoMesa().equals("Turn")){
           ArrayList<String> c=cargarTurn();
           datos.setEstadoMesa("River");
           for(GestorClientes cliente: clientes){
               if  (!datos.getJugador(cliente.getnCliente()-1).getEstado().equals("NoIr")&& !datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Fuera")){
            datos.setEstadoJugador("Jugando",cliente.getnCliente()-1);
               }
            cliente.escribirTurn(c);
       }
       }
       
       if(comprobarPasar()&& datos.getEstadoMesa().equals("River")){
           ArrayList<String> c=cargarRiver();
           datos.setEstadoMesa("FinRonda");
           for(GestorClientes cliente: clientes){
                if  (!datos.getJugador(cliente.getnCliente()-1).getEstado().equals("NoIr")&& !datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Fuera")){
            datos.setEstadoJugador("Jugando",cliente.getnCliente()-1);   
                }
            cliente.escribirRiver(c);
           }
       }
       for(GestorClientes cliente: clientes){
       if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Pasar") && datos.getApuestaMinima()>datos.getJugador(cliente.getnCliente()-1).getCantidadApuesta())
                datos.getJugador(cliente.getnCliente()-1).setEstado("Jugando");
       }
       for(GestorClientes cliente: clientes){
       if (datos.getJugador(cliente.getnCliente()-1).getDinero()<datos.getApuestaMinima()){
                               datos.getJugador(cliente.getnCliente()-1).setEstado("AllInMin");
                               datos.notificarVoltear(cliente.getnCliente());
                }
       }
        
        for(GestorClientes cliente: clientes){
            String estado=datos.getJugador(cliente.getnCliente()-1).getEstado();
            
            
            
            e = new Evento(cliente.getnEvento(),"turno",estado);
            cliente.escribirEntrada(e);
            if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("NoIr")){
               
            }
            
            else if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Pasar")){
     
            }
            
            else if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("AllIn")){
                
            }
            
            else if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("AllInMin")){
     
            }
            
            else if(datos.getJugador(cliente.getnCliente()-1).getEstado().equals("Fuera")){
     
            }
            
            else{
             
                   
            cliente.escribirApuestaMinima(datos.getApuestaMinima());
            cliente.escribirApuestaMaxima(datos.getApuestaMaxima(cliente.getnCliente()));
            
              if(datos.getJugador(cliente.getnCliente()-1).getCantidadApuesta()!=datos.getApuestaMinima()){
                   cliente.escribirDeshabilitarPasar();
               }
            
            apuesta= cliente.leerAccionCliente();
            if(apuesta.getMensaje().equals("Pasar")){
                datos.pasar(datos.getJugador(cliente.getnCliente()-1).getNombre());
                datos.setEstadoJugador("Pasar",cliente.getnCliente()-1);
            }
            else if(apuesta.getMensaje().equals("NoIr")){
                datos.noIr(datos.getJugador(cliente.getnCliente()-1).getNombre());
                datos.setEstadoJugador("NoIr",cliente.getnCliente()-1);
            }
            else if(apuesta.getMensaje().equals("Apostar")){
                int cant=(Integer)apuesta.getInfo();
                if (cant<datos.getApuestaMinima()){
                    datos.getJugador(cliente.getnCliente()-1).setEstado("AllInMin");
                    datos.notificarVoltear(cliente.getnCliente());
                }
                
                if (cant==datos.getJugador(cliente.getnCliente()-1).getDinero()){
                    datos.getJugador(cliente.getnCliente()-1).setEstado("AllIn");
                    
                }
                
                datos.setBote(cant-datos.getJugador(cliente.getnCliente()-1).getCantidadApuesta());
                datos.setApuestaJugador(cliente.getnCliente(),cant);
                datos.setApuestaMinima(cant);  
                datos.apostar(datos.getJugador(cliente.getnCliente()-1).getNombre());
                datos.setEstadoJugador("Jugando",cliente.getnCliente()-1);
                
            } 
            }
            
            ArrayList<String> puntos=escribirApuestas();
            for(GestorClientes client: clientes){
               
           client.escribirApuestas(puntos);
            }
            cliente.escribirTerminarTurno();
    }
        
     }
     
     
     public void resetearRonda(){
         datos.resetearRonda();
     }
     
     
     public ArrayList<String> cargarManos(int nCliente){
         ArrayList<String> cartas = new ArrayList<>();
         for(int i=0;i<2;i++){
             Carta c=datos.getMazo().devolverCarta();
             cartas.add(c.getValor());
             cartas.add(c.getPalo());
             datos.getJugador(nCliente-1).recibirCarta(c);
         }
         return cartas;
     }
     
     public ArrayList<String> cargarFlop(){
         ArrayList<String> cartas = new ArrayList<>();
         for(int i=0;i<3;i++){
             Carta c=datos.getMazo().devolverCarta();
             cartas.add(c.getValor());
             cartas.add(c.getPalo());
             datos.getCartasCentrales().add(c);
         }
         return cartas;
     }
     
      public ArrayList<String> cargarTurn(){
         ArrayList<String> cartas = new ArrayList<>();
         for(int i=0;i<1;i++){
             Carta c=datos.getMazo().devolverCarta();
             cartas.add(c.getValor());
             cartas.add(c.getPalo());
             datos.getCartasCentrales().add(c);
         }
         return cartas;
     }
      
       public ArrayList<String> cargarRiver(){
         ArrayList<String> cartas = new ArrayList<>();
         for(int i=0;i<1;i++){
             Carta c=datos.getMazo().devolverCarta();
             cartas.add(c.getValor());
             cartas.add(c.getPalo());
             datos.getCartasCentrales().add(c);
         }
         return cartas;
     }
       
       public void nuevaPartida(){
           for(GestorClientes cliente: clientes){
            cliente.escribirHabilitarNuevaPartida();
            String nueva =cliente.leerNuevaPartida();
       }
           datos.nuevaPartida();
           aux=true;
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
    private boolean aux= true;
    
    
}
