/**
 * @author Alfredo Hinojosa Huerta A01036053
 * @author Luis Juan Sanchez A01183634
 * @version 1.00 03/11/2014
 */
package flappywars;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Pipe extends Base {

    private boolean bool;

    public Pipe(int posX, int posY, Animacion animacion) {
        super(posX, posY, animacion);
        bool = true;
    }

    public boolean getBool() {
        return bool;
    }

    public void setBool(boolean b) {
        bool = b;
    }
}
