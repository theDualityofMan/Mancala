package mancala;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class AbstractGameRules implements Serializable{
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public AbstractGameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int pitNum) {
        boolean sideEmpty = true;

        if(pitNum >= 1 && pitNum <= 6){
            for(int x = 1;x <= 6;x++){
                if(gameBoard.getNumStones(x) != 0){
                    sideEmpty = false;
                }
            }
        } else{
            for(int x = 7;x <= 12;x++){
                if(gameBoard.getNumStones(x) != 0){
                    sideEmpty = false;
                }
            }
        }
        return sideEmpty;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    public int getCurPlayer() {
        return currentPlayer;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);
    
    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    abstract boolean isExtraTurn();

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.
        Store storeOne = new Store();
        storeOne.setOwner(one);
        one.setStore(storeOne);
        gameBoard.setStore(storeOne, 1);

        Store storeTwo = new Store();
        storeTwo.setOwner(two);
        two.setStore(storeTwo);
        gameBoard.setStore(storeTwo, 2);

        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    public void clearSide(final int playerNum){
        if(playerNum == 2){
            for(int x = 12; x >= 7; x--){
                getDataStructure().addToStore(2, getDataStructure().removeStones(x));
            }
        } else {
            for(int x = 1; x <= 6; x++){
                getDataStructure().addToStore(1, getDataStructure().removeStones(x));
            }
        }
    }

    // @Override
    // public String toString(){
        
    // }
}
