package eu.mrndesign.matned.portfolioapp.captcha;

public class Maze {

    public static Field[][] generate(int rows, int cols){
        Maze maze = new Maze(cols, rows);
        return maze.matrix;
    }

    int numberOfFields;
    private Field[][] matrix;
    private int openFields = 1;

    private Maze(int column, int row) {
        matrix = new Field[row][column];
        numberOfFields = column * row;
        generateMaze();
        generateStartAndTreasureLocation(true);
    }

    private void generateStartAndTreasureLocation(boolean flag) {
        int row = (int) (Math.random() * matrix.length - 1);
        int col = (int) (Math.random() * matrix[0].length - 1);
        try {
            if (flag) matrix[row][col].isPlayer = true;
            else {
                if (matrix[row][col].isPlayer)
                    generateStartAndTreasureLocation(false);
                matrix[row][col].isTreasure = true;
            }
        } catch (IndexOutOfBoundsException e) {
            if (flag) generateStartAndTreasureLocation(true);
            else generateStartAndTreasureLocation(false);
        }
        if (flag) generateStartAndTreasureLocation(false);
    }


    private void generateMaze() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Field();
            }
        }
        matrix[0][0] = new Field();
        matrix[0][0].isOpen = true;
        generateMazeInner(0, 0);
    }

    private synchronized void generateMazeInner(int y, int x) {
        int randomDirection = (int) (Math.random() * (100)) + 1;
        if (openFields < (numberOfFields - 10)) {
            if (randomDirection <= 25 && (y - 1) >= 0) {
                generateMazeInnerStatements(y, x, -1, 0);
            } else if (randomDirection > 25 && randomDirection <= 50 && (x + 1) < matrix[y].length) {
                generateMazeInnerStatements(y, x, 0, 1);
            } else if (randomDirection > 50 && randomDirection <= 75 && (y + 1) < matrix.length) {
                generateMazeInnerStatements(y, x, 1, 0);
            } else if (randomDirection > 75 && randomDirection <= 100 && (x - 1) >= 0) {
                generateMazeInnerStatements(y, x, 0, -1);
            } else {
                generateMazeInner(y, x);
            }
        }

    }

    private synchronized void generateMazeInnerStatements(int y, int x, int yC, int xC) {
        if (!matrix[y + yC][x + xC].isOpen) {
            if (yC == -1 && xC == 0) {
                matrix[y][x].topNeighbour = true;
                matrix[y + yC][x + xC].bottomNeighbour = true;
            }
            if (yC == 1 && xC == 0) {
                matrix[y][x].bottomNeighbour = true;
                matrix[y + yC][x + xC].topNeighbour = true;
            }
            if (yC == 0 && xC == 1) {
                matrix[y][x].rightNeighbour = true;
                matrix[y + yC][x + xC].leftNeighbour = true;
            }
            if (yC == 0 && xC == -1) {
                matrix[y][x].leftNeighbour = true;
                matrix[y + yC][x + xC].rightNeighbour = true;
            }
            matrix[y + yC][x + xC].isOpen = true;
            openFields++;
        }
        generateMazeInner(y + yC, x + xC);
    }


    static class Field {
        private boolean isOpen;
        private boolean topNeighbour;
        private boolean bottomNeighbour;
        private boolean rightNeighbour;
        private boolean leftNeighbour;
        private boolean isTreasure;
        private boolean isPlayer;


        Field() {
            this.isOpen = false;
            this.topNeighbour = false;
            this.bottomNeighbour = false;
            this.rightNeighbour = false;
            this.leftNeighbour = false;
            this.isTreasure = false;
            this.isPlayer = false;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public boolean isTopNeighbour() {
            return topNeighbour;
        }

        public boolean isBottomNeighbour() {
            return bottomNeighbour;
        }

        public boolean isRightNeighbour() {
            return rightNeighbour;
        }

        public boolean isLeftNeighbour() {
            return leftNeighbour;
        }

        public boolean isTreasure() {
            return isTreasure;
        }

        public boolean isPlayer() {
            return isPlayer;
        }
    }

}

