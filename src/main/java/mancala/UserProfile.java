package mancala;
import java.io.Serializable;
public class UserProfile implements Serializable{
    public static final long serialVersionUID = 99999743;
    private String userName;
    private int nKalahPlayed;
    private int nKalahWon;
    private int nAyoPlayed;
    private int nAyoWon;

    public UserProfile(){
        nKalahPlayed = 0;
        nKalahWon = 0;
        nAyoPlayed = 0;
        nAyoWon = 0;
    }
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