/*
 * TCSS 305 � Autumn 2013
 * Assignment 6 - Tetris
 */

package controller;

import java.awt.event.ActionEvent;

import model.Board;

/**
 * Represents an action to move a tetris piece to the left.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LeftAction extends AbstractTetrisAction {
    
    /**
     * Constructs a new instance of the left action.
     * 
     * @param theName the name of the action.
     * @param theBoard the board which holds the piece.
     */
    public LeftAction(final String theName, final Board theBoard) {
        super(theName, theBoard);
    }
    
    /**
     * {@inheritDoc}
     * 
     * Moves the current piece to the left on the board.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        getBoard().moveLeft();
    }
}
