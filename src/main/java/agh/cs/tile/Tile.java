package agh.cs.tile;

import agh.cs.board.utils.Vector2d;
import agh.cs.board.Board;
import agh.cs.board.utils.Direction;
import lombok.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@AllArgsConstructor
public class Tile {

    @Getter
    private int val;
    @Getter
    private Vector2d pos;
    private Board board;
    protected PropertyChangeSupport changes;

    public Tile(int val, Vector2d pos, Board board) {
        this(val, pos, board, null);
        this.changes = new PropertyChangeSupport(this);
    }

    public void move(Direction dir) {
        Vector2d oldPos = this.pos;
        Vector2d newPos = this.pos.add(dir.toUnitVector());

        if (!newPos.isVectorInBoundary(board.getLowerLeft(), board.getUpperRight())) return;


        while (!board.isOccupied(newPos) && newPos.isVectorInBoundary(board.getLowerLeft(), board.getUpperRight())) {
            this.pos = newPos;
            newPos = this.pos.add(dir.toUnitVector());
        }
        changes.firePropertyChange(new PropertyChangeEvent(this, "positionChanged", oldPos, this.pos));

        if (board.isOccupied(this.pos.add(dir.toUnitVector())))
            board.getTilesMap().get(this.pos.add(dir.toUnitVector())).merge(this);
    }

    public boolean canMerge(Direction dir) {
        Vector2d newPos = this.pos.add(dir.toUnitVector());

        return newPos.isVectorInBoundary(board.getLowerLeft(), board.getUpperRight()) &&
                (board.objectAt(newPos).getVal() == this.val);
    }

    private void merge(Tile t) {
        if (this.val == t.val) {
            this.val *= 2;
            changes.firePropertyChange(new PropertyChangeEvent(this, "objectMerged", t.getPos(), this.getPos()));
        }

    }

    /* Listeners functions */

    public void notifyBoardAfterCreation() {
        changes.firePropertyChange(new PropertyChangeEvent(this, "newTile", null, this.getPos()));
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }


    @Override
    public String toString() {
        return val + "\t";
    }


}
