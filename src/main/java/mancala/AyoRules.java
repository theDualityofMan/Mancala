package mancala;

import java.io.Serializable;

public class AyoRules extends GameRules implements Serializable{    
    public static final long serialVersionUID = 4328743;

    private int stoppingPoint;

    public AyoRules(){
        super();
        stoppingPoint = 0;
    }

    @Override
    public boolean isExtraTurn(){
        return false;
    }

    //Moves stones for the player starting from a specific pit
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if(startPit < 1 || startPit > 12 || getNumStones(startPit) == 0 || getCurPlayer() != playerNum){
            throw new InvalidMoveException();
        }
        distributeStones(startPit);

        if(stoppingPoint >= 0 && stoppingPoint <= 12 && stoppingPoint != 6){
            if(stoppingPoint <= 6){
                stoppingPoint++;
            }
            addToStore(playerNum, captureStones(stoppingPoint));
        }
        return getDataStructure().getStoreCount(playerNum);
    }


    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    @Override
    public int distributeStones(final int startingPoint){
        stoppingPoint = startingPoint;
        getDataStructure().setIterator(stoppingPoint, getCurPlayer(), true);
        int firstStones = getNumStones(stoppingPoint);
        int stonesToMove;
        Countable currentSpot;

        do{
            stonesToMove = removeStones(stoppingPoint);
            for(int x = 0; x < stonesToMove; x++){
                currentSpot = getDataStructure().next();
                currentSpot.addStone();
            }
            stoppingPoint = getDataStructure().getIterator();
            
            if(stoppingPoint >= 0 && stoppingPoint <= 12 && stoppingPoint != 6){
                if(stoppingPoint <= 6){
                    stoppingPoint++;
                }
            } else {
                break;
            }

        } while (getNumStones(stoppingPoint) > 1);

        return firstStones;

    }

    //Captures stones from the opponent's pits
    @Override
    public int captureStones(final int stoppingPoint){
        int captured = 0;
        if(getNumStones(stoppingPoint) == 1 && 
        stoppingPoint >= 1 && stoppingPoint <= 6 && getCurPlayer() == 1 || 
        stoppingPoint >= 7 && stoppingPoint <= 12 && getCurPlayer() == 2){
            captured = removeStones(stoppingPoint + ((6 - stoppingPoint)*2)+1);
        }

        return captured;
    }
}