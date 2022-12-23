import javax.swing.JFrame;



public class Frame extends JFrame
{
    Frame()
    {
        this.add(new Panel());
        this.setTitle("Snake-Game");

        //ensuring same experience across different devices
        // this.setSize(1600,800);
        this.setResizable(false);
        this.pack();

        this.setVisible(true);
        //making sure the frame spawns in the centre of the screen
        this.setLocationRelativeTo(null);

    }
}
