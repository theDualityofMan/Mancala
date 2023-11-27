package mancala;
import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable{

    private AbstractGameRules board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player winner;

    public AbstractGameRules getBoard(){
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
        if(getStoreCount(playerOne) > getStoreCount(playerTwo)){
            winner = playerOne;
        } else{
            winner = playerTwo;
        }
        return winner;
    }

    public boolean isGameOver(){
        if(board.isSideEmpty(1) && currentPlayer == playerOne){
            board.clearSide(2);
        } else if(board.isSideEmpty(7) && currentPlayer == playerTwo){
            board.clearSide(1);
        } else{ 
            return false;
        }
        return true;
        
    }

    public int move(int startPit){
        int playerNum;
        int numStored;
        if(startPit >= 1 && startPit <= 6){
            playerNum = 1;
        } else{
            playerNum = 2;
        }
        try {
            numStored = board.moveStones(startPit, playerNum);
        } catch (InvalidMoveException e){
            return -1;
        }
        return numStored;
    }

    public boolean isExtraTurn(){
        return board.isExtraTurn();
    }

    public void setBoard(AbstractGameRules theBoard){
        board = theBoard;
        board.resetBoard();
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