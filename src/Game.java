import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int BLOCK_SIZE = 10;

    //private Enemy[][] enemies;
    private static ArrayList<Enemy> enemies;
   // private Friend[][] friends;
    private  static ArrayList<Friend> friends;
    private  static ArrayList<Bullet> bullets;

    private Timer timer;

    private Random rand;

    private AirCraft airCraft;

    boolean isrunning;
    boolean isPopUpShown;
    //JOptionPane bitirme;
    boolean abas;
    boolean dbas;

    public Game()
    {
        enemies=new ArrayList<>();
        friends=new ArrayList<>();
        bullets=new ArrayList<>();
        abas=false;
        dbas=false;
        setSize(500,500);
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
        isrunning=true;
        isPopUpShown = false;
        airCraft=new AirCraft();
        this.addKeyListener(airCraft);
        this.addMouseListener(airCraft);
        //bitirme=new JOptionPane();
        //add(bitirme);
        timer= new Timer(1000,this);
        timer.start();
        setVisible(true);
        JFrame jFrame=new JFrame();
        jFrame.setSize(500,500);
        jFrame.add(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // jFrame.setResizable(false);
        jFrame.setVisible(true);

    }
    private /*synchronized*/ void checkCollision()
    {
        //if(isrunning) {
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).islive) {
                    for (int j = 0; j < friends.size(); j++) {
                        if (enemies.get(i).x == friends.get(j).x && enemies.get(i).y == friends.get(j).y && friends.get(j).islive) {
                            enemies.get(i).islive = false;
                            friends.get(j).islive = false;
                        }
                    }
                }
            }
            repaint();
        //}

    }
    public class Enemy extends Thread {
        int x;
        int sagx;
        int y;
        int alty;

        int height=10;
        int weight=10;

        boolean islive;
        public Enemy()
        {
            boolean empty=true;
            while (true)
            {
                rand=new Random();
                x=rand.nextInt(470/10)*10;
                y=rand.nextInt(450/10)*10;
                sagx=x+10;
                alty=y+10;
                for(int i=0;i<enemies.size();i++)
                {
                    if(x==enemies.get(i).x&&y==enemies.get(i).y)
                    {
                        empty=false;
                        break;
                    }
                }
                for(int i=0;i<friends.size();i++)
                {
                    if(x==friends.get(i).x&&y== friends.get(i).y)
                    {
                        empty=false;
                        break;
                    }
                }
                if(empty)
                {

                    islive=true;
                    enemies.add(this);
                    break;
                }

            }
        }

        public void run()
        {
            while (islive)
            {
                int direction = rand.nextInt(4);
                switch (direction) {
                    case 0://sağa git
                        if (x + 20 < 490) {
                            x = x + 10;
                        }
                        break;
                    case 1://sola git
                        if(x-10>=0){
                            x=x-10;
                        }
                        break;
                    case 2://aşağı git
                        if(y+20<470)
                        {
                            y=y+10;
                        }
                        break;
                    case 3://yukarı git
                        if(y-10>=0)
                        {
                            y=y-10;
                        }
                        break;
                }
               repaint();
                checkCollision();
                repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

    }
    public class Friend extends Thread {
        int x;
        int y;
        int height=10;
        int weight=10;

        boolean islive;
        public Friend()
        {
            boolean empty=true;
            while (true)
            {
            rand=new Random();
            x=rand.nextInt(470/10)*10;
            y=rand.nextInt(450/10)*10;
            for(int i=0;i<friends.size();i++)
            {
                if(x==friends.get(i).x&&y==friends.get(i).y)
                {
                    empty=false;
                    break;
                }
            }
            for(int i=0;i<enemies.size();i++)
            {
                if(x==enemies.get(i).x&&y==enemies.get(i).y)
                {
                    empty=false;
                    break;
                }
            }
            if(empty)
            {

                islive=true;
                friends.add(this);
                break;
            }

            }


        }
        public void run()
        {
            while (islive)
            {
                int direction = rand.nextInt(4);
                switch (direction) {
                    case 0://sağa git
                        if (x + 20 < 490) {
                            //System.out.println(x);
                            x = x + 10;
                           // System.out.println(x);
                        }
                        break;
                    case 1://sola git
                        if(x-10>=0){
                            x=x-10;
                        }
                        break;
                    case 2://aşağı git
                        if(y+20<470)
                        {
                            y=y+10;
                        }
                        break;
                    case 3://yukarı git
                        if(y-10>=0)
                        {
                            y=y-10;
                        }
                        break;
                }
               repaint();
                checkCollision();
               // repaint();
               // repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

    }
    public class AirCraft extends Thread implements MouseListener, KeyListener {
        int x;
        int y;
        int height = 10;
        int weight = 10;
        boolean islive;

        public AirCraft() {
            x = 250;
            y = 250;
            islive = true;
            airCraft = this;
            setFocusable(true);
            requestFocusInWindow();
            addKeyListener(this);
        }

        public void AircraftCollision() {
           // if(isrunning) {
                for (int i = 0; i < enemies.size(); i++) {
                    if (airCraft.x == enemies.get(i).x && airCraft.y == enemies.get(i).y) {
                        airCraft.islive = false;
                        enemies.get(i).islive = false;
                        break;
                    }
                }
                repaint();
           // }


        }


        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
           // if(isrunning) {

            if (e.getKeyCode() == KeyEvent.VK_W && airCraft.y - 10 >= 0) {

                airCraft.y = airCraft.y - 10;

                repaint();
                AircraftCollision();
                // repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_D && airCraft.x + 20 < 490) {
                dbas = true;
                airCraft.x = airCraft.x + 10;
                repaint();
                AircraftCollision();
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).islive && bullets.get(i).yon == 2) {
                        if (airCraft.x == bullets.get(i).oldx && airCraft.y == bullets.get(i).y && bullets.get(i).takim == 1) {
                            airCraft.islive = false;
                            bullets.get(i).islive = false;
                            break;
                        }
                        if (airCraft.x == bullets.get(i).oldx && airCraft.y == bullets.get(i).y && bullets.get(i).takim == 2) {
                            bullets.get(i).islive = false;
                            break;
                        }
                    }
                }

                repaint();

            } else
                dbas = false;
            if (e.getKeyCode() == KeyEvent.VK_S && airCraft.y + 20 < 470) {
                airCraft.y = airCraft.y + 10;
                repaint();
                AircraftCollision();
                // repaint();

            }
            if (e.getKeyCode() == KeyEvent.VK_A && airCraft.x - 10 >= 0) {
                abas = true;
                airCraft.x = airCraft.x - 10;
                repaint();
                AircraftCollision();
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).islive && bullets.get(i).yon == 1) {
                        if (airCraft.x == bullets.get(i).oldx && airCraft.y == bullets.get(i).y && bullets.get(i).takim == 1) {
                            airCraft.islive = false;
                            bullets.get(i).islive = false;
                            break;
                        }
                        if (airCraft.x == bullets.get(i).oldx && airCraft.y == bullets.get(i).y && bullets.get(i).takim == 2) {
                            bullets.get(i).islive = false;
                            break;
                        }
                    }
                }
                repaint();

            } else
                abas = false;

        //}
    }
        @Override
        public void keyReleased(KeyEvent e) {
            abas=false;
            dbas=false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Bullet oyuncumermi=new Bullet(airCraft.x+10,airCraft.y,new Color(0xE78406),2,1);
            Bullet oyuncumermi2=new Bullet(airCraft.x,airCraft.y,new Color(0xE78406),2,2);
            bullets.add(oyuncumermi);
            bullets.add(oyuncumermi2);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

    }
    public class Bullet implements ActionListener
    {
        int x;
        int y;
        int oldx;

        Color color;
        int takim;
        int yon;
        boolean islive;

        Timer zaman;

        public Bullet(int x,int y,Color color,int takim,int yon)
        {
            this.x=x;
            this.y=y;
            this.color=color;
            this.takim=takim;
            this.yon=yon;
            zaman= new Timer(100,this);
            zaman.start();
            islive=true;

            bullets.add(this);

        }



        @Override
        public void actionPerformed(ActionEvent e) {
           // if(isrunning) {
            if (islive) {
                switch (yon) {
                    case 1://saÄŸa git
                        if (x + 15 <= 495) {
                            oldx = x;
                            x = x + 10;
                        }
                        break;
                    case 2://sola git
                        if (x - 10 >= 0) {
                            oldx = x;
                            x = x - 10;
                        }
                        break;
                }
                repaint();
                checkcollision();
                repaint();
            } else {
                zaman.stop(); // Bullet artık canlı olmadığında zamanlayıcıyı durdurun
            }
        //}
        }
        public void checkcollision() {
           // if (isrunning) {
                switch (takim) {
                    case 1://enemy
                        for (int i = 0; i < enemies.size(); i++) {
                            if (enemies.get(i).islive) {
                                if (enemies.get(i).x == x && enemies.get(i).y == y) {
                                    islive = false;
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < friends.size(); i++) {
                            if (friends.get(i).islive) {
                                if (friends.get(i).x == x && friends.get(i).y == y) {
                                    islive = false;
                                    friends.get(i).islive = false;
                                    break;
                                }
                            }
                        }
                        if (airCraft.x == x && airCraft.y == y&&airCraft.islive) {
                            islive = false;
                            airCraft.islive = false;
                        }
                        if (x >= 480 || x == 0) {
                            islive = false;
                        }
                        if (/*yon==1&&*/abas&&airCraft.islive) {
                            if (oldx == airCraft.x && y == airCraft.y) {
                                islive = false;
                                airCraft.islive = false;
                            }
                        }
                        if (/*yon==2&&*/dbas&&airCraft.islive) {
                            if (oldx == airCraft.x && y == airCraft.y) {
                                islive = false;
                                airCraft.islive = false;
                            }
                        }
                        break;
                    case 2://friend
                        for (int i = 0; i < friends.size(); i++) {
                            if (friends.get(i).islive) {
                                if (friends.get(i).x == x && friends.get(i).y == y) {
                                    islive = false;
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < enemies.size(); i++) {
                            if (enemies.get(i).islive) {
                                if (enemies.get(i).x == x && enemies.get(i).y == y) {
                                    islive = false;
                                    enemies.get(i).islive = false;
                                    break;
                                }
                            }
                        }
                        if (airCraft.x == x && airCraft.y == y&&airCraft.islive) {
                            islive = false;
                            //airCraft.islive=false;
                        }
                        if (x >= 480 || x == 0) {
                            islive = false;
                        }
                        if (/*yon == 1 &&*/ abas&&airCraft.islive) {
                            if (oldx == airCraft.x && y == airCraft.y) {
                                islive = false;
                                //airCraft.islive=false;
                            }
                        }
                        if (/*yon == 2 &&*/ dbas&&airCraft.islive) {
                            if (oldx == airCraft.x && y == airCraft.y) {
                                islive = false;
                                //airCraft.islive=false;wd
                            }
                        }
                        break;
                }

            }
       // }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<enemies.size();i++)
        {
            if(enemies.get(i).islive)
            {
                Bullet enemymermi=new Bullet(enemies.get(i).x+10,enemies.get(i).y,new Color(0x2CE4EA),1,1);
                Bullet enemymermi2=new Bullet(enemies.get(i).x,enemies.get(i).y,new Color(0x0CCBD2),1,2);
                bullets.add(enemymermi);
                bullets.add(enemymermi2);
            }

        }
        for(int i=0;i<friends.size();i++)
        {
            if(friends.get(i).islive)
            {
                Bullet friendmermi=new Bullet(friends.get(i).x+10,friends.get(i).y,new Color(0x7220FA),2,1);
                Bullet friendmermi2=new Bullet(friends.get(i).x,friends.get(i).y,new Color(0x7C28F8), 2,2);
                bullets.add(friendmermi);
                bullets.add(friendmermi2);

            }

        }

    }

    @Override
    protected /*synchronized*/ void paintComponent(Graphics g) {
       super.paintComponent(g);
        int num=0;
        if(isrunning) {

            g.setColor(Color.GREEN);
            for (int i = 0; i < friends.size(); i++) {
                if (friends.get(i).islive) {
                    g.fillRect(friends.get(i).x, friends.get(i).y, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).islive) {
                    g.setColor(bullets.get(i).color);
                    g.fillRect(bullets.get(i).x, bullets.get(i).y, 5, 5);
                } else {
                    bullets.remove(i); // Canlı olmayan mermiyi listeden kaldır
                    i--; // Listeden bir öğe kaldırıldığından, indeksi azaltın
                }
            }
            g.setColor(Color.BLACK);
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).islive) {
                    g.fillRect(enemies.get(i).x, enemies.get(i).y, BLOCK_SIZE, BLOCK_SIZE);
                } else
                    num++;
            }
           /* for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).islive) {
                    g.setColor(bullets.get(i).color);
                    g.fillRect(bullets.get(i).x, bullets.get(i).y, 5, 5);
                } else {
                    bullets.remove(i); // Canlı olmayan mermiyi listeden kaldır
                    i--; // Listeden bir öğe kaldırıldığından, indeksi azaltın
                }
            }*/

            if (airCraft.islive) {
                g.setColor(Color.RED);
                g.fillRect(airCraft.x, airCraft.y, BLOCK_SIZE, BLOCK_SIZE);
            } else {
                // isrunning = false;
                if (!isPopUpShown) {
                    isPopUpShown = true;
                    int result = JOptionPane.showOptionDialog(this, "Oyunu kaybettin", "Oyun Bitti", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{}, null);
                    if (result == JOptionPane.CLOSED_OPTION) {
                        System.exit(0); // Programı kapat
                        isrunning = false;
                    }
                }
            }
            if (num == enemies.size()) {
                // isrunning = false;
                if (!isPopUpShown) {
                    isPopUpShown = true;
                    int result = JOptionPane.showOptionDialog(this, "Oyunu kazandın", "Oyun Bitti", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                    if (result == JOptionPane.CLOSED_OPTION) {
                        System.exit(0); // Programı kapat
                        isrunning = false;
                    }
                }



            }


        }

    }
}
