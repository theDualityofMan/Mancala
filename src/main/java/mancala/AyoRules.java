package mancala;

public class AyoRules extends GameRules{
    //Moves stones for the player starting from a specific pit
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if(startPit < 1 || startPit > 12 || getNumStones(startPit) == 0){
            throw new InvalidMoveException();
        }
        int stoppingPoint = distributeStones(startPit);
        while (getDataStructure().getNumStones(stoppingPoint) > 1) {
            stoppingPoint = distributeStones(stoppingPoint);
        }
        return 0;
    }


    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    @Override
    public int distributeStones(final int startingPoint){

        int stonesToMove = getDataStructure().removeStones(startingPoint);
        getDataStructure().setIterator(startingPoint + 1, getCurPlayer(), true);
        Countable currentSpot;
        for(int x = 0; x <= stonesToMove; x++){
            currentSpot = getDataStructure().next();
            currentSpot.addStone();
        }
        return getDataStructure().getIterator();

    }

    //Captures stones from the opponent's pits
    @Override
    public int captureStones(final int stoppingPoint){

        if(getDataStructure().getNumStones(stoppingPoint) == 1 && 
        ((stoppingPoint >= 0 && stoppingPoint <= 5 && getCurPlayer() == 1) || 
        (stoppingPoint >= 7 && stoppingPoint <= 12 && getCurPlayer() == 2))){
            return getDataStructure().removeStones(stoppingPoint + (6 - stoppingPoint)*2);
        }

        return 0;
    }
}
