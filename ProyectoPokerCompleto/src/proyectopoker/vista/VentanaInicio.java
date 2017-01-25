
package proyectopoker.vista;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Jugador;


/* Esta clase es la que iniciara el juego, el jugador necesitara ingresar su nombre
su nickname y la cantidad de dinero con la que quiere ingresar a la mesa

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/


public class VentanaInicio extends JFrame  {

    /* El constructor de la clase en donde se invocan los metodos para ajustar la pantalla
    agregar componentes y tambien el metodo donde se agregan los eventos*/
    
    public VentanaInicio(VentanaPrincipal principal) {
        super("Iniciar");
        ventPrincipal=principal;
        this.ajustarComponentes(this.getContentPane());
        this.ajustarPantalla();
        this.agregarEventos();
    }
    
    /* En este metodo se agregan los diferentes componentes a la ventana
    se administra los paneles y se agregan a al contenedor*/
    
    private void ajustarComponentes(Container c){
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(8, 16, 8, 16);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        lblNombre = new JLabel("Nombre");
        panelPrincipal.add(lblNombre,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNombre = new JTextField(10);
        panelPrincipal.add(txtNombre,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        lblNickname = new JLabel("Nickname");
        panelPrincipal.add(lblNickname,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtNickname = new JTextField(10);
        panelPrincipal.add(txtNickname,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        lblDinero= new JLabel("Dinero");
        panelPrincipal.add(lblDinero,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        txtDinero = new JTextField(10);
        panelPrincipal.add(txtDinero,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = 2;
        btnIngresarMesa = new JButton("Ingresar Mesa");
        panelPrincipal.add(btnIngresarMesa,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = 2;
        btnCancelar = new JButton("Cancelar");
        panelPrincipal.add(btnCancelar,gbc);
        
        c.add(panelPrincipal);
        
    }
    
    /* Se ajustan las diferentes caracteristicas a la pantalla */
    
    private void ajustarPantalla(){
        this.setSize(500,200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        
    }
    
    /* pone la pantalla visible */
    public void mostrar(){
        this.setVisible(true);
    }
    /* La pantalla desaparece para continuar con el juego */
     public void ocultar(){
        this.setVisible(false);
    }  
     
     /* En este metodo se agregaran todos los eventos que 
     van a tener los diferentes componentes de la pantalla */
     private void agregarEventos() {
         
          this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                cerrarAplicacion();
            }
        });
          
       btnCancelar.addActionListener(new ActionListener(){

           @Override
           public void actionPerformed(ActionEvent ae) {
             cerrarAplicacion();
           }
       });
       
       btnIngresarMesa.addActionListener(new ActionListener(){

           @Override
           public void actionPerformed(ActionEvent ae) {
             String nombre;
             String nickname ;
             Double dinero ;
             if(txtNombre.getText() !="")
                 nombre = txtNombre.getText();
             else
                 nombre="Anonimo";
             if(txtNickname.getText()!="")
             nickname = txtNickname.getText();
             else 
                 nickname="Desconocido";
           
              Jugador jugAux = new Jugador(nombre, nickname);
     //        gestorPrincipal.agregarJugador(nuevoJugador);
             ocultar();
             ventPrincipal.mostrar();
            ventPrincipal.mandarDatosCliente(jugAux);
             
             
             
             
             
           
           }
       });
    }
    /* El metodo en donde se configura si se quiere cerrar la aplicacion o no */ 
    private void cerrarAplicacion(){
        if(JOptionPane.showConfirmDialog(this, "Desea cerrar la aplicacion","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    
    
    //Atributos
   
    
    private JPanel panelPrincipal;
    
    private JLabel lblNombre;
    private JLabel lblNickname;
    private JLabel lblDinero;
    
    private JTextField txtNombre;
    private JTextField txtNickname;
    private JTextField txtDinero;
    
    private JButton btnIngresarMesa;
    private JButton btnCancelar;
    private VentanaPrincipal ventPrincipal;

  
  
}
