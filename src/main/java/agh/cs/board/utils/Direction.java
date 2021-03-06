package agh.cs.board.utils;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Vector2d toUnitVector() {
        switch (this) {
            case UP:
                return new Vector2d(0,1);
            case RIGHT:
                return new Vector2d(1,0);
            case DOWN:
                return new Vector2d(0,-1);
            case LEFT:
                return new Vector2d(-1,0);
        }
        throw new IllegalArgumentException("Not on enum");
    }
}
