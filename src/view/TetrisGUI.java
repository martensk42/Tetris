/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package view;

import controller.AbstractTetrisAction;
import controller.DownAction;
import controller.HardDropAction;
import controller.LeftAction;
import controller.PauseAction;
import controller.RightAction;
import controller.RotateAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Board;

/**
 * Represents the Graphical User Interface for a Tetris game.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
public class TetrisGUI implements Observer {
    
    /**
     * The Toolkit for the window.
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /**
     * The width of the screen.
     */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /**
     * The height of the screen.
     */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    
    /**
     * The Size of the controls panel.
     */
    private static final int CONTROLS_PANEL_SIZE = 150;
    
    /**
     * The delay for the Timer.
     */
    private static final int DEFAULT_TIME_DELAY = 1000;
    
    /**
     * The padding between panels on the information panel on the west side.
     */
    private static final int VERTICAL_PADDING = 50;
    
    /**
     * The tetris board.
     */
    private Board myBoard;
    
    /**
     * The timer for piece movement.
     */
    private Timer myTimer;
    
    /**
     * The frame for the GUI.
     */
    private final JFrame myFrame;
    
    /**
     * The panel that displays the board.
     */
    private final TetrisBoardPanel myBoardPanel;
    
    /**
     * The panel that displays the next piece.
     */
    private NextPiecePanel myNextPiecePanel;
    
    /**
     * The map to keep track of the controls.
     */
    private Map<String, AbstractTetrisAction> myKeyBindings;
    
    /**
     * The panel used to display the score.
     */
    private ScorePanel myScorePanel;
    
    /**
     * Constructs a new instance of the tetris GUI.
     */
    public TetrisGUI() {
        myFrame = new JFrame("TCSS 305: Tetris");
        myBoard = new Board();
        myScorePanel = new ScorePanel(0, 0, 1);
        initializeTimer();
        
        myKeyBindings = new HashMap<String, AbstractTetrisAction>();
        initializeKeyMappings();
        
        myBoardPanel = new TetrisBoardPanel(myBoard);
        myNextPiecePanel = new NextPiecePanel(myBoard.getNextPiece());
    }
    
    /**
     * Creates the timer and gives it an initial step speed.
     */
    private void initializeTimer() {
        myTimer = new Timer(DEFAULT_TIME_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.step();
            }
        });
    }
    
    /**
     * Adds the default key mappings for the game.
     */
    private void initializeKeyMappings() {
        myKeyBindings.put("move left", new LeftAction("LEFT", myBoard));
        myKeyBindings.put("move right", new RightAction("RIGHT", myBoard));
        myKeyBindings.put("move down", new DownAction("DOWN", myBoard));
        myKeyBindings.put("rotate", new RotateAction("UP", myBoard));
        myKeyBindings.put("hard drop", new HardDropAction("SPACE", myBoard));
        myKeyBindings.put("pause", new PauseAction("P", myTimer, myBoardPanel));
    }
    
    /**
     * Sets up all the components of the GUI.
     */
    private void setupComponents() {
        final JPanel infoPanel = new JPanel();
        final JPanel controlsPanel = new JPanel();
        createControlsPanel(controlsPanel);
        
        final Box westBox = new Box(BoxLayout.PAGE_AXIS);
        westBox.add(myNextPiecePanel);
        westBox.add(Box.createVerticalStrut(VERTICAL_PADDING));
        westBox.add(myScorePanel);
        westBox.add(Box.createVerticalStrut(VERTICAL_PADDING));
        westBox.add(controlsPanel);
        infoPanel.add(westBox);
        
        final JPanel centerPanel = new JPanel();
        centerPanel.add(myBoardPanel);
        myFrame.add(centerPanel, BorderLayout.CENTER);
        myFrame.add(infoPanel, BorderLayout.WEST);
        final TetrisMenuBar menuBar = new TetrisMenuBar(myBoardPanel, myTimer);
        menuBar.setupBlindMode(myNextPiecePanel);
        myFrame.setJMenuBar(menuBar);
        
        myBoard.addObserver(this);
        myBoard.addObserver(menuBar);
    }
    
    /**
     * Adds all the components to the given controls panel.
     * 
     * @param theControlsPanel the panel to display the controls on.
     */
    private void createControlsPanel(final JPanel theControlsPanel) {
        theControlsPanel.setBorder(BorderFactory.createTitledBorder("Controls:"));
        theControlsPanel.setPreferredSize(new Dimension(CONTROLS_PANEL_SIZE,
                                                     CONTROLS_PANEL_SIZE));
        theControlsPanel.setBackground(Color.YELLOW);
        final Box controls = new Box(BoxLayout.PAGE_AXIS);
        
//      set the key bindings for the game
        for (String actionKey: myKeyBindings.keySet()) {
            final Action action = myKeyBindings.get(actionKey);
            final String actionName = ((AbstractTetrisAction) action).getActionName();
            controls.add(new JLabel(actionKey + ": " + actionName.toLowerCase()));
            myBoardPanel.setKeyBinding(actionKey,
                                       KeyStroke.getKeyStroke(actionName), action);
        }
        
        final JButton changeBindings = new JButton("Change Controls");
        changeBindings.setFocusable(false);
        changeBindings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                new KeyBindingsFrame("Change Key Bindings", myBoardPanel, myKeyBindings);
            }
        });
        controls.add(changeBindings);
        theControlsPanel.add(controls);
    }
    
    /**
     * Starts the tetris game.
     */
    public void start() {
        initializeKeyMappings();
        setupComponents();
        
        myFrame.setResizable(false);
        myFrame.pack();
        myFrame.setIconImage(new ImageIcon("./icons/tetris.jpg").getImage());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocation(SCREEN_WIDTH / 2 - myFrame.getWidth() / 2,
                    SCREEN_HEIGHT / 2 - myFrame.getHeight() / 2);
        myFrame.setVisible(true);
        myTimer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable theObservable, final Object theChange) {
        myBoardPanel.repaint();
        myNextPiecePanel.repaint();
        if (theChange != null && (int) theChange > 0) {
            myScorePanel.updateScore((int) theChange, myTimer);
        }
        if (myBoard.isGameOver()) {
            myTimer.stop();
            myBoardPanel.setGameOver(true);
        }
        if (!myNextPiecePanel.getNextPiece().equals(myBoard.getNextPiece())) {
            myNextPiecePanel.setNextPiece(myBoard.getNextPiece());
        }
    }
}
