import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) {
        Scanner popSizeEntry = new Scanner(System.in);
        Scanner genNumEntry = new Scanner(System.in);
        Scanner crossOverRateEntry = new Scanner(System.in);
        Scanner mutationRateEntry = new Scanner(System.in);

        int popSize = -1;
        int genNum = -1;
        int crossOverRate = -1;
        int mutationRate = -1;

        int [] paramArr = {popSize, genNum, crossOverRate, mutationRate};

        while (popSize < 0) {
            askPopSize(popSizeEntry);
        }
        askNumberGeneration(genNumEntry);
        askCrossOverRate(crossOverRateEntry);
        askMutationRate(mutationRateEntry);

        Path inputFile = FileSystems.getDefault ().getPath ("resources", "input.txt");
        ArrayList<String> linksStringVer = new ArrayList<>();
        try {
            Scanner scan = new Scanner (new BufferedReader(Files.newBufferedReader (inputFile, StandardCharsets.UTF_8)));
            while (scan.hasNext()) {
                String s = scan.nextLine();
                linksStringVer.add(s);
                // System.out.println (s);
            }
            scan.close();
            ArrayList<ArrayList<Integer>> linksIntVer = new ArrayList<>();
            for (int i = 0; i < linksStringVer.size(); ++i) {
                linksIntVer.add(new ArrayList<Integer>());
                String[] parts = linksStringVer.get(i).split(" ");
                linksIntVer.get(i).add(Integer.parseInt(parts[0]));
                linksIntVer.get(i).add(Integer.parseInt(parts[1]));
            }
//            for (ArrayList<Integer> arr : linksIntVer) {
//                System.out.println(arr.toString());
//            }
            HashMap <Integer, ArrayList<Integer>> adjacencyMatrix = new HashMap<>();
            for (ArrayList<Integer> arr : linksIntVer) {
                if (!adjacencyMatrix.keySet().isEmpty()){
                    //System.out.println("matrix not empty: " + adjacencyMatrix.toString());
                    if (!adjacencyMatrix.containsKey(arr.get(0))) {
                        adjacencyMatrix.put(arr.get(0), new ArrayList<>());
                        adjacencyMatrix.get(arr.get(0)).add(arr.get(1));
                        System.out.println("matrix new key: " + adjacencyMatrix.toString());
                    }
                    else {
                        adjacencyMatrix.get(arr.get(0)).add(arr.get(1));
                        System.out.println("matrix new value: " + adjacencyMatrix.toString());
                    }
                }
                else {
                    System.out.println(arr.toString());
                    adjacencyMatrix.put(arr.get(0), new ArrayList<>());
                    adjacencyMatrix.get(arr.get(0)).add(arr.get(1));
                    System.out.println("matrix first entry: " + adjacencyMatrix.toString());
                }
            }
            System.out.println(adjacencyMatrix.toString());
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private static void askMutationRate(Scanner mutationRateEntry) {
        int mutationRate;
        System.out.println("Enter mutation rate (0 - 100): ");
        mutationRate = mutationRateEntry.nextInt();
    }

    private static void askCrossOverRate(Scanner crossOverRateEntry) {
        int crossOverRate;
        System.out.println("Enter crossover rate (0 - 100): ");
        crossOverRate = crossOverRateEntry.nextInt();
    }

    private static void askNumberGeneration(Scanner genNumEntry) {
        int genNum;
        System.out.println("Enter number of generation (positive integer): ");
        genNum = genNumEntry.nextInt();
    }

    private static void askPopSize(Scanner popSizeEntry) {
        int popSize;
        System.out.println("Enter population size (positive integer): ");
        popSize = popSizeEntry.nextInt();
    }
}
