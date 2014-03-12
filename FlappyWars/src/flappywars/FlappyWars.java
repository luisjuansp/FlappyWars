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
import javax.swing.JOptionPane;

public class FlappyWars extends JFrame implements Runnable, KeyListener {

    private Image imgBackground; // Imagen del Background (Tablero X-Wing)
    private Image imgBlack; // Imagen del espacio
    private Image imgStart; // Imagen de MainScreen
    private Image imgPause; // Imagen de pausado
    private Image imgGO; // Imagen de Game Over
    private Image imgIns; // Imagen de instrucciones
    private long tiempoActual;  // tiempo actual
    private long tiempoTranscurrido; // tiempo transcurrido
    private Animacion animNave; // Animacion de X-Wing
    private Animacion pp0; // Animacion de Pipe
    private Animacion pp1; // Animacion de Pipe
    private Graphics dbg; // Objeto Grafico
    private Image dbImage; // Imagen
    private Xwing nave; // Objeto de la clase Xwing
    private int gap; // Distancia entre las pipas verticalmente
    private int nVely; // velocidad en y de la nave
    private int pVelx; // Velocidad de los obstaculos
    private int score; // Score del jugador
    private boolean gameON; // Flag de iniciar juego
    private boolean pause; // Flag de pausa
    private boolean gameOver; //Flag de juego terminado
    private boolean instruc; // Flag de instrucciones
    private Font myFont; // Estilo fuente personalizado
    private Color verdeT; // Color especial
    private LinkedList frames; // Lista enlazada de Frames de X-wing
    private LinkedList pipes; // Lista enlazada de Pipes
    private SoundClip jump; // Sonido de salto
    private SoundClip goal; // Sonido de punto
    private SoundClip death; // Sonido de muerte
    private boolean jScore; // Flag de presentar la score
    private boolean jReset; // Flag para resetear el juego
    private int var; // Variable de varianza de las pipas

