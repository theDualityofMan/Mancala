package mancala;
import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable{

    private Board gameBoard;
    private Player curPlayer;
    private Player nextPlayer;
    private ArrayList<Player> playerList;
    private boolean isOver;

    public MancalaGame(){
        playerList = new ArrayList<>();
        isOver = false;
    }

    public void setIsOver(final boolean overState){
        isOver = overState;
    }

    //Gets the board for the game
    public Board getBoard(){
        return gameBoard;
    }

    //Gets the current player
    public Player getCurrentPlayer(){
        return curPlayer;
    }

    public void setNextPlayer(final Player player){
        nextPlayer = player;
    }

    public Player getNextPlayer(){
        return nextPlayer;
    }

    //Gets the number of stones in a specific pit
    public int getNumStones(final int pitNum) throws PitNotFoundException{
        try{
            final int returnStones = gameBoard.getNumStones(pitNum);
            return returnStones;
        } catch(PitNotFoundException e){
            throw e;
        }
    }

    //Gets the players for the game
    public ArrayList<Player> getPlayers(){
        return playerList;
    }

    //Gets the total number of stones in a player's store
    public int getStoreCount(final Player player) throws NoSuchPlayerException{
        if(player == playerList.get(0) || player == playerList.get(1)){
            return player.getStoreCount();
        } else {
            throw new NoSuchPlayerException();
        }
    }

    //Gets the winner of the game
    public Player getWinner() throws GameNotOverException{
        Player winner = null;
        if(isGameOver()){
            if(curPlayer.getStoreCount() > nextPlayer.getStoreCount()){
                winner = curPlayer;
            } else if(curPlayer.getStoreCount() < nextPlayer.getStoreCount()){
                winner = nextPlayer;
            } 
            return winner;
        } else {
            throw new GameNotOverException();
        }
    }

    //Checks if the game is over
    public Boolean isGameOver(){
        isOver = false;
        try{
            isOver = gameBoard.isSideEmpty(1) || gameBoard.isSideEmpty(7);
        } catch (PitNotFoundException e){
            System.out.println(e);
        }
        
        return isOver;
    }

    //Makes a move for the current player
    public int move(int startPit) throws InvalidMoveException{
        startPit -= 1;
        int sum = 0;
        try{
            gameBoard.moveStones(startPit + 1, curPlayer);
        } catch (InvalidMoveException e){
            throw e;
        }

        if(isGameOver()){
            return 0;
        } else {
            if(curPlayer == playerList.get(0)){
                for(int x = 0; x < 6; x++){
                    if (x != startPit){
                        sum += gameBoard.getPits().get(x).getStoneCount();
                    }
                }
            }
            if(curPlayer == playerList.get(1)){
                for(int x = 6; x < 12; x++){
                    if (x != startPit){
                        sum += gameBoard.getPits().get(x).getStoneCount();
                    }
                }
            }
        }
        return sum;
    }

    public void sumOpponent(final Player cPlayer){
        int sum = 0;

        if(cPlayer == playerList.get(1)){
            for(int x = 0; x <= 5; x++){
                sum += gameBoard.getPits().get(x).removeStones();
            }
            gameBoard.getStores().get(0).addStones(sum);
        }
        if(cPlayer == playerList.get(0)){
            for(int x = 6; x <= 11; x++){
                sum += gameBoard.getPits().get(x).removeStones();
            }
            gameBoard.getStores().get(1).addStones(sum);
        }
    }

    /* default */void swapPlayers(){
        final Player tempPlayer = curPlayer;
        curPlayer = nextPlayer;
        nextPlayer = tempPlayer;
    }

    public void turnLogic(){   
        swapPlayers();
        if(gameBoard.getExtraTurn()){
            swapPlayers();
        }
        gameBoard.setExtraTurn(false);
    }

    //Sets the board for the game
    public void setBoard(final Board theBoard){
        gameBoard = theBoard;
    }
    
    //Sets the current player
    public void setCurrentPlayer(final Player player){
        curPlayer = player;
    }

    //Sets the players for the game
    public void setPlayers(final Player onePlayer, final Player twoPlayer){
        playerList.add(onePlayer);
        playerList.add(twoPlayer);
        curPlayer = playerList.get(0);
        nextPlayer = playerList.get(1);
        gameBoard.registerPlayers(onePlayer, twoPlayer);
    }
    
    //Starts a new game by resetting the board
    public void startNewGame(){
        gameBoard.resetBoard();
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