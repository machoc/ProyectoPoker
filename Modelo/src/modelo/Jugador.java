
package modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class Jugador implements Serializable{

    public Jugador(String nombre, String nickname) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.dinero = 5000;
        mano=new ArrayList<>();
        cantidadApuesta=0;
        estado="Jugando";
    }

    public void  limpiarMano(){
        mano.clear();
    }
    public void recibirCarta(Carta c){
        mano.add(c);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero += dinero;
    }
     public String getEstado() {
       return  estado;
    }
    
     public void setEstado(String est) {
        estado=est;
    }
    public int getCantidadApuesta() {
        return cantidadApuesta;
    }

    public void setCantidadApuesta(int cant) { 
        this.cantidadApuesta = cant ;
    }
    
    
    
    public static String[] nombreCampos(){
        return NOMBRE_CAMPOS;
    }
    
     public Object[] toArray(){
        Object[] r = new Object[4];
        r[0] = getNombre();
        r[1] = getNickname();
        r[2] = getDinero();
        r[3] = getMano();
        return r;
    }
     
      public void fijarAtributo(Object aValue, int columnIndex){
        switch(columnIndex){
            case 0:
                setNombre(aValue.toString());
                break;
            case 1:
                setNickname(aValue.toString());
                break;
            case 2:
                setDinero(((Integer) aValue));
            case 3:
                recibirCarta(((Carta) aValue));
            default:
                throw new IndexOutOfBoundsException();
        }
      }
     public ArrayList<Carta> getMano() {
            return mano;
    }
    
    //Atributos
      
      
        private ArrayList<Carta>  mano;
        private String nombre;
        private String nickname;
        private int dinero;
        private int cantidadApuesta;
        private String estado;


        private static final String[] NOMBRE_CAMPOS = {"Nombre", "Nickname", "Dinero"};

   
}
