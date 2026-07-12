import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;
public class   PacmanGame extends JPanel implements ActionListener, KeyListener{
    class block{
        int x;
        int y;
        int width;
        int height;
        Image image;
        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        block( Image image ,int x, int y , int width, int height ){
            this.image= image;
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.startX=x;
            this.startY=y;

        }
        void updateDirection(char Direction){
            this.direction=Direction;
            updatevelocity();
        }
        void updatevelocity(){
            if(this.direction=='U'){
                velocityX=0;
                velocityY=-tilesize/4;
            }
            else if(this.direction=='D'){
                velocityX=0;
                velocityY=tilesize/4;
            }
            else if(this.direction=='L'){
                velocityX=-tilesize/4;
                velocityY=0;
            }
            else if(this.direction=='R'){
                velocityX= tilesize/4;
                velocityY=0;
            }
        }


    }

     int rowCount=21;
        int columnCount=19;
        int tilesize=32;
        int boardWidth=columnCount*tilesize;
        int boardHeight=rowCount*tilesize;
       
        private Image wallImage;

        private Image blueGhostImage;
        private Image orangeGhostImage;
        private Image pinkGhostImage;
        private Image redGhostImage;

        private Image PacmanDownImage;
        private Image PacmanUpImage;
        private Image PacmanLeftImage;
        private Image PacmanRightImage;
        private Timer gameloop;
        
        
        //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };
        
        
        HashSet<block> walls;
        HashSet<block> foods;
        HashSet<block> ghosts;
        block pacman;

    PacmanGame(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        wallImage= new ImageIcon(getClass().getResource("/Objects/wall.png")).getImage();
        blueGhostImage= new ImageIcon(getClass().getResource("/Objects/blueGhost.png")).getImage();
        redGhostImage= new ImageIcon(getClass().getResource("/Objects/redGhost.png")).getImage();
        pinkGhostImage= new ImageIcon(getClass().getResource("/Objects/pinkGhost.png")).getImage();
        orangeGhostImage= new ImageIcon(getClass().getResource("/Objects/orangeGhost.png")).getImage();


        PacmanDownImage= new ImageIcon(getClass().getResource("/Objects/pacmanDown.png")).getImage();
        PacmanUpImage= new ImageIcon(getClass().getResource("/Objects/pacmanUp.png")).getImage();
        PacmanLeftImage= new ImageIcon(getClass().getResource("/Objects/pacmanLeft.png")).getImage();
        PacmanRightImage= new ImageIcon(getClass().getResource("/Objects/pacmanRight.png")).getImage();

        loadmap();
        gameloop = new Timer (50,this);
        gameloop.start();
    
    }
    public void loadmap(){
        walls = new HashSet<block>();
        foods= new HashSet<block>();
        ghosts =new HashSet<block>();

        for( int r=0;r < rowCount;r++ ){
          for (int c=0; c< columnCount; c++){
            String row = tileMap[r];
            char tileMapChar= row.charAt(c);

            int x = c*tilesize;
            int y = r*tilesize;
            if (tileMapChar=='X'){
                //wall
                block wall = new block(wallImage, x , y , tilesize, tilesize);
                walls.add(wall);
            }
            else if(tileMapChar=='b'){
                //blueGhost
               block ghost  = new block(blueGhostImage,x,y,tilesize,tilesize);
               ghosts.add(ghost);
               ghost.updateDirection('U'); // <--- ADD THIS
               ghost.updatevelocity();
            }
            else if (tileMapChar=='r'){
                //redGhost
                block ghost = new block (redGhostImage , x , y , tilesize , tilesize);
                ghosts.add(ghost);
                ghost.updateDirection('U'); // <--- ADD THIS
                ghost.updatevelocity();
            }
            else if (tileMapChar=='o'){
                //orange ghaost 
                block ghost = new block (orangeGhostImage , x , y , tilesize , tilesize);
                ghosts.add(ghost);
                ghost.updateDirection('U'); // <--- ADD THIS
                ghost.updatevelocity();

            }
            else if (tileMapChar=='p'){
                // pint ghost image 
                block ghost = new block (pinkGhostImage, x , y , tilesize , tilesize);
                ghosts.add(ghost);
                ghost.updateDirection('U'); // <--- ADD THIS
                ghost.updatevelocity();
            }
            else if (tileMapChar=='P'){
                // pacman's object 
                pacman = new block(PacmanRightImage,x,y,tilesize,tilesize);
                pacman.direction = 'R';
                pacman.updatevelocity();
            }
            
            else if (tileMapChar == ' ') { // food
               block  food = new block (null,x+14 , y+14 ,4,4);
               foods.add(food);
            }
             }
          }
    
    }
         public void paintComponent(Graphics g){
                super.paintComponent(g);
                draw(g);
    }

