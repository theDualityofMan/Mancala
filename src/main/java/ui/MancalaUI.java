package ui;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;

import mancala.Saver;
import mancala.MancalaGame;
import mancala.Player;
import mancala.KalahRules;
import mancala.AyoRules;
import mancala.UserProfile;

import java.io.*;


public class MancalaUI extends JFrame{
    private JPanel gameContainer;
    private JLabel messageLabel;
    private JMenuBar menuBar;
    private MancalaGame game;
    private Saver saveGame;
    private PositionAwareButton[][] buttons;
    private Player playerOne;
    private Player playerTwo;
    private JLabel p1n;
    private JLabel p2n;
    private JLabel p1KalahWon;
    private JLabel p1KalahPlayed;
    private JLabel p1AyoWon;
    private JLabel p1AyoPlayed;
    private JLabel p2KalahWon;
    private JLabel p2KalahPlayed;
    private JLabel p2AyoWon;
    private JLabel p2AyoPlayed;
    private String gameType;

    public MancalaUI(String title){
        super();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        game = new MancalaGame();
        basicSetUp(title);
        setupGameContainer();
        add(gameContainer, BorderLayout.CENTER);
        add(makeButtonPanel(), BorderLayout.WEST);
        add(messageBox(), BorderLayout.SOUTH);
        add(userProfileDisplay(), BorderLayout.EAST);
        makeMenu();
        setJMenuBar(menuBar);
        pack();
    }
    
