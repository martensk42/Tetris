/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package view;

import controller.AbstractTetrisAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.AbstractPiece;
import model.Block;
import model.Board;
import model.Piece;

/**
 * Represents a panel to display the tetris Board.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TetrisBoardPanel extends JPanel {
    
    /**
     * The scale factor to scale the board.
     */
    private static final int BOARD_SCALAR = 25;
    
    /**
     * The denominator for the ratio to round the corners of the pieces.
     */
    private static final int ROUNDING_DIVISOR = 3;
    
    /**
     * The scalar to round the corners of the pieces.
     */
    private static final int ROUNDING_SCALAR = BOARD_SCALAR / ROUNDING_DIVISOR;

    /**
     * The padding between blocks.
     */
    private static final int OUTER_BLOCK_PADDING = 1;
    
    /**
     * The length of the sides of the blocks.
     */
    private static final int OUTER_BLOCK_SIZE = OUTER_BLOCK_PADDING * 2;
    
    /**
     * The font size of displayed text in the panel.
     */
    private static final int FONT_SIZE = 40;
    
    /**
     * The opacity of some items to be painted.
     */
    private static final int TRANSPARENCY = 150;
    
    /**
     * The RGB values for a white color.
     */
    private static final int WHITE = 255;
    
    /**
     * The color of the game over panel.
     */
    private static final Color GAME_OVER_COLOR = new Color(150, 150, 150, 150);
    
    /**
     * The game board.
     */
    private Board myBoard;
    
    /**
     * The key bindings that are turned off.
     */
    private Map<KeyStroke, String> myOffKeyBindings;
    
    /**
     * Whether the game is running or not.
     */
    private boolean myIsRunning;
    
    /**
     * Whether the game is over.
     */
    private boolean myGameOver;
    
    /**
     * Whether the grid should be displayed.
     */
    private boolean myDisplayGrid;
    
    /**
     * Whether the background image should be drawn.
     */
    private boolean myDrawBackground;
    
    /**
     * Whether the user is in blind mode.
     */
    private boolean myBlindMode;
    
    /**
     * Constructs a new panel that displays the game board.
     * 
     * @param theBoard the game board.
     */
    public TetrisBoardPanel(final Board theBoard) {
        super();
        myBoard = theBoard;
        setPreferredSize(new Dimension(myBoard.getWidth() * BOARD_SCALAR,
                                       myBoard.getHeight() * BOARD_SCALAR));
        
        myOffKeyBindings = new HashMap<KeyStroke, String>();
        initializeBooleans();
    }
    
    /**
     * Sets the initial state of the boolean fields.
     */
    private void initializeBooleans() {
        myIsRunning = true;
        myGameOver = false;
        myDisplayGrid = false;
        myDrawBackground = true;
        myBlindMode = false;
    }
    
    /**
     * Sets a key binding to the panel.
     * 
     * @param theActionName the name of the action.
     * @param theKeyStroke the key stroke used to trigger the action.
     * @param theAction the action used when the key is pressed.
     */
    public void setKeyBinding(final String theActionName,
                              final KeyStroke theKeyStroke,
                              final Action theAction) {
        getInputMap().put(theKeyStroke, theActionName);
        getActionMap().put(theActionName, (AbstractTetrisAction) theAction);
    }
    
    /**
     * Changes an old key stroke to the new value.
     * 
     * @param theActionName the name of the action.
     * @param theOldKeyStroke the old key stroke used to trigger the action.
     * @param theNewKeyStroke the new key stroke used to trigger the action.
     */
    public void setNewKeyBinding(final String theActionName,
                                  final KeyStroke theOldKeyStroke,
                                  final KeyStroke theNewKeyStroke) {
        final InputMap map = getInputMap();
        map.remove(theOldKeyStroke);
        map.put(theNewKeyStroke, theActionName);
    }
    
    /**
     * {@inheritDoc}
     * 
     * Paints the board and the current piece.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        if (!myBlindMode) {
            if (myDrawBackground) {
                theGraphics.drawImage((Image) new ImageIcon("./images/halo.jpg").getImage(),
                                                            0, 0, (ImageObserver) this);
            } else {
                g2d.drawRect(0, 0, myBoard.getWidth() * BOARD_SCALAR + 1,
                             myBoard.getHeight() * BOARD_SCALAR + 1);
            }
        } else {
            g2d.setColor(Color.DARK_GRAY.darker());
            g2d.fillRect(0, 0, myBoard.getWidth() * BOARD_SCALAR + 1,
                         myBoard.getHeight() * BOARD_SCALAR + 1);
        }
        
        if (myIsRunning) {
            if (myDisplayGrid && !myBlindMode) {
                drawGrid(g2d);
            }
            drawCurrentShape(g2d);
            drawFrozenBlocks(g2d);
        } else {
            drawPause(g2d);
        }
        if (myGameOver) {
            drawGameOver(g2d);
        }
    }
    
    /**
     * Draws a grid of piece outlines.
     * 
     * @param theGraphics the graphics used to draw the grid.
     */
    private void drawGrid(final Graphics2D theGraphics) {
        for (int i = 0; i < myBoard.getWidth(); i++) {
            for (int j = 0; j < myBoard.getHeight(); j++) {
                final Shape backgroundBlock = getTetrisBlock(i, j, OUTER_BLOCK_PADDING,
                                                         OUTER_BLOCK_SIZE);
                theGraphics.setColor(Color.LIGHT_GRAY.darker());
                theGraphics.draw(backgroundBlock);
            }
        }
    }
    
    /**
     * Draws the piece that is current falling on the board.
     * 
     * @param theGraphics the graphics used to draw the piece.
     */
    private void drawCurrentShape(final Graphics2D theGraphics) {
        final Piece curPiece = myBoard.getCurrentPiece();
        final int[][] coords = ((AbstractPiece) curPiece).getBoardCoordinates();
        for (int i = 0; i < coords.length; i++) {
            final Shape tetrisPiece = getTetrisBlock(coords[i][0], coords[i][1],
                                                     OUTER_BLOCK_PADDING, OUTER_BLOCK_SIZE);
            
            if (!myBlindMode) {
                final Color blockColor = ((AbstractPiece) curPiece).getBlock().getColor();
                final Color paint = new Color(blockColor.getRed(), blockColor.getGreen(),
                                              blockColor.getBlue(), TRANSPARENCY);
                theGraphics.setColor(paint);
                theGraphics.fill(tetrisPiece);
            }
            theGraphics.setColor(Color.BLACK);
            theGraphics.draw(tetrisPiece);
        }
    }    
    /**
     * Draws the frozen blocks at the bottom of the board.
     * 
     * @param theGraphics used to draw the blocks.
     */
    private void drawFrozenBlocks(final Graphics2D theGraphics) {
        final List<Block[]> blockRow = myBoard.getFrozenBlocks();
        for (int row = 0; row < blockRow.size(); row++) {
            final Block[] blocks = blockRow.get(row);
            for (int col = 0; col < blocks.length; col++) {
                if (blocks[col] != Block.EMPTY) {
                    final Shape tetrisBlock = getTetrisBlock(col, row, OUTER_BLOCK_PADDING,
                                                                 OUTER_BLOCK_SIZE);
                    if (!myBlindMode) {
                        theGraphics.setColor(GAME_OVER_COLOR);
                        theGraphics.fill(tetrisBlock);
                    }
                    theGraphics.setColor(Color.BLACK);
                    theGraphics.draw(tetrisBlock);
                }
            }
        }
    }
    
    /**
     * Displays text and covers the game board when the user is paused.
     * 
     * @param theGraphics the graphics used to draw the pause text and background cover.
     */
    private void drawPause(final Graphics2D theGraphics) {
        final Color paint = new Color(WHITE, WHITE, WHITE, TRANSPARENCY);
        theGraphics.setColor(paint);
        theGraphics.fillRect(0, 0, myBoard.getWidth() * BOARD_SCALAR,
                             myBoard.getHeight() * BOARD_SCALAR);
        theGraphics.setColor(Color.RED);
        theGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
        final String pause = "Paused!";
        theGraphics.drawString(
            pause,
            myBoard.getWidth() * BOARD_SCALAR / 2
            - ((int) theGraphics.getFontMetrics().getStringBounds(pause,
                theGraphics).getWidth() / 2),
            myBoard.getHeight() * BOARD_SCALAR / 2
            - (theGraphics.getFontMetrics().getHeight()) / 2);
        
    }
    
    /**
     * Draws the game over display.
     * 
     * @param theGraphics theGraphics used to draw the display.
     */
    private void drawGameOver(final Graphics2D theGraphics) {
        theGraphics.setColor(GAME_OVER_COLOR);
        theGraphics.fillRect(0, 0, myBoard.getWidth() * BOARD_SCALAR,
                             myBoard.getHeight() * BOARD_SCALAR);
        theGraphics.setColor(Color.RED);
        theGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
        final String gameOver = "Game Over";
        theGraphics.drawString(gameOver,
                               myBoard.getWidth() * BOARD_SCALAR / 2
                               - ((int) theGraphics.getFontMetrics().getStringBounds(gameOver,
                                   theGraphics).getWidth() / 2),
                               myBoard.getHeight() * BOARD_SCALAR / 2
                               - (theGraphics.getFontMetrics().getHeight()) / 2);
    }
    
    /**
     * Makes a new block shape.
     * 
     * @param theX the x coordinate of the shape.
     * @param theY the y coordinate of the shape.
     * @param thePadding the amount of padding between blocks.
     * @param theSideLength the length of one side of the shape.
     * @return the newly created block.
     */
    private Shape getTetrisBlock(final int theX, final int theY, final int thePadding,
                                 final int theSideLength) {
        return new RoundRectangle2D.Float(theX * BOARD_SCALAR + thePadding,
            (myBoard.getHeight() - (theY + 1)) * BOARD_SCALAR + thePadding,
            BOARD_SCALAR - theSideLength, BOARD_SCALAR - theSideLength,
            ROUNDING_SCALAR, ROUNDING_SCALAR);
    }
    
    /**
     * Creates a new game with the same width, height, and a random set of pieces.
     */
    public void newGame() {
        myBoard.newGame(myBoard.getWidth(), myBoard.getHeight(), null);
    }
    
    /**
     * Sets the state of the blind mode.
     * 
     * @param theBlindMode whether the user is to be put in blind mode.
     */
    public void setBlindMode(final boolean theBlindMode) {
        myBlindMode = theBlindMode;
    }
    
    /**
     * Sets the state of the background image.
     * 
     * @param theDrawBackground whether the background image is to be drawn.
     */
    public void setDrawBackground(final boolean theDrawBackground) {
        myDrawBackground = theDrawBackground;
    }
    
    /**
     * Sets the state of the grid display.
     * 
     * @param theDisplayGrid whether the grid is to be displayed.
     */
    public void setGridDisplay(final boolean theDisplayGrid) {
        myDisplayGrid = theDisplayGrid;
    }
    
    /**
     * Sets the visual state of game over.
     * 
     * @param theGameOver whether the game is to be over.
     */
    public void setGameOver(final boolean theGameOver) {
        myGameOver = theGameOver;
        if (!theGameOver) {
            for (KeyStroke key: myOffKeyBindings.keySet()) {
                getInputMap().put(key, myOffKeyBindings.get(key));
            }
            myOffKeyBindings.clear();
            
        } else {
            final KeyStroke[] mapKeys = getInputMap().keys();
            for (int i = 0; i < mapKeys.length; i++) {
                myOffKeyBindings.put(mapKeys[i], (String) getInputMap().get(mapKeys[i]));
                getInputMap().remove(mapKeys[i]);
            }
        }
    }
    
    /**
     * Sets whether the game is running.
     * 
     * @param theIsRunning whether the game should start or stop running.
     */
    public void setRunning(final boolean theIsRunning) {
        if (theIsRunning) {
            for (KeyStroke key: myOffKeyBindings.keySet()) {
                getInputMap().put(key, myOffKeyBindings.get(key));
            }
            myOffKeyBindings.clear();
            
        } else {
            final KeyStroke[] mapKeys = getInputMap().keys();
            for (int i = 0; i < mapKeys.length; i++) {
                if (!"pause".equals(getInputMap().get(mapKeys[i]))) {
                    myOffKeyBindings.put(mapKeys[i], (String) getInputMap().get(mapKeys[i]));
                    getInputMap().remove(mapKeys[i]);
                }
            }
        }
        myIsRunning = theIsRunning;
    }
    
    /**
     * @return Whether the game is running or not.
     */
    public boolean isRunning() {
        return myIsRunning;
    }
}
