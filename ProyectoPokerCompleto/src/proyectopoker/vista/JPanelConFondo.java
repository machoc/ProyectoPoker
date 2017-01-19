
package proyectopoker.vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/* El objetivo de esta clse es configurar un fondo a cualquier JPanel del proyecto

Creadores: Luis Alejandro Castaing.
Pablo Campos

Revisor: Jennifer Fuentes


*/
 
public class JPanelConFondo extends JPanel {
 
    private Image imagen;
 
    /*se le manda la direccion de la ubicacion de la imahen para colocarla de fondo*/
    
    public JPanelConFondo(String direccion){
        URL url = getClass().getResource(direccion);
        imagen = new ImageIcon(url).getImage();
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(),
                        this);
 
        setOpaque(false);
        super.paint(g);
    }
 
   
}

