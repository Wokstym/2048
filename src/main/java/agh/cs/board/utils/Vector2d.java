package agh.cs.board.utils;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    private boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    private boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Boolean isVectorInBoundary(Vector2d lowerLeft, Vector2d upperRight) {
        return this.follows(lowerLeft) && this.precedes(upperRight);
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

