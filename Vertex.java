import java.awt.*;
import java.util.Objects;

public class Vertex {
    private final int x,y;
    private final Color color;

    /**
     * Constructs a vertex
     * @param x the x-coord of the vertex
     * @param y the y-coord of the vertex
     */
    public Vertex(int x,int y)
    {
        this.x = x;
        this.y = y;
        color = new Color(200,100,100);
    }

    /**
     * Makes an array with the coords of the vertex
     * @return integer array [x,y]
     */
    public int[] getCoordsArr()
    {
        return new int[] {x,y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics g, int i)
    {
        g.setColor(color);
        g.fillOval(x-4,y-4,8,8);
        g.setColor(Color.BLACK);
        g.drawString(""+i,x+5,y+5);
    }

    @Override
    public String toString() {
        return ""+Main.gpanel.vertices.indexOf(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x &&
                y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Determines if a point is to the left of or inline with two other points
     * @param a coordinates of a point
     * @param b coordinates of another point
     * @param c coordinates of the point that may or may not be to the left
     * @return true if c is to the left of or collinear with a and b, false elsewise.
     */
    public static boolean leftOn(int[]a, int[] b, int[] c) {
        return crossProduct(a,b,a,c) >= 0;
    }

    /**
     * Determines signed area of the parallelogram with points a,b,c,d
     * @param a coordinates of point a
     * @param b coordinates of point b
     * @param c coordinates of point c
     * @param d coordinates of point d
     * @return signed area of the parallelogram with points a,b,c,d
     */
    public static int crossProduct(int[] a, int[] b, int[] c, int[]d)
    {
        return (b[0] - a[0]) * (d[1] - c[1]) - (b[1] - a[1]) * (d[0] - c[0]);
    }

    public static long crossProduct(int i, int i1, int i2, int i3) {
        return i*i3-i2*i1;
    }
}
