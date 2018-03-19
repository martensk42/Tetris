/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package controller;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import view.TetrisBoardPanel;

/**
 * Represents an action to pause the tetris game.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PauseAction extends AbstractTetrisAction {
    
    /**
     * The timer to constantly progress the game.
     */
    private Timer myTimer;
    
    /**
     * The panel holding the board.
     */
    private TetrisBoardPanel myBoardPanel;
    
    /**
     * Constructs a new instance of the pause action.
     * 
     * @param theName the name to be given to the action.
     * @param theTimer the timer used for the game.
     * @param theBoardPanel the panel with the game board.
     */
    public PauseAction(final String theName, final Timer theTimer,
                       final TetrisBoardPanel theBoardPanel) {
        super(theName);
        myTimer = theTimer;
        myBoardPanel = theBoardPanel;
    }

    /**
     * {@inheritDoc}
     * 
     * Either stops or starts the tetris game.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        if (myTimer.isRunning()) {
            myTimer.stop();
        } else {
            myTimer.start();
        }
        myBoardPanel.setRunning(!myBoardPanel.isRunning());
        myBoardPanel.repaint();
    }
}
