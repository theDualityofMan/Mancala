package mancala;
import java.io.Serializable;


public class Store implements Serializable, Countable{

    private int numStones;
    private int savedStones;
    private Player player;

    public Store(){
        numStones = 0;
        savedStones = 0;
    }
    //Adds stones to stores
    @Override
    public void addStones(final int amount){
        numStones += amount;
    }

    @Override
    public void addStone(){
        numStones ++;
    }

    //Empties the store and returns the number of stones that were in it
    @Override
    public int removeStones(){
        savedStones = numStones;
        numStones = 0;
        return savedStones;
    }

    //Gets the owner of the store
    /* default */ Player getOwner(){
        return player;
    }

    //Gets the total number of stones in the store
    @Override
    public int getStoneCount(){
        return numStones;
    }

    //Sets the owner of the store
    /* default */ void setOwner(final Player newPlayer){
        player = newPlayer;
    }
}