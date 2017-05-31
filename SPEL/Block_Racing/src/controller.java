import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by shse14 on 2017-02-24.
 */
public class controller extends JFrame{
    View view;
    Model model;

    //skapar View och KeyAdapter

    /**
     * metoden skapar View och KeyAdapter
     */
    public controller(){
        view = new View();
        this.view.addKeyListener(new KeyAdapter());
        model = view.model;

        //Programmfönstret
        setTitle("Block Racing");
        setSize(326, 640);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(view);

    }

    //constructor för controller

    /**
     * Constructor för Controller klassen.
     * @param args
     */
    public static void main(String[] args){
        controller test = new controller();
    }


    // controllerna för bilen

    /**
     * klassen Controllerar bilens rörelse höger till vänster.
     */
    private class KeyAdapter implements KeyListener {

            @Override
            public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                model.raceCar.carMove(-1);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                model.raceCar.carMove(1);
            }

        }
            @Override
            public void keyReleased(KeyEvent e) {

        }

            @Override
            public void keyTyped(KeyEvent e) {

        }

        }
    }
