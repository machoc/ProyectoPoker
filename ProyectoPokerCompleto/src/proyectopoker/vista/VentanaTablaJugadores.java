
package proyectopoker.vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import proyectopoker.control.Control;

/* En esta clase se administra los diferentes jugadores de la partida en donde se
muestra el nombre, nickname y el dinero que tiene cada jugador 

Creadores:Luis Alejandro Castaing
Pablo Campos

Revisor:Jennifer Fuentes


*/


public class VentanaTablaJugadores extends JFrame implements Observer{
    /* Constructor de la clase donde se le configura el nombre a la pantalla
    y se invocan los metodos de configurar */
    public VentanaTablaJugadores(Control cont) {
        super("Informacion de jugadores");
        control=cont;
        this.configurarPantalla();
        this.agregarComponentes(this.getContentPane());
        control.registrar(this);
    }
    
    /* Se le configuran las caracteristicas a la pantalla y se le realiza
    un accionListener para ocultar la pantalla cuando esta se cierra*/
    private void configurarPantalla(){
        this.setSize(900,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                ocultar();
            }
        });
        
    }
    /* Se agregan los paneles para luego configurar el JTable para mostrar la informacion
    de los jugadores */
    private void agregarComponentes(Container c){
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        
        panelTabla = new JPanel(new FlowLayout());
        
         tablaJugadores = new JTable();
         scrollTabla = new JScrollPane(tablaJugadores,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tablaJugadores.setFillsViewportHeight(true);
        configurarTabla(tablaJugadores);
        //tablaJugadores = new JTable(tabla, nombreColumnas);
        
        //scrollTabla = new JScrollPane(tablaJugadores);
        
        panelTabla.add(scrollTabla,FlowLayout.LEFT);
        panelPrincipal.add(panelTabla,BorderLayout.CENTER);
        
        c.add(panelPrincipal);  
        
    }
    public void ocultar(){
        this.setVisible(false);
    }
    
    public void mostrar(){
        this.setVisible(true);
    }
    
    public void configurarTabla(JTable tabla){
        //En este llamado se asocia el modelo de la tabla
        // a la tabla (JTable)
        tabla.setModel(control.modeloTabla());
        tabla.setAutoCreateRowSorter(false);
        tabla.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                
            }
        });
    }
    

   
    //Atributos
    private JPanel panelPrincipal;
    private JPanel panelTabla;
    
    private JTable tablaJugadores;
    
    private JScrollPane scrollTabla;
    private Control control;

    @Override
    public void update(Observable o, Object o1) {
       tablaJugadores.repaint();
    }
    
}
