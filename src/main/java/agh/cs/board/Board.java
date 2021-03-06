package agh.cs.board;

import agh.cs.board.utils.Vector2d;
import agh.cs.gui.GameFrame;
import agh.cs.tile.Tile;
import agh.cs.board.utils.Direction;
import agh.cs.utils.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static agh.cs.board.utils.Direction.*;

@Getter
@AllArgsConstructor
public class Board implements PropertyChangeListener  {

    /* Storage Maps */
    private HashMap<Vector2d, Boolean> emptySpaces;
    private Map<Vector2d, Tile> tilesMap;

    /* Settings */
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private int diffLvl;

    /* Game vars */
    private boolean isLost;
    private boolean isWon;
    private int score;
    private int bestScore;

    /* Visualizers */
    private GameFrame gameFrame;

    /* In class variables */
    private boolean madeMove;

    /* Listener */
    protected PropertyChangeSupport changes;


    public Board(int size, int diffLvl, GameFrame gameFrame) {
        this(new HashMap<>(),  new HashMap<>(),
                new Vector2d(0,0), new Vector2d(size - 1, size - 1),
                diffLvl, false, false, 0, 0,
                gameFrame, false,null );

        this.changes = new PropertyChangeSupport(this);

        for (int i = 0; i <= this.upperRight.x; i++) {
            for (int j = 0; j <= this.upperRight.y; j++) {
                updateEmptySpacesList(new Vector2d(i, j));
            }
        }

        addNewTile();
        addNewTile();
    }

    private void addNewTile() {
        Vector2d tilePos = Random.getRandFromMap(emptySpaces);
        if (tilePos == null) return;
        Tile t = new Tile(Random.genTileVal(diffLvl), tilePos, this);
        tilesMap.put(tilePos, t);
        t.addPropertyChangeListener(this);
        t.addPropertyChangeListener(gameFrame.gameScene);
        t.notifyBoardAfterCreation();
        updateEmptySpacesList(tilePos);
    }


    private void updateEmptySpacesList(Vector2d atPos) {
        if (isOccupied(atPos) && this.emptySpaces.containsKey(atPos)) {
            this.emptySpaces.remove(atPos);
        } else if (!isOccupied(atPos) && !this.emptySpaces.containsKey(atPos)) {
            this.emptySpaces.put(atPos, true);
        }
    }


    public boolean isOccupied(Vector2d pos) {
        return this.tilesMap.containsKey(pos);
    }


    public Tile objectAt(Vector2d pos) { return tilesMap.get(pos); }

    public void makeMove(Direction dir) {
        this.madeMove = false;
        int iStart, jStart, iChange, jChange;

        iStart = dir == RIGHT ? upperRight.x : lowerLeft.x;
        iChange = dir == RIGHT ? -1 : 1;

        jStart = dir == UP ? upperRight.y : lowerLeft.y;
        jChange = dir == UP ? -1 : 1;

        for (int i = iStart; i <= upperRight.x && i >= lowerLeft.x; i += iChange) {
            for (int j = jStart; j <= upperRight.y && j >= lowerLeft.y; j += jChange) {
                if (isOccupied(new Vector2d(i, j))) tilesMap.get(new Vector2d(i, j)).move(dir);
            }
        }
        if (madeMove) addNewTile();
        if (emptySpaces.isEmpty() && cantMakeAnyMove())
            isLost = true;

    }

    private boolean cantMakeAnyMove() {
        boolean noMoves = true;
        for (int k = 0; k < 4 && noMoves; k++) {
            Direction tmp = Direction.values()[k];
            noMoves = tilesMap.entrySet()
                    .stream()
                    .noneMatch(e -> e.getValue().canMerge(tmp));
        }
        return noMoves;
    }


    public void reset() {
        this.isLost=false;
        this.isWon=false;
        changes.firePropertyChange(new PropertyChangeEvent(this, "actualScoreChanged",score, 0));
        this.score=0;
        this.emptySpaces.clear();
        this.tilesMap.clear();
        for (int i = 0; i <= this.upperRight.x; i++) {
            for (int j = 0; j <= this.upperRight.y; j++) {
                updateEmptySpacesList(new Vector2d(i, j));
            }
        }


        addNewTile();
        addNewTile();
    }

    /* Listeners functions */

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        this.madeMove=true;
        if(event.getPropertyName().equals("positionChanged"))
        {

            Vector2d oldPos = (Vector2d) event.getOldValue();
            Vector2d newPos = (Vector2d) event.getNewValue();

            Tile t = tilesMap.get(oldPos);
            tilesMap.remove(oldPos);
            tilesMap.put(newPos, t);

            updateEmptySpacesList(newPos);
            updateEmptySpacesList(oldPos);
        }
        if(event.getPropertyName().equals("objectMerged")) {
            if(event.getOldValue().equals(event.getNewValue())) System.out.println("NOT POSSIBLE");

            Vector2d oldPos = (Vector2d) event.getOldValue();
            tilesMap.remove(oldPos);
            updateEmptySpacesList(oldPos);
            int oldScore =score;
            score+=  tilesMap.get(event.getNewValue()).getVal();
            changes.firePropertyChange(new PropertyChangeEvent(this, "actualScoreChanged", oldScore, score));

            if(score>bestScore) {
                changes.firePropertyChange(new PropertyChangeEvent(this, "bestScoreChanged", bestScore, score));
                bestScore = score;
            }
            if(tilesMap.get(event.getNewValue()).getVal()==2048) isWon=true;
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

}
