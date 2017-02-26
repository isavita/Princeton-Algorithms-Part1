import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        int n = points.length;
        for (int i = 0; i < n; i++)
            if (points[i] == null) throw new java.lang.NullPointerException();
        if (checkDuplicated(points)) throw new IllegalArgumentException("Duplicated points.");
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < n - 3; i++)
            for (int j = i + 1; j < n - 2; j++)
                for (int k = j + 1; k < n - 1; k++)
                    for (int l = k + 1; l < n; l++)
                        if (isCollinear(pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[l])) {
                            this.segments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return this.segments.size();
    }
    
    // the line segments 
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.segments.size()]);
    }
    
    // check if 4 points are collinear
    private boolean isCollinear(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d);
    }
    
    private boolean checkDuplicated(Point[] points) {
        int n = points.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (points[i].compareTo(points[j]) == 0) return true;
        return false;
    }
}
