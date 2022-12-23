import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
public class Panel extends JPanel implements ActionListener {
  //dimensions of the panel
   static int width=1200;
   static int height=600;

   //size of each unit
    static int unit=50;

    //for checking the state of the game of regular intervals
    Timer timer;
    static int delay=160;

    //for food spawns
    Random random;
    int fx,fy;
int score =0;
    //Setting the initial body size
    int body=3;
    char dir='R';
    boolean flag=false;
    static int size=(width*height)/(unit*unit);
    //
    int xsnake[]=new int[size];
    int ysnake[]=new int[size];


    Panel()
    {
     //setting the dimensions of the panel to width * height
     this.setPreferredSize(new Dimension(width,height));
     this.setBackground(Color.BLACK);

     //making sure that the panel is in focus and keyboard input gets read
     this.setFocusable(true);
     random=new Random();

     //
     this.addKeyListener(new Key());
     game_start();
    }
    public void game_start()
    {
        //spawning the food
        spawnFood();
        //setting the game running flag to true
        flag=true;
        //starting the timer with delay
        timer =new Timer(delay,this);
        timer.start();
    }

    public void spawnFood()
    {
      fx=random.nextInt((int)(width/unit))*unit;
      fy=random.nextInt((int)(height/unit))*unit;
    }

    public void checkHit()
    {
     //checking the snkaes hand collision with its own body or the walls

     //checking with body parts
     for(int i=body;i>0;i--)
     {
      if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i]))
      {
       flag=false;
      }
     }

     //check hit with walls
     if(xsnake[0]<0)
     {
      flag=false;
     }
     if(xsnake[0]>width)
     {
      flag=false;
     }
     if(ysnake[0]<0)
     {
      flag=false;
     }
     if(ysnake[0]>height)
     {
      flag=false;
     }

     if(flag==false)
     {
      timer.stop();
     }
    }

    //intermediate function to call the draw function
    public void paintComponent(Graphics graphic)
    {
      super.paintComponent(graphic);
      draw(graphic);
    }

 private void draw(Graphics graphic) {
     if(flag) {
         //setting parameter for the food block
         graphic.setColor(Color.RED);
         graphic.fillOval(fx, fy, unit, unit);


         //setting params for the snake
         for (int i = 0; i < body; i++) {
             //for the head
             if (i == 0) {
                 graphic.setColor(Color.green);
                 graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
             }
             //for other parts
             else {
                 graphic.setColor(Color.orange);
                 graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
             }
         }
         //drawing the score
         graphic.setColor(Color.BLUE);
         graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));

         FontMetrics f = getFontMetrics(graphic.getFont());
         //drawing takes the string yo draw, starting position in x and the starting position in y
         graphic.drawString("SCORE:" + score, (width - f.stringWidth("SCORE:" + score)) / 2, graphic.getFont().getSize());
     }
     else{
         gameOver(graphic);
     }
 }

    private void gameOver(Graphics graphic) {

        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f = getFontMetrics(graphic.getFont());
        //drawing takes the string yo draw, starting position in x and the starting position in y
        graphic.drawString("SCORE:" + score, (width - f.stringWidth("SCORE:" + score)) / 2, graphic.getFont().getSize());

        //graphic for the game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        //drawing takes the string yo draw, starting position in x and the starting position in y
        graphic.drawString("Game Over!", (width - f2.stringWidth("Game Over!")) / 2, height/2);

        //graphic for the replay prompt
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f3 = getFontMetrics(graphic.getFont());
        //drawing takes the string yo draw, starting position in x and the starting position in y
        graphic.drawString("Press R to replay" , (width - f3.stringWidth("Press R to replay:" )) / 2, height/2-180);

    }

    public void move()
    {
            //loop for updating the body parts except the head
            for(int i=body;i>0;i--)
            {
                xsnake[i]=xsnake[i-1];
                ysnake[i]=ysnake[i-1];
            }

            //for the updation of the co-ordinates
            switch (dir)
            {
                case 'U':
                    ysnake[0]= ysnake[0]-unit;
                    break;
                case 'D':
                    ysnake[0]= ysnake[0]+unit;
                    break;
                case 'L':
                    xsnake[0]= xsnake[0]-unit;
                    break;
                case 'R':
                    xsnake[0]= xsnake[0]+unit;
                    break;
            }
    }

    public void checkScore()
    {
        //checking if the head and the food block coincide
        if(fx==xsnake[0] && fy==ysnake[0])
        {
            body++;
            score++;
            spawnFood();
        }
    }

    public class Key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    if(dir!='R')
                    {
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    if(dir!='L')
                    {
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D')
                    {
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    if(dir!='U')
                    {
                        dir='D';
                    }
                    break;
                case KeyEvent.VK_R :
                    if(!flag)
                    {
                        score=0;
                        body=3;
                        dir='R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        game_start();
                    }
                    break;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(flag)
        {
            move();
            checkScore();
            checkHit();
        }
        repaint();

    }
}
