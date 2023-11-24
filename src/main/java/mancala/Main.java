import mancala.MancalaGame;
import mancala.Saver;

public class Main{
    public static void main(String[] args){
        MancalaGame newGame = new MancalaGame();
        Saver newSave = new Saver();

        Saver.saveObject(newGame, "game.ser");

    }
}