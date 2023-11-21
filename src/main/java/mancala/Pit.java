package mancala;
import java.io.*;

public class Pit implements Serializable{

    private int numStones;
    private int savedStones;

    public Pit(){
        numStones = 0;
        savedStones = 0;
    }

    //Gets the number of stones in the pit
    /* default */int getStoneCount(){
        return numStones;
    }
    
    //Adds a stone to the pit
    /* default */void addStone(){
        numStones++;
    }

    /* default */void setStone(final int num){
        numStones = num;
    }

    //Removes and returns the stones from the pit
    /* default */int removeStones(){
        savedStones = numStones;
        numStones = 0;
        return savedStones;
    }

    @Override
    public String toString(){
        return Integer.toString(getStoneCount());
    }
}