import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public static JFrame frame = new JFrame("Graham Scan Demonstrator");
    public static GraphPanel gpanel;
    public static AlgebraPanel algebraPanel;
    enum PhaseType {DRAW, SORT, SCAN, FINAL}
    public static PhaseType phase = PhaseType.DRAW;

    /**
     * Main method of the program
     * @param args args
     */
    public static void main(String[] args)
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        frame.setLayout(borderLayout);
        frame.setSize(1200,600);
        gpanel = new GraphPanel();
        gpanel.setPreferredSize(new Dimension(700,400));
        frame.add(gpanel, BorderLayout.WEST);
        algebraPanel = new AlgebraPanel();
        algebraPanel.setPreferredSize(new Dimension(500,400));
        frame.add(algebraPanel, BorderLayout.LINE_END);
        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(1200,100));
        frame.add(infoPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Activated when the CONTINUE button is pressed, advances to the next Stage.
     */
    public static void phaseAdd()
    {
        if (gpanel.vertexStack.size()==0 ||gpanel.vertexStack.get(0)==gpanel.vertexStack.peek()||phase != PhaseType.SCAN)
        {
            phase = PhaseType.values()[(phase.ordinal()+1)%4];
        }
        switch (phase)
        {
            case SCAN:
                gpanel.considerPoint(gpanel.index++);
                break;
            case DRAW:
                gpanel.vertexStack.clear();
                gpanel.index = 2;
                break;
            case SORT:
                gpanel.sort();
                break;
        }
        gpanel.repaint();
        algebraPanel.repaint();
    }

    /**
     * Activated when the CLEAR button is pressed, gets rid of the polygon and returns to DRAW Stage.
     */
    public static void phaseClear() {
        gpanel.vertices = new ArrayList<>();
        gpanel.vertexStack = new Stack<>();
        gpanel.repaint();
        phase = PhaseType.DRAW;
        algebraPanel.repaint();
    }
}
