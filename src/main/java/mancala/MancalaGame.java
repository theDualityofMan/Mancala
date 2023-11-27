package mancala;
import java.io.Serializable;

public class MancalaGame implements Serializable{
    public static final long serialVersionUID = 348743;

    private GameRules board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player winner;

    //returns the gamerules
    public GameRules getBoard(){
        return board;
    }

    //returns current player
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Gets the number of stones at given pit
     * @param pitNum given pit
     * @return number of stones found at pit
     */
    public int getNumStones(final int pitNum){
        return board.getNumStones(pitNum);
    }

    /**
     * Gets the number of stones at store of given player and returns it
     * @param player which player to get storecount from
     * @return number of stones found at store
     */
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

    public void setBoard(GameRules theBoard){
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
    @Override
    public String toString(){
        return "Why would you ask us to make this method if the entire point was to NOT use system in or system out?";
    }

}