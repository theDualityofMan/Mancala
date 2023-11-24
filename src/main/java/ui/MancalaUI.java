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
    private PositionAwareButton[][] buttons;

    public MancalaUI(String title){
        super();
        basicSetUp(title);
        setupGameContainer();
        add(gameContainer, BorderLayout.CENTER);
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
    public void setupGameContainer(){
        gameContainer.add(startupMessage());
    }

    public static void main(String args[]){
        MancalaUI newUI = new MancalaUI("Mancala");
        newUI.setVisible(true);
    }
}