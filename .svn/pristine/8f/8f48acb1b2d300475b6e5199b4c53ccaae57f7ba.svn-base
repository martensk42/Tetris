/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Board;

/**
 * Represents a menu bar with added features for a tetris game.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TetrisMenuBar extends JMenuBar implements Observer {
    
    /**
     * The initial text of the scoring system used in the game.
     */
    private static final String SCORING_SYSTEM = "You must complete lines to gain points.\n"
            + "You gain 10 points per line, multiplied by the level. This means that if\n"
            + "you are on level 4 and you clear a line, you will get 40 points.\n\n"
            + "You progress levels by getting points up through the set:\n";
    
    /**
     * The game menu.
     */
    private JMenu myGameMenu;
    
    /**
     * The options menu.
     */
    private JMenu myOptionsMenu;
    
    /**
     * The help Menu.
     */
    private JMenu myHelpMenu;
    
    /**
     * The panel that contains the game board.
     */
    private TetrisBoardPanel myBoardPanel;
    
    /**
     * The game timer.
     */
    private Timer myTimer;
    
    /**
     * The new game menu item.
     */
    private JMenuItem myNewGame;
    
    /**
     * The end game menu item.
     */
    private JMenuItem myEndGame;
    
    /**
     * The blind mode check box.
     */
    private JCheckBoxMenuItem myBlindMode;
    
    /**
     * Constructs a new instance of the menu bar.
     * 
     * @param theBoardPanel the panel with the game board.
     * @param theTimer The game's timer.
     */
    public TetrisMenuBar(final TetrisBoardPanel theBoardPanel, final Timer theTimer) {
        super();
        myGameMenu = new JMenu("Game");
        myOptionsMenu = new JMenu("Options");
        myHelpMenu = new JMenu("Help");
        myBoardPanel = theBoardPanel;
        myTimer = theTimer;
        myNewGame = new JMenuItem("New Game");
        myEndGame = new JMenuItem("End Game");
        myBlindMode = new JCheckBoxMenuItem("Blind Mode");
        
        setupComponents();
    }
    
    /**
     * Adds the menus to the menu bar.
     */
    private void setupComponents() {
        createGameMenu();
        createOptionsMenu();
        createHelpMenu();
    }
    
    /**
     * Adds the components to the game menu.
     */
    private void createGameMenu() {
        myNewGame.setEnabled(false);
        myNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoardPanel.newGame();
                myBoardPanel.setGameOver(false);
                myBoardPanel.repaint();
                myEndGame.setEnabled(true);
                myNewGame.setEnabled(false);
                myTimer.restart();
            }
        });
        
        myEndGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoardPanel.setGameOver(true);
                myBoardPanel.repaint();
                myNewGame.setEnabled(true);
                myEndGame.setEnabled(false);
                myTimer.stop();
            }
        });
        
        myGameMenu.add(myNewGame);
        myGameMenu.add(myEndGame);
        
        add(myGameMenu);
    }
    
    /**
     * Adds the components to the options menu.
     */
    private void createOptionsMenu() {
        final JCheckBoxMenuItem grid = new JCheckBoxMenuItem("Grid");
        grid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoardPanel.setGridDisplay(grid.isSelected());
                myBoardPanel.repaint();
            }
        });
        
        final JCheckBoxMenuItem background = new JCheckBoxMenuItem("Toggle Background Image");
        background.setSelected(true);
        background.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoardPanel.setDrawBackground(background.isSelected());
                myBoardPanel.repaint();
            }
        });
        
        myOptionsMenu.add(grid);
        myOptionsMenu.add(background);
        
        add(myOptionsMenu);
    }
    
    /**
     * Adds the components to the help menu.
     */
    private void createHelpMenu() {
        final JMenuItem scoring = new JMenuItem("Scoring System");
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                String message = SCORING_SYSTEM;
                for (int i = 0; i < ScorePanel.LEVELS.length; i++) {
                    message += ScorePanel.LEVELS[i] + " points for level "
                            + (i + 1) + "\n";
                }
                JOptionPane.showMessageDialog(null, message);
            }
        });
        
        myHelpMenu.add(scoring);
        
        add(myHelpMenu);
    }
    
    /**
     * Sets up the action listener for the blind menu.
     * 
     * @param theNextPiecePanel the panel containing the next piece pf the game.
     */
    public void setupBlindMode(final NextPiecePanel theNextPiecePanel) {
        myBlindMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoardPanel.setBlindMode(myBlindMode.isSelected());
                theNextPiecePanel.setBlindMode(myBlindMode.isSelected());
                myBoardPanel.repaint();
                theNextPiecePanel.repaint();
            }
        });
        myOptionsMenu.add(myBlindMode);

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable theObservable, final Object theChange) {
        if (((Board) theObservable).isGameOver()) {
            myEndGame.setEnabled(false);
            myNewGame.setEnabled(true);
        }
    }
}
