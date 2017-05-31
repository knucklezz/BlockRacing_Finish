import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by shse14 on 2017-02-24.
 */
public class View extends JPanel implements Runnable{
    private Image dbImage;
    private Graphics dbg;
    //Jpanel variables
    static final int GWIDTH = 1000, GHEIGHT = 1000;
    static final Dimension gameDim = new Dimension(GWIDTH, GHEIGHT);
    //Game variables
    private Thread game;
    private Thread gameModel;
    private volatile boolean running = false;
    Model model;

    //Fönstret för spelet byggs upp

    /**
     * metoden View skapar fönstret för spelet
     */
    public View(){
        model = new Model();
        setPreferredSize(gameDim);
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocus();



    }

    //Skärmen uppdateras

    /**
     * Metoden uppdaterar skärmen, och skriver ut den
     */
    public void run() {
        long lastTime = System.nanoTime();
        int fps = 3;
        long nsView = 1000000000/fps;
        double deltaViewUPS_Update = 0;

        while (running) {
            long now = System.nanoTime();
            deltaViewUPS_Update += ((now - lastTime) / nsView);
//
            while (deltaViewUPS_Update > 1) {
                gameRender();
                paintScreen();
                deltaViewUPS_Update--;
                lastTime = now;
            }

        }


        }
        /*while (running) {

            gameUpdate();
            gameRender();
            paintScreen();

        }*/


    /**
     *Renderar spelet
     */
    private void gameRender() {
        if (dbImage == null) { //Cr
            // eate the buffer
            dbImage = createImage(GWIDTH, GHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is still null!");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        //Clear the screen
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, GWIDTH, GHEIGHT);
        //Draw game elements
        draw(dbg);
    }

    /* Draw all game content in this method */

    /**
     * Ritar ut grafiken
     * @param g
     */
    public void draw(Graphics g) {
        model.draw(g);
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (dbImage != null && g != null) {
                g.drawImage(dbImage, 0, 0, null);
            }
            //For some operating systems (Linux)
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    /**
     * Skapar alla de nödvändiga delarna för spelet
     */
    private void startGame() {
        if (game == null || !running) {
            gameModel = new Thread(model);
            gameModel.start();
            game = new Thread(this);
            game.start();
            running = true;
        }
    }
}

