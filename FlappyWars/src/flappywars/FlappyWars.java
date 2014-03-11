/**
 * @author Luis Juan Sanchez A01183634
 * @author Alfredo Hinojosa Huerta A01036053
 * @version 1.00 03/11/2014
 */
package flappywars;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.awt.Graphics;

public class FlappyWars extends JFrame {
    
    private Image imgBackground; // Imagen del Background (Tablero X-Wing)
    private Image imgBlack; // Imagen del espacio
    private Graphics dbg; // Objeto Grafico
    private Image dbImage; // Imagen
    
    
    public FlappyWars() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 760);
        setTitle("Flappy Wars");
        
        imgBackground = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/background.png"));
        imgBlack = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/black.jpg"));
    }
    
    /**
     * Metodo que actuliza las animaciones
     * @param g es la <code>imagen</code> del objeto.
     */
    public void paint(Graphics g) {
        // Inicializa el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }
    
    /**
     * Este metodo pinta la imagen con posicion actualizada
     */
    public void paint1(Graphics g) {
        g.drawImage(imgBlack, 0, 0, this);
        g.drawImage(imgBackground, 0, 0, this);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlappyWars flappy = new FlappyWars();
        flappy.setVisible(true);
        
    }
    
}
