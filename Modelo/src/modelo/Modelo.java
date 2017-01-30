package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import javax.swing.table.TableModel;
import modelo.Jugador;
import modelo.Jugadores;
import modelo.JugadoresTablaModelo;

/* La clase modelo almacena los datos de la aplicacion y informa a las ventanas para que se actualicen

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/

public class Modelo extends Observable implements Serializable{
    //---------------METODOS------------------------
    public Modelo(){
        jugadores = new Jugadores();
        modeloTabla = new JugadoresTablaModelo(jugadores);
        bote=0;
        mazo=new Mazo();
        cartasCentrales=new ArrayList<>();
        estadoMesa="InicioRonda";
        puntosManos = new int[3];
    }
    
    public void agregarJugadorMesa(Jugador nuevoJugador){
        jugadores.agregarJugadorMesa(nuevoJugador);
        actualizar(null);
    }
    
    public void cargarJugadores(){
        jugadores.guardarJugadores();
        actualizar(null);
    }
    
    public TableModel modeloTabla(){
        return modeloTabla;
    }
    
     public void pasar(String n){
       actualizar("Pasar"+n);
    }
     
     public void noIr(String n){
       actualizar("NoIr"+n);
    }
     public void apostar(String n){
       actualizar("Apostar"+n);
    }
     
      public void notificarVoltear(int n){
       actualizar("Voltear"+n);
    }
     
     public void escribirGanadorRonda(int ganador){
         actualizar("Ganador"+getJugador(ganador).getNombre());
     }
     
     public void escribirGanadorPartida(int ganador){
         actualizar("GanadorPartida"+getJugador(ganador).getNombre());
     }
     
     public void setEstadoJugador(String estado, int n){
         jugadores.recuperarDatos(n).setEstado(estado);
     }
     
     public void setApuestaMinima(int m){
         apuestaMinima=m;
     }
     
     public int getApuestaMaxima(int nCliente){
         return jugadores.recuperarDatos(nCliente-1).getDinero();
     }
     
      public void setBote(int m){
         bote +=m;
     }
      
      public Jugador getJugador(int n){
          return jugadores.recuperarDatos(n);
      }
      
      public void setApuestaJugador(int nCliente, int cant){
         jugadores.recuperarDatos(nCliente-1).setCantidadApuesta(cant);
     }
      
         public String getEstadoMesa(){
          return estadoMesa;
      }
      
      public void setEstadoMesa(String est){
         estadoMesa=est;
     }
      
      public Mazo getMazo(){
          return mazo;
      }
     
     public int getBote(){
         return bote;
     }
     public ArrayList<Carta> getCartasCentrales(){
         return cartasCentrales;
     }
     public int getApuestaMinima(){
         return apuestaMinima;
     }
     
     public void evaluarManos(){
        int[] manos=new int[10];
        int k;
        int puntos;
        for (int q=0;q<3;q++){
                if (jugadores.recuperarDatos(q).getEstado().equals("Pasar")||jugadores.recuperarDatos(q).getEstado().equals("AllIn")||jugadores.recuperarDatos(q).getEstado().equals("AllInMin")){
                    manos[0]=-1;
                    k = 0;
                    while(true){
                        if (manos[k]<4){
                            manos[k+1] = manos[k] + 1;
                            k++;
                        }

                        else{
                            manos[k-1]++;
                            k--;
                        }

                        if (k==0)
                            break;

                        if (k==3){
                            puntos = verMano(manos,q);
                            if (puntos>puntosManos[q]){
                                puntosManos[q]= puntos;
                              
                            }
                        }
                    }

                }
                else{
                    puntosManos[q]=0;
                }
            }
     }
     
     public int verMano(int[] array,int nCliente){
        Carta[] mano = new Carta[5];
            for (int i=1;i<4;i++)
                mano[i-1]=cartasCentrales.get(array[i]);

            for (int i=0;i<2;i++)
                mano[i+3]=jugadores.recuperarDatos(nCliente).getMano().get(i);

            return obtenerJugada(mano);
     }
     
     public int obtenerJugada(Carta[] mano){
        Arrays.sort(mano);
        int escalera, color, trio, poker, full, par, alta;
        int k;
        escalera = color = trio = poker = full = par = alta = 0;
        k = 0;
        while (k < 4 && mano[k].getPalo().equals(mano[k + 1].getPalo()))
            k++;
        if (k==4){
            color = 1;
        }
        /* Revisa si gana por escalera*/
        k=0;
        while (k < 4 && Integer.parseInt(mano[k].getValor()) == Integer.parseInt(mano[k+1].getValor())-1)
            k++;
        if (k == 4){
            escalera = 1;
        }
        /* revisa si gana por poker */
        for (int i=0;i<2;i++){
            k = i;
            while (k < i+3 && mano[k].getValor().equals(mano[k+1].getValor()))
                k++;
            if ( k== i+3){
                poker = 1;
                alta = Integer.parseInt(mano[i].getValor());
            }
        }
        /*revisa si gana por trio o por fullhouse*/
        if (poker == 0){
            for (int i=0;i<3;i++){
                k = i;
                while (k < i+2 && mano[k].getValor().equals(mano[k+1].getValor()))
                k++;
                if (k==i+2){
                    trio = 1;
                    alta = Integer.parseInt(mano[i].getValor());
                    if (i==0){
                        if (mano[3].getValor().equals(mano[4].getValor()))
                            full=1;
                    }
                    else if(i==1){
                        if (mano[0].getValor().equals(mano[4].getValor()))
                            full=1;
                    }
                    else{
                        if (mano[0].getValor().equals(mano[1].getValor()))
                            full=1;
                    }
                }
            }
        }
        if (escalera==1 && color==1)
            return 170 + Integer.parseInt(mano[4].getValor());
        else if(poker==1)
            return 150 + alta;
        else if(full==1)
            return 130 + alta;
        else if(color==1)
            return 110;
        else if(escalera==1)
            return 90 + Integer.parseInt(mano[4].getValor());
        else if(trio==1)
            return 70 + alta;
        /* revisa si es pareja*/
        for (k=0;k<4;k++)
            if (mano[k].getValor().equals(mano[k+1].getValor())){
                par++;
                if (Integer.parseInt(mano[k].getValor()) > alta)
                    alta = Integer.parseInt(mano[k].getValor());
            }
        switch (par) {
            case 2:
                return 50 + alta;
            case 1:
                return 30 + alta;
            default:
                return Integer.parseInt(mano[4].getValor());
        }
     }
     
     public int buscarGanador(){
         int ganador=0;
         int puntos=puntosManos[0];
         for (int i=1;i<3;i++){
             if(puntosManos[i]>puntos){
                 ganador=i;
                 puntos=puntosManos[i];
             }
         }
         return ganador;
     }
    
    public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
    }
    
    public void resetearRonda(){
        mazo.resetearMazo();
        bote=0;
        apuestaMinima=50;
        cartasCentrales.clear();
        estadoMesa="InicioRonda";
        for(int i=0;i<3;i++){
            jugadores.recuperarDatos(i).setCantidadApuesta(0);
            jugadores.recuperarDatos(i).limpiarMano();
            if( jugadores.recuperarDatos(i).getDinero()>0)
                jugadores.recuperarDatos(i).setEstado("Jugando");
            else{
                 jugadores.recuperarDatos(i).setEstado("Fuera");
                 actualizar("Fuera"+(i+1)+ jugadores.recuperarDatos(i).getNombre());
            }
               
        }
        actualizar("NuevaRonda");
    }
    
    public boolean comprobarGanadorPartida(){
        int cont=0;
        for(int i=0;i<3;i++){
            if(jugadores.recuperarDatos(i).getEstado().equals("Fuera"))
                cont++;
        }
        if(cont==2)
            return true;
        else 
            return false;
    }
    
    public int buscarGanadorPartida(){
        int ganador=0;
        for(int i=0;i<3;i++){
            if(jugadores.recuperarDatos(i).getDinero()!=0)
                ganador=i;
        }
        return ganador;
    }
    
    public void nuevaPartida(){
        mazo.resetearMazo();
        bote=0;
        apuestaMinima=50;
        cartasCentrales.clear();
        estadoMesa="InicioRonda";
        for(int i=0;i<3;i++){
            jugadores.recuperarDatos(i).setCantidadApuesta(0);
            jugadores.recuperarDatos(i).limpiarMano();
            jugadores.recuperarDatos(i).setEstado("Jugando");
            jugadores.recuperarDatos(i).setDinero(5000);
            actualizar("NuevaPartida");
    }
    }
  
    
     
    
    
    //----------------------------------------------
    
    
    
    //----------------ATRIBUTOS----------------------
    private Jugadores jugadores;
    private Mazo mazo;
    private JugadoresTablaModelo modeloTabla;
    private int apuestaMinima = 50;
    private int bote;
    private ArrayList<Carta> cartasCentrales;
    private String estadoMesa;
    private int[] puntosManos;
    
    public static final int MAX_JUGADORES = 3;
    
    //----------------------------------------------
}
