package mancala;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("Error: Invalid move detected");
    }
}