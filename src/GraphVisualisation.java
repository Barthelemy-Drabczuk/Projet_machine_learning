import javax.swing.*;
import java.awt.*;

public class GraphVisualisation extends JFrame {
    public static final String TITLE = "Graph Visualisation";
    private static final int WIDTH = 960;
    private static final int HEIGHT = 960;
    private int [][] adjacencyMatrix;
    private int numberOfVertices;
    private int[] ordering;
    private double chunk;

    public GraphVisualisation (int [][] adjacencyMatrix, int [] ordering, int numberOfVertices) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.ordering = ordering;
        this.numberOfVertices = numberOfVertices;
        this.chunk = (Math.PI * 2) / ( (double) numberOfVertices);

        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        int radius = 100;
        int mov = 200;

        for (int i = 0; i < this.numberOfVertices; ++i) {
            for (int j = 0; j < this.numberOfVertices; ++j) {
                if (this.adjacencyMatrix[this.ordering[i]][this.ordering[j]] == 1) {
                    g.drawLine(( (int) Math.cos(i * this.chunk) * radius) + mov,
                            ( (int) Math.sin(i * this.chunk) * radius) + mov,
                            ( (int) Math.cos(j * this.chunk) * radius) + mov,
                            ( (int) Math.sin(j * this.chunk) * radius) + mov);
                }
            }
        }
    }
}
