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


public class MancalaUI extends JFrame{
    private JPanel gameContainer;
    private JLabel messageLabel;
    private JMenuBar menuBar;
    private MancalaGame game;
    private Saver saveGame;
    private PositionAwareButton[][] buttons;
    private Player playerOne;
    private Player playerTwo;

    public MancalaUI(String title){
        super();
        playerOne = new Player();
        playerTwo = new Player();
        game = new MancalaGame();
        basicSetUp(title);
        setupGameContainer();
        add(gameContainer, BorderLayout.CENTER);
        add(makeButtonPanel(), BorderLayout.WEST);
        add(messageBox(), BorderLayout.SOUTH);
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
    }

    protected void newAyoGame(){
        game.setBoard(new AyoRules());
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

        JLabel a = new JLabel("Enter P1 Name");
        JLabel b = new JLabel("Enter P2 Name");

        JTextField p1 = new JTextField(7);
        JTextField p2 = new JTextField(7);

        JButton p1E = new JButton("Enter");
        JButton p2E = new JButton("Enter");

        p1E.addActionListener(e -> {
            playerOne.setName(p1.getText());
            p1.setText(" ");
        });
        p2E.addActionListener(e -> {
            playerTwo.setName(p2.getText());
            p2.setText(" ");
        });

        buttonPanel.add(a);
        buttonPanel.add(p1);
        buttonPanel.add(p1E);

        buttonPanel.add(b);
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
                    final int result = move(num);
                    if(game.isGameOver()){
                        changeMessage(game.getWinner().getName() + " wins!");
                    } else{
                        if(result == 0){
                            swapPlayer(game.getCurrentPlayer());
                            changeMessage(game.getCurrentPlayer().getName() + "'s turn");
                        } else if (result == 1){
                            changeMessage(game.getCurrentPlayer().getName() + " gets an extra turn!");
                        } else if (result == -1){
                            changeMessage("Invalid move!");
                        }
                    }
                    updatePits();
                }
        }); 

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
        JMenuItem item = new JMenuItem("Save Game");

        menu.add(item);
        menuBar.add(menu);
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