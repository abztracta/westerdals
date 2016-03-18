package no.woact.pg4600.assignment1.ronesp13.models;

import static no.woact.pg4600.assignment1.ronesp13.models.GameState.DRAW;
import static no.woact.pg4600.assignment1.ronesp13.models.GameState.PLAYER_ONE_WON;
import static no.woact.pg4600.assignment1.ronesp13.models.GameState.PLAYER_TWO_WON;
import static no.woact.pg4600.assignment1.ronesp13.models.GameState.RUNNING;

public class TicTacToe {

    private int[][] board;
    private boolean isPlayerOneActive;
    private boolean hasMoved;

    private GameState state;

    public TicTacToe() {
        board = new int[3][3];
        isPlayerOneActive = true;
        state = RUNNING;
    }

    public GameState getGameState() {
        return state;
    }

    public boolean isSquareOccupied(int row, int column) {
        return board[row][column] != 0;
    }

    public void move(int row, int column) {
        if (isSquareOccupied(row, column)) {
            return;
        }
        board[row][column] = getActivePlayerId();
        hasMoved = true;
    }

    public String getPlayerIcon() {
        return isPlayerOneActive ? "X" : "O";
    }

    public boolean hasPlayerMoved() {
        return hasMoved;
    }

    public void update() {
        if (isWon(getActivePlayerId())) {
            setWinner();
            return;
        }
        if (isBoardFull()) {
            state = DRAW;
            return;
        }
        prepareForNextMove();
    }

    public boolean isGameOver() {
        return state != RUNNING;
    }

    private void setWinner() {
        state = isPlayerOneActive ? PLAYER_ONE_WON : PLAYER_TWO_WON;
    }

    private void prepareForNextMove() {
        hasMoved = false;
        isPlayerOneActive = !isPlayerOneActive;
    }

    private int getActivePlayerId() {
        return isPlayerOneActive ? 1 : 2;
    }

    private boolean isBoardFull() {
        for (int[] rows : board) {
            for (int square : rows) {
                if (square == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWon(int player) {
        for (int[] theBoard : board) { // check rows
            if (theBoard[0] == player && theBoard[1] == player && theBoard[2] == player) {
                return true;
            }
        }
        for (int i = 0; i < board.length; i++) { // check columns
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) { // check diagonal
            return true;
        }
        return board[0][2] == player && board[1][1] == player && board[2][0] == player; // check diagonal
    }
}
