import javax.swing.JFrame;
public class App{
    public static void main(String [] args){
        int rowCount=21;
        int columnCount=19;
        int tilesize=32;
        int boardWidth=columnCount*tilesize;
        int boardHeight=rowCount*tilesize;
        JFrame j = new JFrame("Pac man");
        // j.setVisible(true);
        j.setSize( boardWidth, boardHeight);
        j.setResizable(false);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLocationRelativeTo(null);

        PacmanGame pacman = new PacmanGame();
        j.add(pacman);
        j.pack();
        pacman.requestFocusInWindow();
        j.setVisible(true);

    }}