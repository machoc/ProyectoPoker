
package proyectopoker.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Carta;
import modelo.Jugador;
import proyectopoker.Cliente;



/* Esta clase es donde se ubican las configuraciones de la ventana principal del juego.

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/

public class VentanaPrincipal extends JFrame {

    
    //--------METODOS-----------------------------------------------------
    
     /* Constructor de la clase donde se le configura el nombre a la pantalla
    y se invocan los metodos para configurar la ventana */
    public VentanaPrincipal(VentanaTablaJugadores ventTab) {
        super("POKER"); 
        ventanaTabla=ventTab;
        configuracionInicial();
        agregarComponentes(this.getContentPane());
        agregarEventos();
      
    }
/* Se le configuran las caracteristicas de la pantalla
   */
    private void configuracionInicial(){
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1100,800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);  
        
    }
    /*metodo que sirve para cerrar por completo la aplicacion
    */
    private void cerrarAplicacion(){
        if(JOptionPane.showConfirmDialog(this, "Desea cerrar la aplicacion","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
     public void conectarse(){
          cliente= new Cliente (this,ventanaInicio,ventanaTabla);
           hiloCliente= new Thread( cliente);
           hiloCliente.start();
    }
     
     

     public void mandarDatosCliente(Jugador j){
         cliente.recibirDatosCliente(j);
     }
     
     
     public VentanaTablaJugadores mandarVentTabla(){
         return ventanaTabla;
     }
     
    
/* se agregan y configuran los componentes de la ventana
    */
    private void agregarComponentes(Container c){
        c.setLayout(new BorderLayout());
        
        panelPrincipal= new JPanelConFondo("../vista/imagenes/fondo1.jpg");
            
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        c.add(panelPrincipal,BorderLayout.CENTER);        
        
        panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setPreferredSize(new Dimension(1000,100));
        panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
        
        
        panelCentral=new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setOpaque(false);
        panelPrincipal.add(panelCentral,BorderLayout.CENTER);
        
        
        panelDerecho=new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(200,700));
        panelDerecho.setLayout(new GridLayout(3,1));
        panelPrincipal.add(panelDerecho,BorderLayout.EAST);
        
        panelIzquierdo=new JPanel();
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(200,700));
        panelIzquierdo.setLayout(new GridLayout(3,1));
        panelPrincipal.add(panelIzquierdo,BorderLayout.WEST);
        
        
        menu=new JMenuBar();
        menuArchivo=new JMenu("ARCHIVO");
        menuArchivoSalir=new JMenuItem("SALIR");
        menuNuevaPartida=new JMenuItem("NUEVA PARTIDA");
        menuNuevaPartida.setEnabled(false);
        menuArchivo.add(menuArchivoSalir);
         menuArchivo.add(menuNuevaPartida);
        menu.add(menuArchivo);
        menuPartida=new JMenu("PARTIDA");
        menuPartidaPosiciones=new JMenuItem("VER POSICIONES");
        menuPartida.add(menuPartidaPosiciones);
        menu.add(menuPartida);
        menuAyuda=new JMenu("AYUDA");
        menuAcerca=new JMenuItem("ACERCA");
        menuHelp=new JMenuItem("JUGADAS");
        menuAyuda.add(menuHelp);
        menuAyuda.add(menuAcerca);
        menu.add(menuAyuda);
        
        this.setJMenuBar(menu);
        

  
       JPanel panelCentralSuperior = new JPanel(new GridLayout(1,3));
       panelCentralSuperior.setOpaque(false);

       
       
       
       panelJugador1=new JPanel(new GridLayout(2,1));
       panelJugador1.setOpaque(false);
       panelJugador1Info = new JPanelConFondo("../vista/imagenes/jugadorFondo.png");
       panelJugador1Info .setLayout(new BorderLayout());
       panelJugador1Info.setOpaque(false);
       panelJugador1Info.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
       labNombreJugador1=new JLabel("NOMBRE",JLabel.CENTER);
       labApuestaJugador1=new JLabel("0",JLabel.CENTER);
       panelJugador1Info.add(labApuestaJugador1,BorderLayout.SOUTH);
       panelJugador1Info.add(labNombreJugador1,BorderLayout.CENTER);
       panelJugador1Cartas = new JPanel();
       panelJugador1Cartas.setOpaque(false);
       panelJugador1.add(panelJugador1Info);
       panelJugador1.add(panelJugador1Cartas);
       cartasJug1=new ArrayList<>();
       for(int i = 0; i < 2; i++){
           cartasJug1.add(new JLabel());
           cartasJug1.get(i).setVisible(false);
           panelJugador1Cartas.add( cartasJug1.get(i));
       }
       
       panelJugador2=new JPanel(new GridLayout(2,1));
       panelJugador2.setOpaque(false);
       panelJugador2Info = new JPanelConFondo("../vista/imagenes/jugadorFondo.png");
       panelJugador2Info .setLayout(new BorderLayout());
       panelJugador2Info.setOpaque(false);
       panelJugador2Info.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
       labNombreJugador2=new JLabel("NOMBRE",JLabel.CENTER);
       labApuestaJugador2=new JLabel("0",JLabel.CENTER);
       panelJugador2Info.add(labApuestaJugador2,BorderLayout.SOUTH);
       panelJugador2Info.add(labNombreJugador2,BorderLayout.CENTER);
       panelJugador2Cartas = new JPanel();
       panelJugador2Cartas.setOpaque(false);
       panelJugador2.add(panelJugador2Info);
       panelJugador2.add(panelJugador2Cartas);
       cartasJug2=new ArrayList<>();
       for(int i = 0; i < 2; i++){
           cartasJug2.add(new JLabel());
           cartasJug2.get(i).setVisible(false);
           panelJugador2Cartas.add( cartasJug2.get(i));
       }
       
       panelJugador3=new JPanel(new GridLayout(2,1));
       panelJugador3.setOpaque(false);
       panelJugador3Info = new JPanelConFondo("../vista/imagenes/jugadorFondo.png");
       panelJugador3Info .setLayout(new BorderLayout());
       panelJugador3Info.setOpaque(false);
       panelJugador3Info.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
       labNombreJugador3=new JLabel("NOMBRE",JLabel.CENTER);
       labApuestaJugador3=new JLabel("0",JLabel.CENTER);
       panelJugador3Info.add(labApuestaJugador3,BorderLayout.SOUTH);
       panelJugador3Info.add(labNombreJugador3,BorderLayout.CENTER);
       panelJugador3Cartas = new JPanel();
       panelJugador3Cartas.setOpaque(false);
       panelJugador3.add(panelJugador3Info);
       panelJugador3.add(panelJugador3Cartas);
       cartasJug3=new ArrayList<>();
       for(int i = 0; i < 2; i++){
           cartasJug3.add(new JLabel());
           cartasJug3.get(i).setVisible(false);
           panelJugador3Cartas.add( cartasJug3.get(i));
       }
       
       
        JPanel panelIzquierdoInferior = new JPanel();
        panelIzquierdoInferior.setOpaque(false);
        
        panelIzquierdo.add(panelIzquierdoInferior);
        
       panelCentralSuperior.add(panelJugador1);
       panelCentralSuperior.add(panelJugador2);
       panelCentralSuperior.add(panelJugador3);
       
       JPanel panelDerechoInferior = new JPanel();
       panelDerechoInferior.setOpaque(false);
        
        panelDerecho.add(panelDerechoInferior);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,20);
        labBoteMesa=new JLabel("0");
        labBoteMesa.setFont(font);
        labBoteMesa.setForeground(Color.WHITE);
        panelDerechoInferior.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
       
        JLabel bote=new JLabel("Bote: ");
        bote.setFont(font);
        bote.setForeground(Color.WHITE);
        panelDerechoInferior.add(bote);
        panelDerechoInferior.add(labBoteMesa);
       
       JPanel panelCentralInferior = new JPanel();
       panelCentralInferior.setOpaque(false);
       
       JPanel panelCartasCentrales = new JPanel(new GridLayout(3,1));
       panelCartasCentrales.setOpaque(false);
       JPanel panelCartasCentral = new JPanel();
       panelCartasCentral.setOpaque(false);
       panelCartasCentral.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
       cartasCentrales = new ArrayList<>();
       for(int i = 0; i < 5; i++){
           cartasCentrales.add(new JLabel());
           cartasCentrales.get(i).setVisible(false);
           panelCartasCentral.add( cartasCentrales.get(i));
       }
       panelCartasCentrales.add(panelCentralSuperior);
       panelCartasCentrales.add(panelCartasCentral);
       panelCartasCentrales.add(panelCentralInferior);
       panelCentral.add(panelCartasCentrales,BorderLayout.CENTER);
        
        panelBotones = new JPanel();
        panelBotones.setOpaque(false);
       
        
        btnNoIr = new JButton("NO IR");
        btnNoIr.setForeground(Color.red);
        btnNoIr.setEnabled(false);
        panelBotones.add(btnNoIr);
        
        btnPasar = new JButton("PASAR");
        btnPasar.setForeground(Color.red);
        btnPasar.setEnabled(false);
        panelBotones.add(btnPasar);
        
        btnApostar = new JButton("APOSTAR");
        btnApostar.setForeground(Color.red);
        btnApostar.setEnabled(false);
        panelBotones.add(btnApostar);
        
        sliderApostar = new JSlider();
        panelBotones.add(sliderApostar);
        txtCantidadApuesta= new JTextField(5);
        txtCantidadApuesta.setEditable(false);
         panelBotones.add(txtCantidadApuesta);
        panelInferior.add(panelBotones,BorderLayout.CENTER);
        
        
        
    }
    
    public void cargarFlop(ArrayList<String> cartas){
        int cont=0;
        for(int i =0;i<6;i+=2){
            cartasCentrales.get(cont).setIcon(buscarImagenes(cartas.get(i),cartas.get(i+1)));
            cartasCentrales.get(cont).setVisible(true);
            cont++;
        }
    }
    
    public void cargarTurn(ArrayList<String> cartas){
        for(int i =0;i<1;i++){
            cartasCentrales.get(3).setIcon(buscarImagenes(cartas.get(i),cartas.get(i+1)));
            cartasCentrales.get(3).setVisible(true);
        }
    }
    
      public void cargarManos(ArrayList<String> cartas, String nCliente){
          URL url = getClass().getResource("../vista/imagenes/Cartas/ParteAtras.png");  
          ImageIcon parteAtras = new ImageIcon(url);
           int cont =0;
          if(nCliente.equals("1")){
              cont =0;
          }
          else if(nCliente.equals("2")){
              cont =4;
          }
          else
              cont=8;
          if(!cartas.get(cont).equals("Fuera")){
             for(int i =0;i<2;i++){
            cartasJug1.get(i).setIcon(buscarImagenes(cartas.get(cont),cartas.get(cont+1)));
            cartasJug1.get(i).setVisible(true);
            cont+=2;
             }
          }
          int cont1=0;
          int cont2=0;
          if(nCliente.equals("1")){
              cont1 =4;
              cont2 =8;
          }
          else if(nCliente.equals("2")){
              cont1 =0;
              cont2 =8;
          }
          else{
             cont1 =0;
              cont2 =4;
          }
          if(!cartas.get(cont1).equals("Fuera")){
              for(int i =0;i<2;i++){
             cartasJug2.get(i).setIcon(parteAtras);
             cartasJug2.get(i).setVisible(true);
              }
          }
          if(!cartas.get(cont2).equals("Fuera")){ 
              for(int i =0;i<2;i++){
             cartasJug3.get(i).setIcon(parteAtras);
             cartasJug3.get(i).setVisible(true);
              }
          }
          cartasJugadores=cartas;
        }
        
      
      public void cargarCartasJugadores(String nCliente){
          
          int cont1=0;
          int cont2=0;
          if(nCliente.equals("1")){
              cont1 =4;
              cont2 =8;
          }
          else if(nCliente.equals("2")){
              cont1 =0;
              cont2 =8;
          }
          else{
             cont1 =0;
              cont2 =4;
          }
          if(!cartasJugadores.get(cont1).equals("Fuera")){
              for(int i =0;i<2;i++){
             cartasJug2.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug2.get(i).setVisible(true);
             cont1+=2;
              }
          }
           if(!cartasJugadores.get(cont2).equals("Fuera")){ 
              for(int i =0;i<2;i++){
             cartasJug3.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont2),cartasJugadores.get(cont2+1)));
             cartasJug3.get(i).setVisible(true);
             cont2+=2;
              }
          }
        }
      
      public void voltearCartas(String clienteVoltear, String nCliente){
          
          int cont1=0;
          if(nCliente.equals(clienteVoltear)){
             return;
          }
          else if(nCliente.equals("1")&& clienteVoltear.equals("2")){
              cont1 =4;
               for(int i =0;i<2;i++){
             cartasJug2.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug2.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
          else if(nCliente.equals("1")&& clienteVoltear.equals("3")){
              cont1 =8;
               for(int i =0;i<2;i++){
             cartasJug3.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug3.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
          else if(nCliente.equals("2")&& clienteVoltear.equals("1")){
              cont1 =0;
               for(int i =0;i<2;i++){
             cartasJug2.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug2.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
          else if(nCliente.equals("2")&& clienteVoltear.equals("3")){
              cont1 =8;
               for(int i =0;i<2;i++){
             cartasJug3.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug3.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
          else if(nCliente.equals("3")&& clienteVoltear.equals("1")){
              cont1 =0;
               for(int i =0;i<2;i++){
             cartasJug2.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug2.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
          else if(nCliente.equals("3")&& clienteVoltear.equals("2")){
              cont1 =4;
               for(int i =0;i<2;i++){
             cartasJug3.get(i).setIcon(buscarImagenes(cartasJugadores.get(cont1),cartasJugadores.get(cont1+1)));
             cartasJug3.get(i).setVisible(true);
             cont1+=2;
              }
          }
          
         
        }
        
             
      public void cargarRiver(ArrayList<String> cartas){
        for(int i =0;i<1;i++){
            cartasCentrales.get(4).setIcon(buscarImagenes(cartas.get(i),cartas.get(i+1)));
            cartasCentrales.get(4).setVisible(true);
        }
    }
    
    
    
    public void deshabilitarPasar(){
        btnPasar.setEnabled(false);
    }
    
    public void setMinimo(int x){
        sliderApostar.setMinimum(x);
    }
    
    public void setMaximo(int x){
        sliderApostar.setMaximum(x);
    }
    
    public void setTexto(int x){
        txtCantidadApuesta.setText(Integer.toString(x));
    }
    
    public JSlider getJS(){
        return sliderApostar;
    }
    /* Se agregan y configuran los listeners de los componentes de la ventana
    que responderan a los eventos generados por el usuario*/
    public void agregarEventos(){
         this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                cerrarAplicacion();
            }
        });
         
         menuArchivoSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cerrarAplicacion();
            }});
         
         menuNuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cliente.nuevaPartida();
                menuNuevaPartida.setEnabled(false);
            }});
         
         final ImageIcon imagen = new ImageIcon(getClass().getResource("../vista/imagenes/poker_jugadas.png")); 
        menuHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,imagen,"JUGADAS",JOptionPane.INFORMATION_MESSAGE);
            }
        }); 
        
         menuAcerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Aplicacion creada por:"
                        + "\n     Juan Pablo Campos Leon"
                        + "\n     Luis Castaing Vargas"
                        + "\n\nProgramacion III Verano 2016"
                        + "\nProfesora:"
                        + "\n     Jennifer Fuentes Bustos", "Acerca", JOptionPane.INFORMATION_MESSAGE);
            }}); 
        
         menuPartidaPosiciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ventanaTabla.setVisible(true);
            }});
         
         btnPasar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
                 cliente.pasar();
             }
         });
         
         btnNoIr.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
                cliente.noIr(labApuestaJugador3.getText()); 
             }
         });
         
         btnApostar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
                 cliente.apostar();
             }
         });
         sliderApostar.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                 txtCantidadApuesta.setText(Integer.toString(sliderApostar.getValue()));
             }
         });
    }
    /*hace visible esta ventana*/
    public void mostrar(){
        conectarse();
        this.setVisible(true);
    }
    
    public void ocultarCartasJugadores(){
        for (int i=0;i<2;i++){
            cartasJug1.get(i).setVisible(false);
            cartasJug2.get(i).setVisible(false);
            cartasJug3.get(i).setVisible(false);
        }
    }
    
     public void ocultarCartasMesa(){
        for (int i=0;i<5;i++){
           cartasCentrales.get(i).setVisible(false);
        }
    }
     
    public void habilitarBotones(){
    btnNoIr.setEnabled(true);
    btnPasar.setEnabled(true);
    btnApostar.setEnabled(true);
    }
    
    public void deshabilitarBotones(){
    btnNoIr.setEnabled(false);
    btnPasar.setEnabled(false);
    btnApostar.setEnabled(false);
    }
    
    public ImageIcon buscarImagenes(String valor, String palo){
        String valorAux;
        if(valor.equals("14"))
            valorAux="As";
        else if(valor.equals("11"))
             valorAux="J";
        else if(valor.equals("12"))
             valorAux="Q";
        else if(valor.equals("13"))
             valorAux="K";
        else 
            valorAux=valor;
       return new ImageIcon(getClass().getResource("../vista/imagenes/Cartas/"+valorAux+palo+".png"));  
    }
    
    public JLabel getLabApuesta1(){
        return labApuestaJugador1;
    }
    
     public JLabel getLabApuesta2(){
        return labApuestaJugador2;
    }
     
      public JLabel getLabApuesta3(){
        return labApuestaJugador3;
    }
      
       public JLabel getLabBote(){
        return labBoteMesa;
    }
       
       public JLabel getLabNombre1(){
        return labNombreJugador1;
    }
       
       public JLabel getLabNombre2(){
        return labNombreJugador2;
    }
       
       public JLabel getLabNombre3(){
        return labNombreJugador3;
    }
       
       public void habilitarNuevaPartida(){
       menuNuevaPartida.setEnabled(true);
    }
     
    
    public int getCantApuesta(){
        return Integer.parseInt(txtCantidadApuesta.getText());
    }

    
    //---------------------------------------------------------------------------
    
    
    
    //--------ATRIBUTOS------------------------------------------------------------
   
    private JPanel panelPrincipal;
    private JPanel panelCentral;
    private JPanel panelInferior;
    private JPanel panelDerecho;
    private JPanel panelIzquierdo;
    private JPanel panelBotones;
    private ArrayList<JLabel> cartasCentrales;
    private JPanel panelJugador1;
    private JPanel panelJugador1Info;
    private JLabel labNombreJugador1;
    private JLabel labApuestaJugador1;
    private JPanel panelJugador1Cartas;
    private JPanel panelJugador2;
    private JPanel panelJugador2Info;
    private JLabel labNombreJugador2;
    private JLabel labApuestaJugador2;
    private JPanel panelJugador2Cartas;
    private JPanel panelJugador3;
    private JPanel panelJugador3Info;
    private JLabel labNombreJugador3;
    private JLabel labApuestaJugador3;
    private JPanel panelJugador3Cartas;
    private ArrayList<JLabel> cartasJug1;
    private ArrayList<JLabel> cartasJug2;
    private ArrayList<JLabel> cartasJug3;
    private ArrayList<String> cartasJugadores;
    
    private JMenuBar menu;
    private JMenuItem menuArchivo;
    private JMenuItem menuArchivoSalir;
    private JMenuItem menuPartida;
    private JMenuItem menuPartidaPosiciones;
    private JMenuItem menuAyuda;
    private JMenuItem menuAcerca;
    private JMenuItem menuHelp;
     private JMenuItem menuNuevaPartida;
    
    private JButton btnNoIr;
    private JButton btnPasar;
    private JButton btnApostar;
    private JTextField txtCantidadApuesta;
    private JSlider sliderApostar;
    private JLabel labBoteMesa;
    private VentanaTablaJugadores ventanaTabla;
    private VentanaInicio ventanaInicio;
    

    private Thread hiloCliente;
    private Cliente cliente;
    
    //---------------------------------------------------------------------------
    
}
