import sun.java2d.loops.FillRect;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shse14 on 2017-02-24.
 */

public class Model implements Runnable {
    private volatile boolean running = true;
    //private Thread game;

    // Array med alla värden på banan
    boolean pxlArray[] = new boolean[240];

    int offset = 0;
    private Rectangle[] blocks;
    public Car raceCar;
    //Array för banan som innehåller värden för väggens färger.

    /**
     *  Metoden bygger upp banan i en array och bestämmer färgen på banans väggar.
     */
    public Model() {
        raceCar = new Car();
        for (int i = 0; i < pxlArray.length; i += 40) {
            pxlArray[i % 240] = true;
            pxlArray[i + 9] = true;
            pxlArray[i + 10] = true;
            pxlArray[i + 19] = true;
            pxlArray[i + 20] = true;
            pxlArray[i + 29] = true;
            pxlArray[i + 30] = false;
            pxlArray[i + 39] = false;
        }

        //byggstenen för bananan.
        blocks = new Rectangle[240];
        int x = 0;
        int y = -128;
        for (int i = 0; i < 240; i++) {
            blocks[i] = new Rectangle(x, y, 32, 32);
//            System.out.println(" debug " + i + " " + x + " " + y);
            x += 32;
            if (x > 32 * 9) {
                y += 32;
                x = 0;
            }
        }
    }

    //Banan ritas ut

    /**
     * Metoden ritar ut banan och bilen
     * @param g
     */
    public void draw(Graphics g) {


            for (int a = 0; a < pxlArray.length; a++) {
                if (pxlArray[a]) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(blocks[a].x, blocks[a].y+offset, 32, 32);
            }
            raceCar.draw(g);
        }

    // Flyttar ner banan
    /**
     * Update flyttar ned banan 1 pixel i taget
     */
    public void update() {
        this.offset+=1;
        offset = offset%(4*32);
    }


    //bestämmer uppdateringar av modellen per sekund
    /**
     * Metoden bestämmer hur ofta modellen skall uppdateras per sekund
     */

    public void run() {
        long lastTime = System.nanoTime();
        int ups = 96;
        long nsModel = 1000000000 / ups;
        double deltaModelUPS_Draw = 0;

        while (running) {
            long now = System.nanoTime();
            deltaModelUPS_Draw += ((now - lastTime) / nsModel);

            while (deltaModelUPS_Draw > 1) {
                gameUpdate();
                deltaModelUPS_Draw--;
                lastTime = now;
            }

        }
    }
    //ropar på update om programmet körs

    /**
     * Metoden ropar på Update om programmet körs, där update flyttar ned banan.
     */
    private void gameUpdate() {
            if (running) {
                update();//update gamestate
         }
    }
}



