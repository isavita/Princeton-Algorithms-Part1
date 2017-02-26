import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        int n = points.length;
        for (int i = 0; i < n; i++)
            if (points[i] == null) throw new java.lang.NullPointerException();
        if (checkDuplicated(points)) throw new IllegalArgumentException("Duplicated points.");
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < n - 3; i++) {
           Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());
           if (isCollinear(pointsCopy[i], pointsCopy[i+1], pointsCopy[i+2], pointsCopy[i+3])) {
               Point startPoint = pointsCopy[i];
               Point endPoint = findLastCollinear(pointsCopy, i);
               this.segments.add(new LineSegment(startPoint, endPoint));
           }
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
   private Point findLastCollinear(Point[] points, int indexStart) {
       int i = indexStart;
       int n = points.length;
       while (i < n - 3 && isCollinear(points[i], points[i+1], points[i+2], points[i+3])) i++;
       return points[i + 2];
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
