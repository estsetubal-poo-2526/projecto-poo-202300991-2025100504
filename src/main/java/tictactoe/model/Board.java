package tictactoe.model;

public class Board {
    public static final int SIZE = 3;

    private final Cell[][] cells;

    public Board() {
        cells = new Cell[SIZE][SIZE];

        for(int row = 0; row < SIZE; row++) {
            for(int column = 0; column < SIZE; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    public Cell getCell(int row, int column) throws InvalidMoveException {
        validatePosition(row, column);
        return cells[row][column];
    }

    public boolean isCellFree(int row, int column) throws InvalidMoveException {
        validatePosition(row, column);
        return cells[row][column].isEmpty();
    }

    public void markCell(int row, int column, Symbol symbol) throws InvalidMoveException {
        validatePosition(row, column);

        if(symbol == null || symbol == Symbol.EMPTY) {
            throw new InvalidMoveException("Símbolo inválido.");
        }

        if(!cells[row][column].isEmpty()) {
            throw new InvalidMoveException("Célula já está ocupada.");
        }

        cells[row][column].setSymbol(symbol);
    }

    public boolean isFull() {
        for(int row = 0; row < SIZE; row++) {
            for(int column = 0; column < SIZE; column++) {
                if(cells[row][column].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWinner(Symbol symbol) {
        if(symbol == null || symbol == Symbol.EMPTY) {
            return false;
        }
        return checkRows(symbol) || checkColumns(symbol) || checkDiagonals(symbol);
    }

    public void reset() {
        for(int row = 0; row < SIZE; row++) {
            for(int column = 0; column < SIZE; column++) {
                cells[row][column].clear();
            }
        }
    }

    private void validatePosition(int row, int column) throws InvalidMoveException {
        if (row < 0 || row >= SIZE || column < 0 || column >= SIZE) {
            throw new InvalidMoveException("Jogada fora do tabuleiro.");
        }
    }

    private boolean checkRows(Symbol symbol) {
        for(int row = 0; row < SIZE; row++) {
            if(cells[row][0].getSymbol() == symbol &&
               cells[row][1].getSymbol() == symbol &&
               cells[row][2].getSymbol() == symbol) {
               return true;
            }
        }
        return false;
    }

    private boolean checkColumns(Symbol symbol) {
        for(int column = 0; column < SIZE; column++) {
            if(cells[0][column].getSymbol() == symbol &&
               cells[1][column].getSymbol() == symbol &&
               cells[2][column].getSymbol() == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(Symbol symbol) {
        boolean mainDiagonal =
                cells[0][0].getSymbol() == symbol &&
                cells[1][1].getSymbol() == symbol &&
                cells[2][2].getSymbol() == symbol;

        boolean secondDiagonal =
                cells[0][2].getSymbol() == symbol &&
                cells[1][1].getSymbol() == symbol &&
                cells[2][0].getSymbol() == symbol;

        return mainDiagonal || secondDiagonal;
    }
}