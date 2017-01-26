
package modelo;


public class Jugador {

    public Jugador(String nombre, String nickname) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.dinero = 5000.0;
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

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }
    
    public static String[] nombreCampos(){
        return NOMBRE_CAMPOS;
    }
    
     public Object[] toArray(){
        Object[] r = new Object[3];
        r[0] = getNombre();
        r[1] = getNickname();
        r[2] = getDinero();
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
                setDinero(((Double) aValue));
            default:
                throw new IndexOutOfBoundsException();
        }
      }
    
    
    //Atributos
    private String nombre;
    private String nickname;
    private double dinero;
    
    
    private static final String[] NOMBRE_CAMPOS = {"Nombre", "Nickname", "Dinero"};
}
