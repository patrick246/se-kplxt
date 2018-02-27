package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

public class Point {
    private final int x;
    private final int y;
    private final long val;

    public Point(int x, int y, long val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getVal() {
        return val;
    }
}
