package agh.cs.utils;
import agh.cs.board.utils.Vector2d;

import java.util.HashMap;

public class Random {

    private static java.util.Random rand = new java.util.Random();

    public static int getRandomInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static Vector2d genVectInBound(int maxX, int maxY) {
        int x, y;
        x = getRandomInRange(0, maxX);
        y = getRandomInRange(0, maxY);
        return new Vector2d(x, y);
    }

    public static int genTileVal(int diffLvl) {
        return (int) Math.pow(2, getRandomInRange(1, diffLvl));
    }

    public static Vector2d getRandFromMap(HashMap<Vector2d, Boolean> emptySpaces)
    {
        Vector2d[] pos = emptySpaces.keySet().toArray(new Vector2d[0]);
        if(pos.length>0) {
            return pos[Random.getRandomInRange(0, pos.length - 1)];
        }
        return null;
    }


}