    /**
     * Constructor de la clase
     */
    public FlappyWars() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 760);
        setTitle("Flappy Wars");
        myFont = new Font("Serif", Font.BOLD, 14);
        verdeT = new Color(1, 75, 8);
        gameON = false;
        pause = false;
        gameOver = false;
        instruc = false;
        nVely = 0;
        score = 0;
        frames = new LinkedList();
        pipes = new LinkedList();
        gap = 200;
        pVelx = 10;
        var = 100;

        // Imagenes del juego
        imgPause = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pause.png"));
        imgStart = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/start.png"));
        imgBackground = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/background.png"));
        imgBlack = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/black.jpg"));
        imgGO = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/gameover.png"));
        imgIns = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/inst.png"));

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
        int rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
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
        nave.setPosX(this.getWidth() / 2 - nave.getAncho() / 2);
        nave.setPosY(this.getHeight() / 2 - nave.getAlto() / 2);
        // Se cargan los sonidos
        jump = new SoundClip("sounds/jump.wav");
        goal = new SoundClip("sounds/goal.wav");
        death = new SoundClip("sounds/death.wav");

        addKeyListener(this);
        Thread th = new Thread(this);
        th.start();
    }

    /**
     * Metodo que reinicia el juego despues de perder. Reinicia los valores
     * iniciales del juego
     */
    public void reset() {
        gameON = true;
        instruc = false;
        pause = false;
        gameOver = false;
        jScore = false;
        jReset = false;
        nVely = 0;
        score = 0;
        pipes = new LinkedList();
        gap = 200;
        pVelx = 10;
        var = 100;
        nave.setPosX(this.getWidth() / 2 - nave.getAncho() / 2);
        nave.setPosY(this.getHeight() / 2 - nave.getAlto() / 2);
        pipes.clear();
        int rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 2, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 3, rnd + gap, pp1));
        rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd - 383, pp0));
        pipes.add(new Pipe(getWidth() + 340 * 4, rnd + gap, pp1));
    }

    /**
     * Se ejecuta el Thread, el juego no continua si la pausa esta activada. El
     * juego no continua si no sea presionado Enter del Menu Principal. El juego
     * termina cuando la boolean gameOver es True
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (true) {
            if (gameON && !pause && !instruc) {
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
            if (jScore) {
                String nombre = JOptionPane.showInputDialog("Cual es tu nombre?");
                JOptionPane.showMessageDialog(null,
                        "El puntaje de " + nombre + " es: " + score, "PUNTAJE",
                        JOptionPane.PLAIN_MESSAGE);
                jReset = true;
            }
        }
    }

    /**
     * En este metodo se actualiza la posicion en Y del X-Wing. Aqui se genera
     * el movimiento aleatorio de los pipes Dependiendo de la direccion de la
     * nave cambia la animacion del X-Wing
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
        if (nave.getPosY() < this.getHeight()) {
            nVely -= 1;
            nave.setPosY(nave.getPosY() - nVely);
        } else {
            jScore = !jReset;
        }
        pVelx = 10 + (score / 10) * 2;
        if (!gameOver) {
            for (int i = 0; i < 8; i++) {
                Pipe temp = (Pipe) pipes.get(i);
                temp.setPosX(temp.getPosX() - pVelx);
            }
        }
    }

    /**
     * Este metodo se administran las colisiones entre el X-Wing con el JFrame,
     * las colisiones del X-Wing con las pipes y la colision de las pipes
     * aleatorias
     */
    public void checaColision() {
        // Colision de X-Wing con fondo del JFrame
        if (nave.getPosY() > 523) {
            gameOver = true;
            death.play();
        }

        if (nave.getPosY() < 50) {
            nVely = 0;
        }

        // Manejo aleatorio de Pipes
        int rnd = 0;
        for (int i = 0; i < 8; i++) {
            Pipe temp = (Pipe) pipes.get(i);
            if ((i % 2 == 0) && temp.getPosX() < nave.getPosX()) {
                if (temp.getBool()) {
                    score++;
                    goal.play();
                    temp.setBool(false);
                }
            }
            if (temp.getPerimetro().intersects(nave.getPerimetro())) {
                gameOver = true;
                death.play();
            }
            if (temp.getPosX() < -temp.getAncho()) {
                if (i % 2 == 0) {
                    var = score / 10;
                    switch (var) {
                        case 0:
                            var = 100;
                            break;
                        case 1:
                            var = 50;
                            break;
                        default:
                            var = 0;
                            break;
                    }
                    rnd = (int) (50 + var + Math.random() * (483 - (2 * var) - gap));
                    temp.setPosY(rnd - 383);
                } else {
                    temp.setPosY(rnd + gap);
                }
                temp.setPosX(getWidth());
                temp.setBool(true);
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
     * Este metodo pinta las imagenes correctas dependiendo de su posicion
     * actulizada y de los estados del juego.
     */
    public void paint1(Graphics g) {
        if (!gameON) { // Menu principal
            g.drawImage(imgBlack, 0, 0, this);
            g.drawImage(imgStart, 0, 0, this);
        } else if (gameON) { // Juego comenzado
            g.drawImage(imgBlack, 0, 0, this);
            for (int i = 0; i < 8; i++) {
                Pipe temp = (Pipe) pipes.get(i);
                g.drawImage(temp.animacion.getImagen(), temp.getPosX(), temp.getPosY(), this);
            }
            if (nave.getAnimacion() != null) {
                g.drawImage(nave.animacion.getImagen(), nave.getPosX(), nave.getPosY(), this);
            }
            g.setFont(myFont);
            g.setColor(verdeT);
            g.drawImage(imgBackground, 0, 0, this);
            g.drawString("" + score, 564, 682);
        }

        if (pause) { // Juego pausado
            g.drawImage(imgPause, 0, 0, this);
            g.drawString("" + score, 564, 682);
        }

        if (instruc) { // Instrucciones del juego
            g.drawImage(imgIns, 0, 0, this);
        }

        if (gameOver) { // Juego perdido
            g.drawImage(imgGO, 0, 0, this);
            g.drawString("" + score, 564, 682);
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

    /**
     * Se encarga de las instrucciones relacionadas con las teclas, como la
     * pausa (P), el inicio del juego (ENTER), el salto (SPACE), las
     * instrucciones (I), el metodo de reset (R) y el salir del juego al perder
     * (S).
     *
     * @param e evento activado por una tecla en especifico
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameON = true;
        }
        if (gameON && !gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !pause && !instruc) {
                nVely = 10;
                jump.play();
            }
            if (e.getKeyCode() == KeyEvent.VK_P && !instruc) {
                pause = !pause;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_I) {
            instruc = !instruc;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (jReset) {
                reset();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (gameOver) {
                System.exit(0);
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

}
