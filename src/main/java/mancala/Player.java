package mancala;
import java.io.*;

public class Player implements Serializable{

    private String name;
    private Store myStore;

    //Constructor
    public Player(){
        name = "No name given";
    }

    public Player(final String newName){
        name = newName;
    }

    //Gets the name of the player
    /* default */ public String getName(){
        return name;
    }

    //Sets the players name
    /* default */ public void setName(final String newName){
        name = newName;
    }

    //Gets the player's store where they collect stones
    /* default */ Store getStore(){
        return myStore;
    }

    //Gets the count of the number of stones in the player's store where they collect stones 
    /* default */ int getStoreCount(){
        return myStore.getTotalStones();
    }

    //Sets the player's store
    /* default */ void setStore(final Store store){
        myStore = store;
    }

    @Override
    public String toString(){
        return getName();
    }

}