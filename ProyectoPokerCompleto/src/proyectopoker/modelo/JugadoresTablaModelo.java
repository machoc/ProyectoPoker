
package proyectopoker.modelo;

import javax.swing.table.AbstractTableModel;


public class JugadoresTablaModelo extends AbstractTableModel {
 public JugadoresTablaModelo (Jugadores jug){
        this.jugadores = jug;
    }
    
    
    @Override
    public int getRowCount() { 
        int filas = jugadores.cantidadJugadores();
        return filas;
    }

    @Override
    public int getColumnCount() {
        return Jugador.nombreCampos().length;
      
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return jugadores.recuperarDatos(rowIndex).toArray()[columnIndex];
    }
    
    @Override 
    public String getColumnName(int columnIndex){
        return Jugador.nombreCampos()[columnIndex];
    }
    
    
    @Override 
    public void setValueAt(Object valor, int rowIndex, int columnIndex){
        //Actualiza el atributo de la persona
        jugadores.recuperarDatos(rowIndex).fijarAtributo(valor, columnIndex);
        //Actualiza la celda del modelo de la tabla
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
       return false;
    }
    
    @Override
    //Permite conocer la clase asociada a la columna
    public Class getColumnClass(int columnIndex){
        return getValueAt(0, columnIndex).getClass();
    }
    
    
    //Atributo
    private Jugadores jugadores;
    
}