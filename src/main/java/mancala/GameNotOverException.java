package mancala;

public class GameNotOverException extends Exception {
    public GameNotOverException() {
        super("Error: Game not over");
    }
}