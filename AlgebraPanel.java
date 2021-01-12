import javax.swing.*;
import java.awt.*;

/**
 * Shows information about the process of the algorithm
 */
public class AlgebraPanel extends JPanel{
    public AlgebraPanel()
    {
        setBackground(new Color(250,210,250));
    }

    /**
     * Paints the info about the next clip on the right side of the screen
     * @param g Graphics object used by JPanel
     */
    public void paint(Graphics g)
    {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        g.setFont(newFont);
        super.paintComponent(g);
        switch (Main.phase)
        {
            case SORT:
                g.drawString("The vertices are in angle order with lowest vertex",20,40);
                break;
            case SCAN:
                g.drawString("Here is the current stack of vertices for the scan:",20,40);
                g.drawString(Main.gpanel.vertexStack.toString(),20,70);
                break;
            case FINAL:
                g.drawString("Congratulations, we have the convex hull!",20,40);
        }
        g.setFont(currentFont);
    }
}