    private void basicSetUp(String title){
        this.setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void newKalahGame(){
        game.setBoard(new KalahRules());
        gameType = "kalah";
    }

    protected void newAyoGame(){
        game.setBoard(new AyoRules());
        gameType = "ayo";
    }

    protected void setPlayers(){
        game.setPlayers(playerOne, playerTwo);
        game.setCurrentPlayer(playerOne);
    }

    private int move(int pitNum){
        int extraTurn;
        extraTurn = game.move(pitNum);
        return extraTurn;
    }

    private void swapPlayer(Player curPlayer){
        if(curPlayer == playerOne){
            game.setCurrentPlayer(playerTwo);
        } else{
            game.setCurrentPlayer(playerOne);
        }
    }

    private void updatePits(){
        for(int x = 12; x >= 7; x--){
            buttons[0][x + ((6-x)*2) + 1].setText(Integer.toString(game.getNumStones(x)));
        }

        for(int x = 1; x <= 6; x++){
            buttons[2][x].setText(Integer.toString(game.getNumStones(x)));
        }

        buttons[1][0].setText(Integer.toString(game.getStoreCount(playerTwo)));
        buttons[1][7].setText(Integer.toString(game.getStoreCount(playerOne)));
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton kalah = new JButton("New Kalah game");
        JButton ayo = new JButton("New Ayo game");

        kalah.addActionListener(e -> {
            newKalahGame();
            setPlayers();
            updatePits();
            changeMessage(game.getCurrentPlayer().getName() + "'s turn");
        });

        ayo.addActionListener(e -> {
            newAyoGame();
            setPlayers();
            updatePits();
            changeMessage(game.getCurrentPlayer().getName() + "'s turn");
        });

        buttonPanel.add(kalah);
        buttonPanel.add(ayo);

        JTextField p1 = new JTextField("Enter P1 Name");
        JTextField p2 = new JTextField("Enter P2 Name");

        JButton p1E = new JButton("Enter");
        JButton p2E = new JButton("Enter");

        p1E.addActionListener(e -> {
            playerOne.setName(p1.getText());
            p1.setText("Enter P1 Name");
            p1n.setText("    " + playerOne.getName()+ "    ");
        });
        p2E.addActionListener(e -> {
            playerTwo.setName(p2.getText());
            p2.setText("Enter P2 Name");
            p2n.setText("    " + playerTwo.getName()+ "    ");
        });

        buttonPanel.add(p1);
        buttonPanel.add(p1E);

        buttonPanel.add(p2);
        buttonPanel.add(p2E);

        return buttonPanel;
    }

    private JPanel makeMancalaGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        GridLayout layOut = new GridLayout(tall, wide);
        panel.setLayout(layOut);
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x);
                buttons[y][x].setDown(y);
                buttons[y][x].setPreferredSize(new Dimension(50,50));
                panel.add(buttons[y][x]);
            }
        }
        for(int x = 12; x >= 7; x--){
            addButtonListener(0, x + ((6-x)*2) + 1, x);
        }

        for(int x = 1; x <= 6; x++){
            addButtonListener(2, x, x);
        }

        return panel;
    }

    private void addButtonListener(int x, int y, int num){
        buttons[x][y].addActionListener(e -> {
                if(!game.isGameOver()){
                    if(move(num) == -1){
                        changeMessage("Invalid move!");
                    } else if(game.isGameOver()){
                        changeMessage(game.getWinner().getName() + " wins!");
                        game.getWinner().getUserProfile().incGamePlayed(gameType);
                        if(game.getWinner() == playerOne){
                            playerTwo.getUserProfile().incGamePlayed(gameType);
                        } else {
                            playerOne.getUserProfile().incGamePlayed(gameType);
                        }
                        game.getWinner().getUserProfile().incGameWon(gameType);
                        updateData();
                    } else{
                        if(!game.isExtraTurn()){
                            swapPlayer(game.getCurrentPlayer());
                            changeMessage(game.getCurrentPlayer().getName() + "'s turn");
                        } else{
                            changeMessage(game.getCurrentPlayer().getName() + " gets an extra turn!");
                        }
                    }
                    updatePits(); 
                }
        });


    }

    private void updateData(){
        p1n.setText("    " + playerOne.getName()+ "    ");
        p2n.setText("    " + playerTwo.getName()+ "    ");
        p1KalahPlayed.setText("Kalah games played: " +  playerOne.getUserProfile().getKalahPlayed());
        p2KalahPlayed.setText("Kalah games played: " +  playerTwo.getUserProfile().getKalahPlayed());
        p1AyoPlayed.setText("Ayo games played: " +  playerOne.getUserProfile().getAyoPlayed());
        p2AyoPlayed.setText("Ayo games played: " +  playerTwo.getUserProfile().getAyoPlayed());
        p1KalahWon.setText("Kalah games won: " +  playerOne.getUserProfile().getKalahWon());
        p2KalahWon.setText("Kalah games won: " +  playerTwo.getUserProfile().getKalahWon());
        p1AyoWon.setText("Ayo games won: " +  playerOne.getUserProfile().getAyoWon());
        p2AyoWon.setText("Ayo games won: " +  playerTwo.getUserProfile().getAyoWon());
    }

    private JPanel messageBox(){
        JPanel panel = new JPanel();
        messageLabel = new JLabel("Welcome to Mancala! Select a game type and enter your names!");
        panel.add(messageLabel);
        return panel;
    }

    private void changeMessage(String message){
        messageLabel.setText(message);
    }


    private void makeMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        // Customize the menu item label
        JMenuItem save = new JMenuItem("Save Game");
        JTextField saveName = new JTextField("Filename");
        JMenuItem load = new JMenuItem("Load Game");
        JTextField loadName = new JTextField("Filename");

        JMenuItem saveU1 = new JMenuItem("Save User One");
        JTextField saveU1Name = new JTextField("Filename");
        JMenuItem loadU1 = new JMenuItem("Load User One");
        JTextField loadU1Name = new JTextField("Filename");

        JMenuItem saveU2 = new JMenuItem("Save User Two");
        JTextField saveU2Name = new JTextField("Filename");
        JMenuItem loadU2 = new JMenuItem("Load User Two");
        JTextField loadU2Name = new JTextField("Filename");

        save.addActionListener(e -> {
            try{
                saveGame.saveObject(game, saveName.getText());
            } catch (Exception x){
                System.out.println(x);
            }
            saveName.setText("FileName");
        });
        load.addActionListener(e -> {
            try{
                game = (MancalaGame) saveGame.loadObject(loadName.getText());
            } catch (Exception x){
                System.out.println(x);
            }
            updatePits();
            loadName.setText("FileName");
        });
        saveU1.addActionListener(e -> {
            try{
                saveGame.saveObject(playerOne.getUserProfile(), saveU1Name.getText());
            } catch (Exception x){
                System.out.println(x);
            }
            saveU1Name.setText("FileName");
        });
        loadU1.addActionListener(e -> {
            try{
                playerOne.setUserProfile((UserProfile)saveGame.loadObject(loadU1Name.getText()));
            } catch (Exception x){
                System.out.println(x);
            }
            loadU1Name.setText("FileName");
            updateData();
        });
        saveU2.addActionListener(e -> {
            try{
                saveGame.saveObject(playerTwo.getUserProfile(), saveU2Name.getText());
            } catch (Exception x){
                System.out.println(x);
            }
            saveU2Name.setText("FileName");
        });
        loadU2.addActionListener(e -> {
            try{
                playerTwo.setUserProfile((UserProfile)saveGame.loadObject(loadU2Name.getText()));
            } catch (Exception x){
                System.out.println(x);
            }
            loadU2Name.setText("FileName");
            updateData();
        });

        menu.add(saveName);
        menu.add(save);
        menu.add(loadName);
        menu.add(load);

        menu.add(saveU1Name);
        menu.add(saveU1);
        menu.add(loadU1Name);
        menu.add(loadU1);
        
        menu.add(saveU2Name);
        menu.add(saveU2);
        menu.add(loadU2Name);
        menu.add(loadU2);

        menuBar.add(menu);
    }

    private JPanel userProfileDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        p1n = new JLabel("    " + playerOne.getName()+ "    ");
        p2n = new JLabel("    " + playerTwo.getName()+ "    ");

        p1KalahPlayed = new JLabel("Kalah games played: " +  playerOne.getUserProfile().getKalahPlayed());
        p2KalahPlayed = new JLabel("Kalah games played: " +  playerTwo.getUserProfile().getKalahPlayed());

        p1AyoPlayed = new JLabel("Ayo games played: " +  playerOne.getUserProfile().getAyoPlayed());
        p2AyoPlayed = new JLabel("Ayo games played: " +  playerTwo.getUserProfile().getAyoPlayed());

        p1KalahWon = new JLabel("Kalah games won: " +  playerOne.getUserProfile().getKalahWon());
        p2KalahWon = new JLabel("Kalah games won: " +  playerTwo.getUserProfile().getKalahWon());

        p1AyoWon = new JLabel("Ayo games won: " +  playerOne.getUserProfile().getAyoWon());
        p2AyoWon = new JLabel("Ayo games won: " +  playerTwo.getUserProfile().getAyoWon());

        panel.add(p1n);
        panel.add(p1KalahPlayed);
        panel.add(p1KalahWon);
        panel.add(p1AyoPlayed);
        panel.add(p1AyoWon);
        panel.add(new JLabel("               "));
        panel.add(p2n);
        panel.add(p2KalahPlayed);
        panel.add(p2KalahWon);
        panel.add(p2AyoPlayed);
        panel.add(p2AyoWon);

        return panel;
    }

    public void setupGameContainer(){
        gameContainer = new JPanel();
        setLayout(new BorderLayout());
        gameContainer.add(makeMancalaGrid(3, 8));
    }

    public static void main(String args[]){
        MancalaUI newUI = new MancalaUI("Mancala");
        newUI.setVisible(true);
    }
}