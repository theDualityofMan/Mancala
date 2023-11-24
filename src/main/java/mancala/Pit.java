package mancala;
import java.io.Serializable;

public class Pit implements Serializable, Countable{

    private int numStones;
    private int savedStones;

    public Pit(){
        numStones = 0;
        savedStones = 0;
    }

    //Gets the number of stones in the pit
    @Override
    public int getStoneCount(){
        return numStones;
    }
    
    //Adds a stone to the pit
    @Override
    public void addStone(){
        numStones++;
    }

    @Override
    public void addStones(final int num){
        numStones = num;
    }

    @Override 
    //Removes and returns the stones from the pit
    public int removeStones(){
        savedStones = numStones;
        numStones = 0;
        return savedStones;
    }
}