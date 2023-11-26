package mancala;

public class AyoRules extends GameRules{

    //Moves stones for the player starting from a specific pit
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if(startPit < 1 || startPit > 12 || getNumStones(startPit) == 0 || getCurPlayer() != playerNum){
            throw new InvalidMoveException();
        }
        int stoppingPoint = distributeStones(startPit);
        
        while ((stoppingPoint >= 0 && stoppingPoint <= 12 && stoppingPoint != 6)){
            if(stoppingPoint <= 6){
                stoppingPoint++;
            }
            if(getDataStructure().getNumStones(stoppingPoint) > 1){
                System.out.println(stoppingPoint);
                stoppingPoint = distributeStones(stoppingPoint);
            } else {
                break;
            }
           }
        if(stoppingPoint >= 0 && stoppingPoint <= 12 && stoppingPoint != 6){
            getDataStructure().addToStore(playerNum, captureStones(stoppingPoint));
        }
        return 0;
    }


    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    @Override
    public int distributeStones(final int startingPoint){

        int stonesToMove = getDataStructure().removeStones(startingPoint);
        getDataStructure().setIterator(startingPoint, getCurPlayer(), true);
        Countable currentSpot;
        for(int x = 0; x < stonesToMove; x++){
            currentSpot = getDataStructure().next();
            currentSpot.addStone();
        }
        return getDataStructure().getIterator();

    }

    //Captures stones from the opponent's pits
    @Override
    public int captureStones(final int stoppingPoint){

        if(getDataStructure().getNumStones(stoppingPoint) == 1 && 
        ((stoppingPoint >= 1 && stoppingPoint <= 6 && getCurPlayer() == 1) || 
        (stoppingPoint >= 7 && stoppingPoint <= 12 && getCurPlayer() == 2))){
            return getDataStructure().removeStones(stoppingPoint + ((6 - stoppingPoint)*2)+1);
        }

        return 0;
    }
}