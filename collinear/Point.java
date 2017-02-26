import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            return Double.compare(slopeA, slopeB);
        }
    }
    
    private final int x;
    private final int y;
    
    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // string representation
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.y == that.y && this.x == that.x) return 0;
        if (this.y > that.y) return 1;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        return 1;
    }
    
    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        if (dy == 0) return +0.0d;
        if (dx == 0) return Double.POSITIVE_INFINITY;
        return dy / dx;
    }
    
    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }
    
    public static void main(String[] args) {
        
    }
}