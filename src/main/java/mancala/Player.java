package mancala;
import java.io.Serializable;

public class Player implements Serializable{

    private Store myStore;
    private UserProfile newPlayer = new UserProfile();

    //Constructor
    public Player(){
        newPlayer.setUserName("Unnamed Player");
    }

    public Player(final String newName){
        newPlayer.setUserName(newName);
    }

    //Gets the name of the player
    /* default */ public String getName(){
        return newPlayer.getUserName();
    }

    //Sets the players name
    /* default */ public void setName(final String newName){
        newPlayer.setUserName(newName);
    }

    //Gets the player's store where they collect stones
    /* default */ Store getStore(){
        return myStore;
    }

    //Gets the count of the number of stones in the player's store where they collect stones 
    /* default */ int getStoreCount(){
        return myStore.getStoneCount();
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