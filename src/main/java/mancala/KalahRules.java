package mancala;

import java.io.Serializable;

public class KalahRules extends GameRules implements Serializable{  

    public static final long serialVersionUID = 4323438;

    private int stoppingPoint;
    private boolean extraTurn;

    public KalahRules(){
        super();
        stoppingPoint = 0;
        extraTurn = false;
    }

    @Override
    public boolean isExtraTurn(){
        return extraTurn;
    }

    //Moves stones for the player starting from a specific pit
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if(startPit < 1 || startPit > 12 || getNumStones(startPit) == 0 || getCurPlayer() != playerNum){
            throw new InvalidMoveException();
        }
        distributeStones(startPit);
        if(stoppingPoint == 6 && playerNum == 1 || stoppingPoint == 13 && playerNum == 2){
            extraTurn = true;
        } else{
            extraTurn = false;
        }
        if(stoppingPoint <= 6){
            stoppingPoint ++;
        }
        if(stoppingPoint >= 1 && stoppingPoint <= 12){
            addToStore(playerNum, captureStones(stoppingPoint));
        }
        return getDataStructure().getStoreCount(playerNum);
    }


    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    @Override
    public int distributeStones(final int startingPoint){

        int stonesToMove = removeStones(startingPoint);
        getDataStructure().setIterator(startingPoint, getCurPlayer(), false);
        Countable currentSpot;
        for(int x = 0; x < stonesToMove; x++){
            currentSpot = getDataStructure().next();
            currentSpot.addStone();
        }
        stoppingPoint = getDataStructure().getIterator();

        return stonesToMove;

    }

    //Captures stones from the opponent's pits
    @Override
    public int captureStones(final int stoppingPoint){

        if(getNumStones(stoppingPoint) == 1 && 
        ((stoppingPoint >= 1 && stoppingPoint <= 6 && getCurPlayer() == 1) || 
        (stoppingPoint >= 7 && stoppingPoint <= 12 && getCurPlayer() == 2))){
            System.out.println(getNumStones(stoppingPoint));
            return removeStones(stoppingPoint) + removeStones(stoppingPoint + ((6 - stoppingPoint)*2) + 1);
        }

        return 0;
    }
}