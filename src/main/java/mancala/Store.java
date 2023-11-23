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
    /* default */ void addStones(final int amount){
        numStones += amount;
    }

    @Override
    /* default */ void addStone(){
        numStones ++;
    }

    //Empties the store and returns the number of stones that were in it
    @Override
    /* default */ int removeStones(){
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
    /* default */ int getStoneCount(){
        return numStones;
    }

    //Sets the owner of the store
    /* default */ void setOwner(final Player newPlayer){
        player = newPlayer;
    }
}