    public void draw(Graphics g){
        for (block wall : walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        for (block food : foods){
            g.setColor(Color.WHITE);
            g.fillOval(food.x, food.y, food.width, food.height);
        }
        for (block ghost : ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }
        if (pacman != null && pacman.image != null) {
            g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);
        }
}

        public void movePacman(){
    pacman.x += pacman.velocityX;
    pacman.y += pacman.velocityY;

    for (block wall : walls) {
        if (checkWallCollision(pacman)) {
            pacman.x -= pacman.velocityX;
            pacman.y -= pacman.velocityY;
            break; 
        }
    }

    // Boundary check
    pacman.x = Math.max(0, Math.min(pacman.x, boardWidth - pacman.width));
    pacman.y = Math.max(0, Math.min(pacman.y, boardHeight - pacman.height));

    for (block ghost : ghosts) {
        if (checkCollision(pacman, ghost)) {
            gameloop.stop(); // Game over logic
            System.out.println("Game Over!");
        }
    }

    block foodEaten = null;
    for (block food : foods){
        if (pacman.x < food.x + food.width &&
            pacman.x + pacman.width > food.x &&
            pacman.y < food.y + food.height &&
            pacman.y + pacman.height > food.y) {
                foodEaten = food;
                break;
            }
    }
    foods.remove(foodEaten);
}
    



// check wall collision
public boolean checkWallCollision(block nextPosition){

    for (block wall : walls){
        if (nextPosition.x < wall.x + wall.width &&
            nextPosition.x + nextPosition.width > wall.x &&
            nextPosition.y < wall.y + wall.height &&
            nextPosition.y + nextPosition.height > wall.y) {
                return true;
            }
    }
    return false;
}

public boolean checkCollision(block a, block b) {
    return a.x < b.x + b.width &&
           a.x + a.width > b.x &&
           a.y < b.y + b.height &&
           a.y + a.height > b.y;
}

public void moveGhosts() {
    for (block ghost : ghosts) {
        ghost.x += ghost.velocityX;
        ghost.y += ghost.velocityY;

        // Agar wall se takraye toh direction change karein
        if (checkWallCollision(ghost)) {
            ghost.x -= ghost.velocityX;
            ghost.y -= ghost.velocityY;
            
            // Randomly new direction select karna (U, D, L, R)
            char[] directions = {'U', 'D', 'L', 'R'};
            ghost.updateDirection(directions[new Random().nextInt(4)]);
        }
    }
}


    @Override
    public void actionPerformed(ActionEvent e) {
        movePacman();
        moveGhosts();
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
      ;
    }
    @Override
    public void keyReleased(KeyEvent e) {
    //    System.out.println("EventKey: "+e.getKeyCode());
    if(e.getKeyCode()==KeyEvent.VK_UP){
        pacman.updateDirection('U');
        pacman.image=PacmanUpImage;
    }
    else if(e.getKeyCode()==KeyEvent.VK_DOWN){
        pacman.updateDirection('D');
        pacman.image=PacmanDownImage;
    }
    else if(e.getKeyCode()==KeyEvent.VK_LEFT){
        pacman.updateDirection('L');
        pacman.image=PacmanLeftImage;
    }
    else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
        pacman.updateDirection('R');
        pacman.image=PacmanRightImage;
    }

}
}



