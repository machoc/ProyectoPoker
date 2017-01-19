package modelo;
import java.io.Serializable;

public class Evento implements Serializable {

    public Evento(int codigo, String mensaje, Object info) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.info = info;
    }

    @Override
    public String toString() {
        return String.format(
                "Evento: %d (%s) -> %s",
                codigo, mensaje, info);
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    private int codigo;
    private String mensaje;
    private Object info;
}
