/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package controller;

import java.awt.event.ActionEvent;

import model.Board;

/**
 * Represents an action to rotate a tetris piece.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RotateAction extends AbstractTetrisAction {
    
    /**
     * Constructs a new instance of the rotate action.
     * 
     * @param theName the name of the action.
     * @param theBoard the board which holds the piece.
     */
    public RotateAction(final String theName, final Board theBoard) {
        super(theName, theBoard);
    }
    
    /**
     * {@inheritDoc}
     * 
     * Rotates the current piece on the board.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        getBoard().rotate();
    }
}
