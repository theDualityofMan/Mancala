package mancala;

public class AyoRules extends GameRules{
    @Override
    public int moveStones(int startPit);

    @Override
    public int distributeStones(int startPit);

    @Override
    public int captureStones(int stoppingPoint);
}
