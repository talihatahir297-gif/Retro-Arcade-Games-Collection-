import java.util.Scanner;

class Pacman {
    public int x;
    public int y;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Ghost {
    public int x;
    public int y;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveGhost(char[][] grid, Pacman p) {
        int nextX = this.x;
        int nextY = this.y;

        if (p.x > this.x){
             nextX++;}
        else if (p.x < this.x){
             nextX--;}

        if (p.y > this.y) {
            nextY++;}
        else if (p.y < this.y) {
            nextY--;}

        if (nextY >= 0 && nextY < 10 && nextX >= 0 && nextX < 20) {
            if (grid[nextY][nextX] != '#') {
                this.x = nextX;
                this.y = nextY;
            }
        }
    }
}

class Maze {
    char[][] grid = {
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    public void printMaze(Pacman p, Ghost g1, Ghost g2) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (i == p.y && j == p.x) {
                    System.out.print("P");
                } else if (i == g1.y && j == g1.x) {
                    System.out.print("G");
                } else if (i == g2.y && j == g2.x) {
                    System.out.print("G");
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }
    }

    public boolean isFoodLeft() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (grid[i][j] == '.') return true;
            }
        }
        return false;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Pacman pacman = new Pacman(1, 1);
        Ghost ghost1 = new Ghost(2, 2);
        Ghost ghost2 = new Ghost(15, 7);

        Maze maze = new Maze();
        int score = 0;
        while (true) {
            maze.printMaze(pacman, ghost1, ghost2);
            System.out.println("Score: " + score);
            System.out.print("Enter move (w/a/s/d): ");
            char choice = input.next().charAt(0);

            int pX = pacman.x;
            int pY = pacman.y;

            if (choice == 'w') {
                pY--;
            } else if (choice == 's') {
                pY++;
            } else if (choice == 'a') {
                pX--;
            } else if (choice == 'd') {
                pX++;
            }

            if (pY >= 0 && pY < 10 && pX >= 0 && pX < 20 && maze.grid[pY][pX] != '#') {
                pacman.x = pX;
                pacman.y = pY;

                if (maze.grid[pY][pX] == '.') {
                    maze.grid[pY][pX] = ' ';
                    score += 5;
                }
            } else {
                maze.printMaze(pacman, ghost1, ghost2);
                System.out.println("Wall Collision! Game Over.");
                System.out.println("Final Score: " + score);
                break;
            }
            ghost1.moveGhost(maze.grid, pacman);
            ghost2.moveGhost(maze.grid, pacman);

            if (!maze.isFoodLeft()) {
                maze.printMaze(pacman, ghost1, ghost2);
                System.out.println("Winner! Sara food khatam ho gaya.");
                break;
            }

            if ((pacman.x == ghost1.x && pacman.y == ghost1.y) ||
                (pacman.x == ghost2.x && pacman.y == ghost2.y)) {

                maze.printMaze(pacman, ghost1, ghost2);
                System.out.println("Ghost Attack! Game Over.");
                break;
            }
        }
        input.close();
    }
}