import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

/**
 * Shows the left side of the screen with the polygon
 */
public class GraphPanel extends JPanel implements MouseListener, KeyListener {
    ArrayList<Vertex> vertices;
    Stack<Vertex> vertexStack;
    static int pointerX,pointerY;
    int index = 2;
    public GraphPanel()
    {
        setBackground(Color.white);
        addMouseListener(this);
        setFocusable(true);
        addKeyListener(this);
        vertices = new ArrayList<>();
        vertexStack = new Stack<>();
        repaint();
        pointerX = pointerY = -10;
    }

    /**
     * Sorts the points by angle with respect to the lowest point.
     */
    public void sort()
    {
        Vertex lowest = vertices.get(0);
        for (Vertex vertex: vertices)
        {
            if (vertex.getY()>lowest.getY())
            {
                lowest = vertex;
            }
            if (vertex.getY()==lowest.getY()&&vertex.getX()<lowest.getX())
            {
                lowest = vertex;
            }
        }
        Vertex finalLowest = lowest;
        ArrayList<Vertex> redundant = new ArrayList<>();
        Comparator<Vertex> comparator = (a, b) -> {
            if (a == finalLowest)
            {
                return -1;
            }
            if (b == finalLowest)
            {
                return 1;
            }
            long cross = Vertex.crossProduct(a.getX() - finalLowest.getX(), a.getY() - finalLowest.getY(), b.getX() - finalLowest.getX(), b.getY() - finalLowest.getY());
            if (cross == 0)
            {
                if (Math.hypot(a.getX() - finalLowest.getX(), a.getY() - finalLowest.getY())>Math.hypot(b.getX() - finalLowest.getX(), b.getY() - finalLowest.getY()))
                {
                    redundant.add(b);
                }
                else
                    redundant.add(a);
                return 0;
            }
            if (cross > 0)
                return 1;
            else
                return -1;

        };
        vertices.removeAll(redundant);
        vertices.sort(comparator);
        vertexStack.push(lowest);
        if (vertices.size()>1)
            vertexStack.push(vertices.get(1));
    }

    /**
     * The Graham scan applied to one point.
     * @param i the index of the point in the list vertices
     */
    public void considerPoint(int i)
    {
        i = (vertices.size()+i)%vertices.size();
        Vertex recent = vertexStack.pop();
        Vertex older = vertexStack.peek();
        if (!Vertex.leftOn(older.getCoordsArr(),recent.getCoordsArr(),vertices.get(i).getCoordsArr()))
        {
            vertexStack.push(recent);
            vertexStack.push(vertices.get(i));
        }
        else
        {
            considerPoint(i);
        }
    }
    /**
     * Paints the left side of the screen
     * @param g Graphics object used by JPanel
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int i = 0;
        for (Vertex vertex:vertices)
        {
            vertex.paint(g,i++);
        }
        for (i = 0; i < vertexStack.size()-1; i++)
        {
            g.drawLine(vertexStack.get(i).getX(),vertexStack.get(i).getY(),vertexStack.get(i+1).getX(),vertexStack.get(i+1).getY());
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * Determines what to do when mouse is pressed
     * @param e KeyEvent containing info on which mouse button was pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (Main.phase == Main.PhaseType.DRAW) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                vertices.add(new Vertex(e.getX(), e.getY()));
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                Vertex closest = vertices.get(0);
                double dist = Math.hypot(closest.getX() - e.getX(), closest.getY() - e.getY());
                for (int i = 1; i < vertices.size(); i++) {
                    Vertex vertex = vertices.get(i);
                    if (Math.hypot(vertex.getX() - e.getX(), vertex.getY() - e.getY()) < dist) {
                        dist = Math.hypot(vertex.getX() - e.getX(), vertex.getY() - e.getY());
                        closest = vertex;
                    }
                }
                vertices.remove(closest);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}