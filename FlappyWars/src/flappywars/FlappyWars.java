/**
 * @author Alfredo Hinojosa Huerta A01036053
 * @author Luis Juan Sanchez A01183634
 * @version 1.00 03/11/2014
 */
package flappywars;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.awt.Graphics;

public class FlappyWars extends JFrame implements Runnable, KeyListener {
    
    private Image imgBackground; // Imagen del Background (Tablero X-Wing)
    private Image imgBlack; // Imagen del espacio
    private Image imgPipe; // TEST PIPE <----- DELETE
    private long tiempoActual;  // tiempo actual
    private long tiempoInicial; // tiempo inicial
    private Animacion animNave; // Animacion de X-Wing
    private Animacion animPipe; // Animacion de Pipe
    private Graphics dbg; // Objeto Grafico
    private Image dbImage; // Imagen
    private Xwing nave; // Objeto de la clase Xwing 
    private int nPosx; // Posicion en x de la nave
    private int nPosy; // Posicion en y de la nave
    
    
    public FlappyWars() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 760);
        setTitle("Flappy Wars");
        
        imgBackground = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/background.png"));
        imgBlack = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/black.jpg"));
        imgPipe = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pipe.png"));
        
        // Se cargan las imagenes de animNave
        Image n0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing4.png")); 
        
        // Animacion del X-Wing
        animNave = new Animacion();
        animNave.sumaCuadro(n0, 100);
        
        // X-Wing
        nave = new Xwing(150, 200, animNave);
       // addKeyListener(this);
       Thread th = new Thread(this);
       th.start();
    }
    
    /**
     * Se ejecuta el Thread, el juego no continua si la pausa esta activada.
     */
    public void run() {

        // Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();
        while (true) {
                checaColision();
                actualiza();
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }
    
    /**
     * En este metodo se actualiza las posiciones del balon y de la canasta.
     */
    public void actualiza() {
        
    }

    /**
     * Este metodo se encarga de cambiar las posiciones de lso objetos balon y
     * canasta cuando colisionan entre si.
     */
    public void checaColision() {

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
        g.drawImage(imgPipe, 500, 332, this);
        if (nave.getAnimacion() != null) {
            g.drawImage(nave.animacion.getImagen(), nave.getPosX(), nave.getPosY(), this);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlappyWars flappy = new FlappyWars();
        flappy.setVisible(true);
        
    }

    public void keyTyped(KeyEvent e) {
       
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        
    }
    
}
