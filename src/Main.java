import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
* CS4106 group project, a machine learning algorithm to draw graphs
* @author: Jinqi Wang
* @author: Guillaume
* @author: Barthélémy Drabczuk
**/
public class Main {

    /*
    * for now everything is in the main function, but later pieces will be moved in different functions or objects
    */
    public static void main (String [] args) {

        // scanner to get user inputs
        Scanner in = new Scanner(System.in);

        // 4 variables, for the 4 user inputs
        int popSize = 20;
        int genNum = -1;
        int crossOverRate = -1;
        int mutationRate = -1;

        /*
        * Getting user input part
        */

        while (popSize < 0) popSize = askPopSize(in);
        while (genNum < 0) genNum = askNumberGeneration(in);
        while (crossOverRate < 0  || crossOverRate > 100) crossOverRate = askCrossOverRate(in);
        while (mutationRate < 0 || mutationRate > 100) mutationRate = askMutationRate(in);

        in.close();

        /*
        * End of getting user input part
        */

        /*
        * Input file reading part
        */

        // get the list of link through the input file
        Path inputFile = FileSystems.getDefault ().getPath ("resources", "input.txt");
        ArrayList<String> linksStringVer = new ArrayList<>();
        try {
            Scanner scan = new Scanner (new BufferedReader(Files.newBufferedReader (inputFile, StandardCharsets.UTF_8)));
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

            HashMap <Integer, ArrayList<Integer>> adjacencyMatrix = new HashMap<>();
            for (ArrayList<Integer> arr : linksIntVer) {
                if (!adjacencyMatrix.keySet().isEmpty()){
                    if (!adjacencyMatrix.containsKey(arr.get(0))) {
                        adjacencyMatrix.put(arr.get(0), new ArrayList<>());
                    }
                }
                else {
                    adjacencyMatrix.put(arr.get(0), new ArrayList<>());
                }
                adjacencyMatrix.get(arr.get(0)).add(arr.get(1));
            }
            System.out.println(adjacencyMatrix.toString());

            /*
             * End of creation of the adjacency matrix
             */

            /*
             * Initialisation of the first currentPopulation
             */

            ArrayList<ArrayList<Integer>> currentPopulation = new ArrayList<>();

            ArrayList<Integer> nodes = new ArrayList<>();
            for (int n = 1; n < adjacencyMatrix.keySet().size() + 1; ++n) {
                nodes.add(n);
            }

            for (int i = 0; i < popSize; ++i) {
                nodes = shuffle(nodes);
                currentPopulation.add(nodes);
            }

            System.out.println(currentPopulation.toString());

            /*
             * End of initialisation of the first currentPopulation
             */

            ArrayList<ArrayList<Integer>> nextPopulation = new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private static int askMutationRate(Scanner scan) {
        System.out.println("Enter mutation rate (0 - 100): ");
        return scan.nextInt();
    }

    private static int askCrossOverRate(Scanner scan) {
        System.out.println("Enter crossover rate (0 - 100): ");
        return scan.nextInt();
    }

    private static int askNumberGeneration(Scanner scan) {
        System.out.println("Enter number of generation (positive integer): ");
        return scan.nextInt();
    }

    private static int askPopSize(Scanner scan) {
        System.out.println("Enter population size (positive integer): ");
        return scan.nextInt();
    }

    //Knuth shuffle : https://en.wikipedia.org/wiki/Fisher-Yates_shuffle
    private static ArrayList<Integer> shuffle (ArrayList<Integer> ar) {
        Random rn = new Random();

        for (int i = ar.size() - 1; i > 0; --i){
            int index = rn.nextInt(i + 1);
            Integer a = ar.get(index);
            ar.set(index, ar.get(i));
            ar.set(i, a);
        }

        return new ArrayList<>(ar);
    }
}
