/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package controller;

import javax.swing.AbstractAction;

import model.Board;

/**
 * Represents an action used to manipulate a tetris piece.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class AbstractTetrisAction extends AbstractAction {
    
    /**
     * The name of the action.
     */
    private String myActionName;
    
    /**
     * The board which holds the piece.
     */
    private Board myBoard;
    
    /**
     * Constructs a new instance of a tetris action.
     * 
     * @param theName the name to be given to the action.
     */
    public AbstractTetrisAction(final String theName) {
        this(theName, null);
    }
    
    /**
     * Constructs a new instance of a tetris action.
     * 
     * @param theName the name to be given to the action.
     * @param theBoard the board which holds the piece.
     */
    public AbstractTetrisAction(final String theName, final Board theBoard) {
        myActionName = theName;
        myBoard = theBoard;
    }
    
    /**
     * @return the name of the action.
     */
    public String getActionName() {
        return myActionName;
    }
    
    /**
     * @return the game board.
     */
    public Board getBoard() {
        return myBoard;
    }
}
