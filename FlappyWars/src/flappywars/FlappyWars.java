/**
 * @author Alfredo Hinojosa Huerta A01036053
 * @author Luis Juan Sanchez A01183634
 * @version 1.00 03/11/2014
 */
package flappywars;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.awt.Graphics;

public class FlappyWars extends JFrame implements Runnable, KeyListener {

    private Image imgBackground; // Imagen del Background (Tablero X-Wing)
    private Image imgBlack; // Imagen del espacio
    private Image imgStart; // Imagen de MainScreen
    private long tiempoActual;  // tiempo actual
    private long tiempoTranscurrido; // tiempo transcurrido
    private Animacion animNave; // Animacion de X-Wing
    private Animacion animPipe; // Animacion de Pipe
    private Animacion pp0;
    private Animacion pp1;
    private Graphics dbg; // Objeto Grafico
    private Image dbImage; // Imagen
    private Xwing nave; // Objeto de la clase Xwing
    private int gap; // Distancia entre las pipas verticalmente
    private int nPosx; // Posicion en x de la nave
    private int nPosy; // Posicion en y de la nave
    private int nVely; // velocidad en y de la nave
    private int pVelx; // Velocidad de los obstaculos
    private int score; // Score del jugador
    private boolean gameON; // Flag de iniciar juego
    private boolean pause; // Flag de pausa
    private boolean gameOver; //Flag de juego terminado
    private LinkedList frames;
    private LinkedList pipes;
    private SoundClip jump;
    private SoundClip goal;
    private SoundClip death;

    public FlappyWars() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 760);
        setTitle("Flappy Wars");
        gameON = false;
        pause = false;
        gameOver = false;
        nVely = 0;
        score = 0;
        frames = new LinkedList();
        pipes = new LinkedList();
        gap = 200;
        pVelx = 10;

        imgStart = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/start.png"));
        imgBackground = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/background.png"));
        imgBlack = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/black.jpg"));

        // Se cargan las imagenes de animNave
        Image n0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing4.png"));
        Image n1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing3.png"));
        Image n2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing2.png"));
        Image n3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing1.png"));
        Image n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing0.png"));

        Image p0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pipeU.png"));
        Image p1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pipeD.png"));

        //Creacion de las pipas
        pp0 = new Animacion();
        pp1 = new Animacion();
        pp0.sumaCuadro(p0, 100);
        pp1.sumaCuadro(p1, 100);
        int rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd + gap, pp1));

        // Animacion del X-Wing
        animNave = new Animacion();
        animNave.sumaCuadro(n0, 200);
        animNave.sumaCuadro(n1, 200);
        animNave.sumaCuadro(n2, 200);
        animNave.sumaCuadro(n3, 200);
        animNave.sumaCuadro(n4, 200);
        animNave.sumaCuadro(n3, 200);
        animNave.sumaCuadro(n2, 200);
        animNave.sumaCuadro(n1, 200);

        // Lista enlazada de cuadros de X-Wing
        frames.add(n0);
        frames.add(n1);
        frames.add(n2);
        frames.add(n3);
        frames.add(n4);

        // X-Wing
        nave = new Xwing(150, 200, animNave);

        jump = new SoundClip("sounds/jump.wav");
        goal = new SoundClip("sounds/goal.wav");
        death = new SoundClip("sounds/death.wav");

        addKeyListener(this);
        Thread th = new Thread(this);
        th.start();
    }

    /**
     * Metodo de reset
     */
    public void reset() {
        gameON = true;
        pause = false;
        gameOver = false;
        nVely = 0;
        score = 0;
        pipes = new LinkedList();
        gap = 200;
        pVelx = 10;
        nave.setPosX(150);
        nave.setPosY(200);
        pipes.clear();
        int rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd + gap, pp1));
        rnd = (int) (50 + Math.random() * (483 - gap));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd + gap, pp1));
    }

    /**
     * Se ejecuta el Thread, el juego no continua si la pausa esta activada.
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (true) {
            if (gameON && !pause) {
                if (!gameOver) {
                    checaColision();
                }
                actualiza();
            }
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
        // Guarda el tiempo actual del sistema

//        tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
//        tiempoActual += tiempoTranscurrido;
//        nave.getAnimacion().actualiza(tiempoTranscurrido);
        Animacion anim = new Animacion();
        int flag = nVely / 5 - 1;
        switch (flag) {
            case 1:
                Image n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing0.png"));
                anim.sumaCuadro(n4, 100);
                nave.setAnimacion(anim);
                break;
            case 0:
                n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing1.png"));
                anim.sumaCuadro(n4, 100);
                nave.setAnimacion(anim);
                break;
            case -1:
                n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing2.png"));
                anim.sumaCuadro(n4, 100);
                nave.setAnimacion(anim);
                break;
            case -2:
                n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing3.png"));
                anim.sumaCuadro(n4, 100);
                nave.setAnimacion(anim);
                break;
            default:
                n4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/xwing4.png"));
                anim.sumaCuadro(n4, 100);
                nave.setAnimacion(anim);
                break;
        }
        nVely -= 1;
        nave.setPosY(nave.getPosY() - nVely);
        if (!gameOver) {
            for (int i = 0; i < 8; i++) {
                Pipe temp = (Pipe) pipes.get(i);
                temp.setPosX(temp.getPosX() - pVelx);
            }
        }
    }

    /**
     * Este metodo se encarga de cambiar las posiciones de lso objetos balon y
     * canasta cuando colisionan entre si.
     */
    public void checaColision() {
        // Colision de X-Wing con fondo del JFrame
        if (nave.getPosY() > 523) {
            gameOver = true;
            death.play();
        }

        // Manejo aleatorio de Pipes
        int rnd = 0;
        for (int i = 0; i < 8; i++) {
            Pipe temp = (Pipe) pipes.get(i);
            if ((i % 2 == 0) && temp.getPosX() == nave.getPosX()) {
                score++;
                goal.play();
            }
            if (temp.getPerimetro().intersects(nave.getPerimetro())) {
                gameOver = true;
                death.play();
            }
            if (temp.getPosX() < -temp.getAncho()) {
                if (i % 2 == 0) {
                    rnd = (int) (50 + Math.random() * (483 - gap));
                    temp.setPosY(rnd - 383);
                } else {
                    temp.setPosY(rnd + gap);
                }
                temp.setPosX(getWidth());
            }
        }
    }

    /**
     * Metodo que actuliza las animaciones
     *
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
        if (!gameON) {
            g.drawImage(imgBlack, 0, 0, this);
            g.drawImage(imgStart, 0, 0, this);
        } else if (gameON) {
            g.drawImage(imgBlack, 0, 0, this);
            for (int i = 0; i < 8; i++) {
                Pipe temp = (Pipe) pipes.get(i);
                g.drawImage(temp.animacion.getImagen(), temp.getPosX(), temp.getPosY(), this);
            }
            if (nave.getAnimacion() != null) {
                g.drawImage(nave.animacion.getImagen(), nave.getPosX(), nave.getPosY(), this);
            }
            g.drawImage(imgBackground, 0, 0, this);
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameON = true;
        }
        if (gameON && !gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                nVely = 10;
                jump.play();
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                pause = !pause;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                reset();
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

}
