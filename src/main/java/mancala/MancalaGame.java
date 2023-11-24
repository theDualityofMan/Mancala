package mancala;
import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable{

    private GameRules board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player winner;

    public MancalaGame(){

    }

    public GameRules getBoard(){
        return board;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getNumStones(final int pitNum){
        return board.getNumStones(pitNum);
    }

    public int getStoreCount(final Player player){
        int playerNum;

        if(player == playerOne){
            playerNum = 1;
        } else{
            playerNum = 2;
        }

        return board.getDataStructure().getStoreCount(playerNum);
    }

    public Player getWinner(){
        return winner;
    }

    public boolean isGameOver(){
        if(board.isSideEmpty(0) && currentPlayer == playerOne){
            winner = playerOne;
            return true;
        } else if(board.isSideEmpty(7) && currentPlayer == playerTwo){
            winner = playerTwo;
            return true;
        }
        return false;
    }

    public int move(int startPit) throws InvalidMoveException{
        int playerNum;
        int isExtraMove = 0;
        if(currentPlayer == playerOne){
            playerNum = 1;
        } else{
            playerNum = 2;
        }
        try {
            isExtraMove = board.moveStones(startPit, playerNum);
        } catch (InvalidMoveException e){
            throw e;
        }
        return isExtraMove;
    }

    public void setBoard(GameRules theBoard){
        board = theBoard;
    }

    public void setCurrentPlayer(Player player){
        int playerNum;
        currentPlayer = player;
        if(player == playerOne){
            playerNum = 1;
        } else{
            playerNum = 2;
        }
        board.setPlayer(playerNum);
    }

    public void setPlayers(Player onePlayer, Player twoPlayer){
        playerOne = onePlayer;
        playerTwo = twoPlayer;
        board.registerPlayers(onePlayer, twoPlayer);
    }

    public void startNewGame(){
        board.resetBoard();
    }

   
    //Generates a string representation of the game
    // @Override
    // public String toString(){
        
    // }

}