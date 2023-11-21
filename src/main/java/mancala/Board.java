package mancala;
import java.util.ArrayList;

public class Board{

    private ArrayList<Pit> pitList;
    private ArrayList<Store> storeList;
    private boolean extraTurn;
    private boolean captureExtra;
    private Store storeOne;
    private Store storeTwo;
    private Player curPlayer;
    private Player playerOne;
    private Player playerTwo;

    public Board(){
        pitList = new ArrayList<>();
        storeList = new ArrayList<>();
        extraTurn = false;
        captureExtra = false;
        setUpPits();
        setUpStores();
        initializeBoard();
    }

    public void setExtraTurn(final Boolean newState){
        extraTurn = newState;
    }

    public boolean getExtraTurn(){
        return extraTurn;
    }

    public void setCaptureExtra(final Boolean newState){
        captureExtra = newState;
    }

    public boolean getCaptureExtra(){
        return captureExtra;
    }

    //Returns the list of pits in the board, not including the stores
    public ArrayList<Pit> getPits(){
        return pitList;
    }

    //Returns the list of stores in the board
    public ArrayList<Store> getStores(){
        return storeList;
    }

    public Pit getPit(final int num){
        return pitList.get(num);
    }

    //Establishes 12 empty Pits in the board
    /* default */void setUpPits(){
        for(int x = 0;x < 12;x++){
            pitList.add(x, new Pit());
        }
    }

    //Establishes 2 empty Stores in the board
    /* default */void setUpStores(){
        storeList.add(0, new Store());
        storeList.add(1, new Store());
        storeOne = storeList.get(0);
        storeTwo = storeList.get(1);
    }

    //Initializes the board by distributing stones
    /* default */void initializeBoard(){
        for(final Pit pit: pitList){
            pit.setStone(4);
        }
    }

    //Connects players to their stores
    /* default */void registerPlayers(final Player one, final Player two){
        one.setStore(storeOne);
        two.setStore(storeTwo);
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
        playerOne = one;
        playerTwo = two;
    }

    //Completely clears the board
    /* default */void clearBoard(){
        for(final Pit pit: pitList){
            pit.removeStones();
        }
        storeOne.emptyStore();
        storeTwo.emptyStore();
    }

    //Resets the board by redistributing stones but retains the players
    /* default */void resetBoard(){
        clearBoard();
        setUpPits();
        initializeBoard();
    }

    //Captures stones from the opponent's pits
    /* default */int captureStones(int stoppingPoint) throws PitNotFoundException{
        stoppingPoint -= 1;
        
        if(stoppingPoint > 11 || stoppingPoint < 0){
            throw new PitNotFoundException();
        }
        return getPit(stoppingPoint + ((6 - stoppingPoint) * 2) - 1).removeStones() 
            + getPit(stoppingPoint).removeStones();
      
    }
    //checks if move is elligble to capture opponents stones
    /* default */int checkIfCapture(int startingPoint, final int numStones){
        startingPoint -= 1;
        int stoppingPoint = (numStones + startingPoint)%12;

        if((stoppingPoint > 6 && curPlayer == playerOne)
         || (stoppingPoint > 12 && curPlayer == playerTwo)){
            stoppingPoint -= 1; //Subtract 1 to account for store
        }
        stoppingPoint = stoppingPoint%12;

        if(getPit(stoppingPoint).getStoneCount() == 0 && ((stoppingPoint >= 0 && stoppingPoint <= 5 && curPlayer == playerOne) 
                || (stoppingPoint >= 6 && stoppingPoint <= 11 && curPlayer == playerTwo))){
                setCaptureExtra(true);
                return stoppingPoint;
        }
    
        return 0;
    }

    //Checks if last stone is deposited in the store
    /* default */void checkIfExtra(int startingPoint, final int numStones){
        startingPoint -= 1;
        if (((numStones + startingPoint)%12 == 6 && curPlayer == playerOne) 
            || ((numStones + startingPoint)%12 == 0 && curPlayer == playerTwo)){
            setExtraTurn(true);
        }
    }

    //Helper method that distributes stones into pits and stores, skipping the opponent's store
    /* default */int distributeStones(int startingPoint) throws PitNotFoundException{
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
    
    //Gets the number of stones in a specific pit
    /* default */int getNumStones(int pitNum) throws PitNotFoundException{
        pitNum -= 1;
        if(pitNum < 0 || pitNum > 11){
            throw new PitNotFoundException();
        }
        return getPit(pitNum).getStoneCount();
    }


    //Indicates whether one side of the board is empty
    /* default */boolean isSideEmpty(int pitNum) throws PitNotFoundException{
        pitNum -= 1;
        if(pitNum >= 0 && pitNum <= 5){
            for(int x = 0;x < 6;x++){
                if(getPit(x).getStoneCount() != 0){
                    return false;
                }
            }
        }else if(pitNum >= 6 && pitNum <= 11){
            for(int x = 6;x < 12;x++){
                if(getPit(x).getStoneCount() != 0){
                    return false;
                }
            }
        } else {
            throw new PitNotFoundException();
        }
        return true;
    }

    //Moves stones for the player starting from a specific pit
    /* default */int moveStones(int startPit, final Player player) throws InvalidMoveException {
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

    @Override
    public String toString(){
        String pBoard = "";

        for(int x = 11; x >= 6; x--){
            pBoard += ("      " + getPit(x).toString());
        }

        pBoard += ("\n" + storeOne.toString() 
        +  "    -------------------------------------     " + storeTwo.toString() + "\n");

        for(int x = 0; x <= 5; x++){
            pBoard += ("      " + getPit(x).toString());
        }


        return pBoard;
    }

}