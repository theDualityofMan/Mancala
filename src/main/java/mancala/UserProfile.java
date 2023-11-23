package mancala;
import java.io.Serializable;
public class UserProfile implements Serializable{
    private String userName;
    private int nKalahPlayed = 0;
    private int nKalahWon = 0;
    private int nAyoPlayed = 0;
    private int nAyoWon = 0;

    public void setUserName(final String newName){
        userName = newName;
    }
    public String getUserName(){
        return userName;
    }
    public int getKalahPlayed(){
        return nKalahPlayed;
    }
    public int getKalahWon(){
        return nKalahWon;
    }
    public int getAyoPlayed(){
        return nAyoPlayed;
    }
    public int getAyoWon(){
        return nAyoWon;
    }
    
    public void incGamePlayed(String gameType){
        if(gameType == "kalah"){
            nKalahPlayed++;
        } else {
            nAyoPlayed++;
        }
    }

    public void incGameWon(String gameType){
        if(gameType == "kalah"){
            nKalahWon++;
        } else {
            nAyoWon++;
        }
    }
}