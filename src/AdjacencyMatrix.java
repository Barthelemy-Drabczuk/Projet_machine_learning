import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdjacencyMatrix {
    private HashMap<Integer, ArrayList<Integer>> adjacency;
    private static AdjacencyMatrix instance;

    public static AdjacencyMatrix getInstance() {
        if (instance == null) {
            instance = new AdjacencyMatrix();
        }
        return instance;
    }

    private AdjacencyMatrix() {
        /*
         * Input file reading part
         */

        // get the list of link through the input file
        Path inputFile = FileSystems.getDefault().getPath("resources", "input.txt");
        ArrayList<String> linksStringVer = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new BufferedReader(Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)));
            while (scan.hasNext()) {
                String s = scan.nextLine();
                // get all the file line by line, in order to convert them later in list of int
                linksStringVer.add(s);
            }
            scan.close();

            /*
             * End of input file reading part
             */

            /*
             * Conversion part
             */
            // creation of the converted int links
            ArrayList<ArrayList<Integer>> linksIntVer = new ArrayList<>();

            // converting lines into int
            for (int i = 0; i < linksStringVer.size(); ++i) {
                linksIntVer.add(new ArrayList<>());
                String[] parts = linksStringVer.get(i).split(" ");
                linksIntVer.get(i).add(Integer.parseInt(parts[0]));
                linksIntVer.get(i).add(Integer.parseInt(parts[1]));
            }

            /*
             * End of conversion part
             */

            /*
             * Creation of the adjacency matrix
             */

            this.adjacency = new HashMap<>();
            for (ArrayList<Integer> arr : linksIntVer) {
                if (!this.adjacency.keySet().isEmpty()) {
                    if (!this.adjacency.containsKey(arr.get(0))) {
                        this.adjacency.put(arr.get(0), new ArrayList<>());
                    }
                } else {
                    this.adjacency.put(arr.get(0), new ArrayList<>());
                }
                this.adjacency.get(arr.get(0)).add(arr.get(1));
            }

            /*
             * End of creation of the adjacency matrix
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, ArrayList<Integer>> getMatrix () {
        return this.adjacency;
    }
}
