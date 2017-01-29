package modelo;

import java.io.Serializable;
import java.util.ArrayList;
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
        puntosMano = 0;
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
    
     public void pasar(Evento e){
       actualizar("Pasar");
    }
     
     public void noIr(Evento e){
       actualizar("NoIr");
    }
     public void apostar(Evento e){
       actualizar("Apostar");
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
     
     /*public void evaluarManos(){
        ArrayList<Integer> manos = new ArrayList<>(10);
        int k;
        int puntos;
        for (int q=0;q<3;q++){
                if (jugadores.recuperarDatos(q).getEstado().equals("Jugando")){
                    manos.get(0).equals(-1); 
                    k = 0;
                    while(manos.get(0).equals(1)){
                        if (manos.get(k)<4){
                            manos.get(k+1).equals(manos.get(k)+1);
                            k++;
                        }

                        else{
                            manos.get(k-1)++;
                            k--;
                        }

                        if (k==0)
                            break;

                        if (k==3){
                            puntos = verMano(manos,q);
                            if (puntos>puntosMano[q]){
                                puntosMano[q] = puntos;
                                for (int x=0;x<3;x++)
                                    mejorMano[q][x] = stack[x+1];
                            }
                        }
                    }

                }
            }
     }*/
     
     public int verMano(ArrayList<Integer> array,int nCliente){
         ArrayList<Carta> mano = new ArrayList<>(5);
            for (int i=1;i<4;i++)
                mano.get(i-1).equals(cartasCentrales.get(array.get(i)));

            for (int i=0;i<2;i++)
                mano.get(i+3).equals(jugadores.recuperarDatos(nCliente).getMano());

            return obtenerJugada(mano);
     }
     
     public int obtenerJugada(ArrayList<Carta> mano){
        int escalera, color, trio, poker, full, par, alta;
        int k;
        escalera = color = trio = poker = full = par = alta = 0;
        k = 0;
        while (k < 4 && mano.get(k).getPalo().equals(mano.get(k + 1).getPalo()))
            k++;
        if (k==4){
            color = 1;
        }
        /* Revisa si gana por escalera*/
        k=0;
        while (k < 4 && mano.get(k).getValor().equals(Integer.parseInt(mano.get(k+1).getValor())-1))
            k++;
        if (k == 4){
            escalera = 1;
        }
        /* revisa si gana por poker */
        for (int i=0;i<2;i++){
            k = i;
            while (k < i+3 && mano.get(k).getValor().equals(mano.get(k+1).getValor()))
                k++;
            if ( k== i+3){
                poker = 1;
                alta = Integer.parseInt(mano.get(i).getValor());
            }
        }
        /*revisa si gana por trio o por fullhouse*/
        if (poker == 0){
            for (int i=0;i<3;i++){
                k = i;
                while (k < i+2 && mano.get(k).getValor().equals(mano.get(k+1).getValor()));
                k++;
                if (k==i+2){
                    trio = 1;
                    alta = Integer.parseInt(mano.get(i).getValor());
                    if (i==0){
                        if (mano.get(3).getValor().equals(mano.get(4).getValor()))
                            full=1;
                    }
                    else if(i==1){
                        if (mano.get(0).getValor().equals(mano.get(4).getValor()))
                            full=1;
                    }
                    else{
                        if (mano.get(0).getValor().equals(mano.get(1).getValor()))
                            full=1;
                    }
                }
            }
        }
        if (escalera==1 && color==1)
            return 170 + Integer.parseInt(mano.get(4).getValor());
        else if(poker==1)
            return 150 + alta;
        else if(full==1)
            return 130 + alta;
        else if(color==1)
            return 110;
        else if(escalera==1)
            return 90 + Integer.parseInt(mano.get(4).getValor());
        else if(trio==1)
            return 70 + alta;
        /* revisa si es pareja*/
        for (k=0;k<4;k++)
            if (mano.get(k).getValor().equals(mano.get(k+1).getValor())){
                par++;
                if (Integer.parseInt(mano.get(k).getValor()) > alta)
                    alta = Integer.parseInt(mano.get(k).getValor());
            }
        switch (par) {
            case 2:
                return 50 + alta;
            case 1:
                return 30 + alta;
            default:
                return Integer.parseInt(mano.get(4).getValor());
        }
     }
    
    public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
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
    private int puntosMano;
    
    public static final int MAX_JUGADORES = 3;
    
    //----------------------------------------------
}
