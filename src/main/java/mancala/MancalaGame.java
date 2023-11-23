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

    public int move(int startPit){
        int playerNum;
        if(currentPlayer == playerOne){
            playerNum = 1;
        } else{
            playerNum = 2;
        }
        return board.moveStones(startPit, playerNum);
    }

    public void setBoard(GameRules theBoard){
        board = theBoard;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
        board.setCurrentPlayer(player);
    }

    public void setPlayers(Player onePlayer, Player twoPlayer){
        playerOne = onePlayer;
        playerTwo = twoPlayer;
        registerPlayers(onePlayer, twoPlayer);
    }

    public void startNewGame(){
        board.resetBoard();
    }

   
    //Generates a string representation of the game
    @Override
    public String toString(){
         String pGame = "";

         pGame += "__________________________________________________\n\n ";

        for(int x = 12; x >= 10; x--){
            pGame += ("   (" + x + ")");
        }

        for(int x = 9; x >= 7; x--){
            pGame += ("    (" + x + ")");
        }

        pGame += "\n" + gameBoard.toString() + "\n ";
        
        for(int x = 1; x <= 6; x++){
            pGame += ("    (" + x + ")");
        }

        pGame += "\n__________________________________________________\n";

        return pGame;
    }

}