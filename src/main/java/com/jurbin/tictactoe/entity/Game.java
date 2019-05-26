package com.jurbin.tictactoe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String playerOneName;
    String playerTwoName;
    Player[][] board = new Player[3][3];
    Player playersTurn = Player.X;
    GameState state = GameState.INPROGRESS;
    String message = "";

    public Game() {
    }

    public Game(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Player.BLANK;
            }
        }
    }

    public void takeTurn(Integer row, Integer column) {
        if(row < 0 || row > 2 || column < 0 || column > 2) {
            message = "Row/Column values must be 1, 2, or 3";
            this.setState(GameState.INVALID);
        }else if(board[row][column].equals(Player.BLANK)) {
            board[row][column] = playersTurn;
            checkGameStatus();
            playersTurn = playersTurn.equals(Player.X) ? Player.O : Player.X;
        }else{
            message = "Position already taken";
            this.setState(GameState.INVALID);
        }
    }

    private void checkGameStatus() {
        if (board[0][0].equals(playersTurn) && board[0][1].equals(playersTurn) && board[0][2].equals(playersTurn) ||
                board[1][0].equals(playersTurn) && board[1][1].equals(playersTurn) && board[1][2].equals(playersTurn) ||
                board[2][0].equals(playersTurn) && board[2][1].equals(playersTurn) && board[2][2].equals(playersTurn) ||
                board[0][0].equals(playersTurn) && board[1][0].equals(playersTurn) && board[2][0].equals(playersTurn) ||
                board[0][1].equals(playersTurn) && board[1][1].equals(playersTurn) && board[2][1].equals(playersTurn) ||
                board[0][2].equals(playersTurn) && board[1][2].equals(playersTurn) && board[2][2].equals(playersTurn) ||
                board[0][0].equals(playersTurn) && board[1][1].equals(playersTurn) && board[2][2].equals(playersTurn) ||
                board[0][2].equals(playersTurn) && board[1][1].equals(playersTurn) && board[2][0].equals(playersTurn)) {
            if (playersTurn.equals(Player.X))
                this.setState(GameState.PLAYERXWIN);
            else
                this.setState(GameState.PLAYEROWIN);

            message = "Congratulation player " + playersTurn;
        } else {
            this.setState(GameState.TIE);
            message = "Game has tied";

            for (Player[] row : board) {
                for (Player cell : row) {
                    if (cell.equals(Player.BLANK)) {
                        this.setState(GameState.INPROGRESS);
                        message = "";
                    }
                }
            }

        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public Player[][] getBoard() {
        return board;
    }

    public void setBoard(Player[][] board) {
        this.board = board;
    }

    public Player getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(Player playersTurn) {
        this.playersTurn = playersTurn;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", playerOneName='" + playerOneName + '\'' +
                ", playerTwoName='" + playerTwoName + '\'' +
                ", board=" + Arrays.toString(board) +
                ", playersTurn=" + playersTurn +
                ", state=" + state +
                '}';
    }
}
