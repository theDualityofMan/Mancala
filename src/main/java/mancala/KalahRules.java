package mancala;

public class KalahRules extends GameRules{

    private boolean extraTurn = false;
    private boolean captureExtra = false;

    //Moves stones for the player starting from a specific pit
    @Override
    public int moveStones(int startPit, final Player player) throws InvalidMoveException {
        startPit -= 1;
        Store playerStore = player.getStore();
        int prevStoreStones = playerStore.getTotalStones();
        int afterStoreStones;
        int numStones;
        curPlayer = player;

        try{
            if(getPit(startPit).getStoneCount() == 0){
                throw new InvalidMoveException();
            }
        } catch(Exception e){
            throw new InvalidMoveException();
        }

        try {
            numStones = distributeStones(startPit + 1);
            checkIfExtra(startPit + 1, numStones);
            afterStoreStones = playerStore.getTotalStones();
            return afterStoreStones - prevStoreStones;
        } catch (PitNotFoundException e){
            throw new InvalidMoveException();
        }
        
    }


    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    @Override
    public int distributeStones(int startingPoint) throws PitNotFoundException{
        startingPoint -= 1;
        int whichPlayer;
        int offset = 0;
        boolean atStore = true;
        

        if(!((startingPoint >= 0 && startingPoint <= 5 && curPlayer == playerOne) 
            || (startingPoint >= 6 && startingPoint <= 11 && curPlayer == playerTwo))){
            throw new PitNotFoundException();
        }

        int numStones = getPit(startingPoint).removeStones();

        int stoppingPoint = checkIfCapture(startingPoint + 1, numStones);
        
        for(int x = 1; x < numStones + 1;x++){
            if(startingPoint + x + offset > 11){
                offset -= 12;
                atStore = true;
            }
            if(atStore && ((startingPoint + x + offset == 0 && curPlayer == playerOne) 
            || (startingPoint + x + offset == 6 && curPlayer == playerTwo))){
                curPlayer.getStore().addStones(1);
                offset--;
                atStore = false;
            } else {
                getPit(startingPoint + x + offset).addStone();
            }
        }

        if(captureExtra){
            curPlayer.getStore().addStones(captureStones(stoppingPoint + 1));
            setCaptureExtra(false);
        }

        return numStones;
    }

    //Captures stones from the opponent's pits
    @Override
    public int captureStones(int stoppingPoint) throws PitNotFoundException{
        stoppingPoint -= 1;
        
        if(stoppingPoint > 11 || stoppingPoint < 0){
            throw new PitNotFoundException();
        }
        return getPit(stoppingPoint + ((6 - stoppingPoint) * 2) - 1).removeStones() 
            + getPit(stoppingPoint).removeStones();
      
    }
}