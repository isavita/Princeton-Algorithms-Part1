import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> points;
    
    // construct an empty set of points
    public PointSET() {
        this.points = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return points.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        if (points.contains(p)) return;
        points.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        return points.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points)
            point.draw();
    }
    
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException();
        Queue<Point2D> pointsInside = new Queue<Point2D>();
        for (Point2D point : points)
            if (rect.contains(point)) pointsInside.enqueue(point);
        return pointsInside;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException();
        if (points.isEmpty()) return null;
        Point2D nearestPoint = null;
        double nearestPointDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points)
            if (p.distanceSquaredTo(point) < nearestPointDistance) {
                nearestPoint = point;
                nearestPointDistance = p.distanceSquaredTo(point);
            }
        return nearestPoint;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
    }
}
