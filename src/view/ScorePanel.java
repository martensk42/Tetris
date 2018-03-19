/*
 * TCSS 305 – Autumn 2013
 * Assignment 6 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents a panel that keeps track of the score in a tetris game.
 * 
 * @author Kyle Martens
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
    
    /**
     * The points required to increase in level.
     */
    public static final int[] LEVELS = {100, 250, 500, 750};
    
    /**
     * How many dynamics of the score to be displayed.
     */
    private static final int SCORE_DYNAMICS = 3;
    
    /**
     * Number of columns for the gird layout.
     */
    private static final int SCORE_COLUMNS = 2;
    
    /**
     * Multiplier for the points gained for each line cleared.
     */
    private static final int POINT_MODIFIER = 10;
    
    /**
     * Amount of decrease of the timer speed for each level.
     */
    private static final double TIMER_DECREMENT = .75;
    
    /**
     * The current score.
     */
    private JLabel myScore;
    
    /**
     * The current number of lines cleared.
     */
    private JLabel myClearedLines;
    
    /**
     * The current level.
     */
    private JLabel myLevel;
    
    /**
     * Constructs a new instance of the ScorePanel.
     * 
     * @param theScore the initial score.
     * @param theClearedLines the initial number of lines cleared.
     * @param theLevel the initial level.
     */
    public ScorePanel(final int theScore, final int theClearedLines, final int theLevel) {
        super();
        myScore = new JLabel(String.valueOf(theScore), JLabel.CENTER);
        myClearedLines = new JLabel(String.valueOf(theClearedLines), JLabel.CENTER);
        myLevel = new JLabel(String.valueOf(theLevel), JLabel.CENTER);
        setLayout(new GridLayout(SCORE_DYNAMICS, SCORE_COLUMNS, 0, 0));
        
        setupComponents();
    }
    
    /**
     * Adds the components to the panel.
     */
    private void setupComponents() {
        add(new JLabel("Score"));
        add(myScore);
        add(new JLabel("Lines Cleared"));
        add(myClearedLines);
        add(new JLabel("Level"));
        add(myLevel);
        
        setBorder(BorderFactory.createTitledBorder("Score:"));
        setBackground(Color.WHITE);
        
    }
    
    /**
     * Updates the score in the game.
     * 
     * @param theClearedLines new number of lines cleared by the user.
     * @param theTimer current state of the game timer.
     */
    public void updateScore(final int theClearedLines, final Timer theTimer) {
        final int curLines = Integer.valueOf(myClearedLines.getText());
        int curScore = Integer.valueOf(myScore.getText());
        int curLevel = Integer.valueOf(myLevel.getText());
        
        curScore += curLevel * theClearedLines * POINT_MODIFIER;
        final int index = (curLevel - 1) % LEVELS.length;
        if (LEVELS[index] * Math.pow(POINT_MODIFIER,
                                     (curLevel - 1) / LEVELS.length) <= curScore) {
            curLevel++;
            theTimer.setDelay((int) Math.round(theTimer.getDelay() * TIMER_DECREMENT));
        }
        
        updateText(myScore, curScore);
        updateText(myClearedLines, curLines + theClearedLines);
        updateText(myLevel, curLevel);
        
    }
    
    /**
     * Updates the text of the JLabel to account for the new score.
     * 
     * @param theLabel the label displaying the score.
     * @param theChange the new value to be set.
     */
    private void updateText(final JLabel theLabel, final int theChange) {
        theLabel.setText(String.valueOf(theChange));
    }
    
    
}
