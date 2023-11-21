package mancala;
import java.io.*;


public class Store implements Serializable{

    private int numStones;
    private int savedStones;
    private Player player;

    public Store(){
        numStones = 0;
        savedStones = 0;
    }
    //Adds stones to stores
    /* default */ void addStones(final int amount){
        numStones += amount;
    }

    //Empties the store and returns the number of stones that were in it
    /* default */ int emptyStore(){
        savedStones = numStones;
        numStones = 0;
        return savedStones;
    }

    //Gets the owner of the store
    /* default */ Player getOwner(){
        return player;
    }

    //Gets the total number of stones in the store
    /* default */ int getTotalStones(){
        return numStones;
    }

    //Sets the owner of the store
    /* default */ void setOwner(final Player newPlayer){
        player = newPlayer;
    }


    @Override
    public String toString(){
        return Integer.toString(getTotalStones());
    }
}