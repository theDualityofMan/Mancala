package mancala;
import java.io.Serializable;
public class UserProfile implements Serializable{
    public static final long serialVersionUID = 99999743;
    private String userName;
    private int nKalahPlayed;
    private int nKalahWon;
    private int nAyoPlayed;
    private int nAyoWon;

    //constructor
    public UserProfile(){
        nKalahPlayed = 0;
        nKalahWon = 0;
        nAyoPlayed = 0;
        nAyoWon = 0;
    }

    /**
     * Takes in a new string and sets userName to it
     * @param newName the name to set userName to
     */
    public void setUserName(final String newName){
        userName = newName;
    }

    //returns userName
    public String getUserName(){
        return userName;
    }

    //returns number of kalah games played
    public int getKalahPlayed(){
        return nKalahPlayed;
    }

    //returns number of kalah games won
    public int getKalahWon(){
        return nKalahWon;
    }

    //returns number of ayo games played
    public int getAyoPlayed(){
        return nAyoPlayed;
    }

    //returns number of ayo games won
    public int getAyoWon(){
        return nAyoWon;
    }
    
    /**
     * takes in the game type as a string, and increments games played of that said game type by one
     * @param gameType string representation of desired game to increment
     */
    public void incGamePlayed(String gameType){
        if(gameType == "kalah"){
            nKalahPlayed++;
        } else {
            nAyoPlayed++;
        }
    }


    /**
     * takes in the game type as a string, and increments games won of that said game type by one
     * @param gameType string representation of desired game to increment
     */
    public void incGameWon(String gameType){
        if(gameType == "kalah"){
            nKalahWon++;
        } else {
            nAyoWon++;
        }
    }
}