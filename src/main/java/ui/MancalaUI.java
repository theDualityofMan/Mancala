package ui;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import mancala.MancalaGame;
import mancala.Saver;

public class MancalaUI extends JFrame{
    private JPanel gameContainer;
    private JLabel messageLabel;
    private JMenuBar menuBar;
    private MancalaGame game;
    private Saver saveGame;
    private PositionAwareButton[][] buttons;

    public MancalaUI(String title){
        super();
        basicSetUp(title);
        setupGameContainer();
        add(gameContainer, BorderLayout.CENTER);
        makeMenu();
        setJMenuBar(menuBar);
        pack();
    }
    private void basicSetUp(String title){
        this.setTitle(title);
        gameContainer = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private JPanel startupMessage() {
        JPanel temp = new JPanel();
        // Customize the message as desired
        temp.add(new JLabel("Mancala!"));
        return temp;
    }

    private JPanel makeMancalaGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y = 0; y < wide; y++) {
            for (int x = 0; x < tall; x++) {
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1);
                buttons[y][x].setDown(y + 1);
                buttons[y][x].addActionListener
                panel.add(buttons[y][x]);
            }
        }
        return panel;
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
        gameContainer.add(startupMessage());
        gameContainer.add(makeMancalaGrid(3, 8));
    }

    public static void main(String args[]){
        MancalaUI newUI = new MancalaUI("Mancala");
        newUI.setVisible(true);
    }
}