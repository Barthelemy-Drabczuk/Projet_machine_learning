import java.util.*;

/**
* CS4106 group project, a machine learning algorithm to draw graphs
* @author: Jinqi Wang
* @author: Guillaume Humbert
* @author: Barthélémy Drabczuk
**/
public class Main {

    private static final HashMap<Integer, ArrayList<Integer>> adjacencyMatrix = AdjacencyMatrix.getInstance().getMatrix();

    /*
    * for now everything is in the main function, but later pieces will be moved in different functions or objects
    */
    public static void main (String [] args) {

        // scanner to get user inputs
        Scanner in = new Scanner(System.in);

        // 4 variables, for the 4 user inputs
        int popSize = -1;
        int genNum = -1;
        int crossOverRate = -1;
        int mutationRate = -1;

        /*
        * Getting user input part
        */

        while (popSize < 0) popSize = askPopSize(in);
        while (genNum < 0) genNum = askNumberGeneration(in);
        while (crossOverRate < 0  || crossOverRate > 100) crossOverRate = askCrossOverRate(in);
        while ( (mutationRate < 0 || mutationRate > 100) && crossOverRate + mutationRate <= 100) mutationRate = askMutationRate(in);

        in.close();

        /*
        * End of getting user input part
        */

        /*
        * Initialisation of the first currentPopulation
        */

        ArrayList<ArrayList<Integer>> currentPopulation = new ArrayList<>();

        ArrayList<Integer> nodes = new ArrayList<>();
        for (int n = 1; n < adjacencyMatrix.keySet().size() + 1; ++n) {
            nodes.add(n);
        }

        //shuffle the first order to get new ones and create first population
        for (int i = 0; i < popSize; ++i) {
            nodes = shuffle(nodes);
            currentPopulation.add(nodes);
        }

        System.out.println(adjacencyMatrix.toString());
        System.out.println(currentPopulation.toString());

        /*
        * End of initialisation of the first currentPopulation
        */

        for (int generation = 0; generation < genNum; ++generation) {
            /*
             * Creation of the next population
             */

            PriorityQueue<ArrayList<Integer>> populationRanking = new PriorityQueue<>(new Comparator<ArrayList<Integer>>() {
                @Override
                public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                    return fitness(o2) - fitness(o1);
                }
            });

            /*
             * Increasing number of good ordering
             */

            populationRanking.addAll(currentPopulation);
            ArrayList<GeneticArray> orderedPop = new ArrayList<>();

            for (int i = 0; i < populationRanking.size(); ++i){
                orderedPop.add(GeneticArray.fromArrayList(populationRanking.poll()));
            }

            //indexes of the end of population s2
            int lastS2Index = (populationRanking.size() * 2) / 3;

            //replacement of s3 population by s1 population
            for (int i = lastS2Index; i < orderedPop.size(); ++i) {
                orderedPop.set(i, orderedPop.get(i - lastS2Index));
            }

            /*
             * End of increasing number of good ordering
             */

            ArrayList<GeneticArray> nextPopulation = new ArrayList<>();

            while (!nextPopulation.equals(popSize)) {
                Random rn = new Random();
                int pr = rn.nextInt(100);

                //crossover
                if (crossOverRate >= pr) {
                    //picking 2 candidates
                    GeneticArray firstOne = orderedPop.get(rn.nextInt(orderedPop.size()));
                    GeneticArray secondOne = orderedPop.get(rn.nextInt(orderedPop.size()));

                    //crossover process
                    firstOne.crossOverWith(secondOne);

                    //adding to new population and removing from the current one
                    nextPopulation.add(firstOne);
                    orderedPop.remove(firstOne);
                    nextPopulation.add(secondOne);
                    orderedPop.remove(secondOne);
                }
                //mutates
                else if (crossOverRate <= pr && pr <= (crossOverRate + mutationRate)) {
                    GeneticArray selected = orderedPop.get(rn.nextInt(orderedPop.size() - 1));
                    selected.mutate();
                    nextPopulation.add(selected);
                    orderedPop.remove(selected);
                }
                //reproduction
                else if (crossOverRate == mutationRate && crossOverRate <= pr) {
                    GeneticArray selected = orderedPop.get(rn.nextInt(orderedPop.size() - 1));
                    nextPopulation.add(selected);
                    orderedPop.remove(selected);
                }
            }

        }

    }//main


    /*
    * Utility functions
    */

    private static int fitness(ArrayList<Integer> ar) {
        /*
        * Calculation of coordinates for every node in the graph
        * cf: paper subject
        */

        //Chunk size for the particular order
        final double CHUNK = (2 * Math.PI) / (ar.size() + 1);

        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();

        for (int i = 0; i < ar.size(); ++i) {
            coordinates.add(new ArrayList<Double>());
            coordinates.get(i).add(Math.cos( (double) i * CHUNK));
            coordinates.get(i).add(Math.sin( (double) i * CHUNK));
        }

        HashMap<Integer, ArrayList<Double>> nodeCoordinates = new HashMap<>();
        int count = 0;
        for (Integer i : ar) {
            nodeCoordinates.put(i, coordinates.get(count));
            count++;
        }

        ArrayList<Integer> avgDistForNodes = new ArrayList<>(ar.size());

        double totAvg = 0;

        for (int i = 0; i < avgDistForNodes.size(); ++i) {
            double avg = 0;

            //x,y coordinates of current node
            double nodeX = nodeCoordinates.get(ar.get(i)).get(0);
            double nodeY = nodeCoordinates.get(ar.get(i)).get(1);

            for (int link = 0; link < adjacencyMatrix.get(i).size(); ++link) {
                // add to the average the distance to every node for the current node
                avg += Math.sqrt
                        ( Math.pow(
                                (nodeCoordinates.get(adjacencyMatrix.get(i).get(link)).get(0) - nodeX) , 2
                        )
                                + Math.pow(
                                        (nodeCoordinates.get(adjacencyMatrix.get(i).get(link)).get(1) - nodeY), 2
                        )
                );
            }
            // divide the average distances by the number of links
            avg /= adjacencyMatrix.get(i).size();
            totAvg += avg;
        }

        return (int) (totAvg/ar.size()) * 100;
    }//fitness

    private static int askMutationRate(Scanner scan) {
        System.out.println("Enter mutation rate (0 - 100): ");
        return scan.nextInt();
    }//askMutationRate

    private static int askCrossOverRate(Scanner scan) {
        System.out.println("Enter crossover rate (0 - 100): ");
        return scan.nextInt();
    }//askCrossOverRate

    private static int askNumberGeneration(Scanner scan) {
        System.out.println("Enter number of generation (positive integer): ");
        return scan.nextInt();
    }//askNumberGeneration

    private static int askPopSize(Scanner scan) {
        System.out.println("Enter population size (positive integer): ");
        return scan.nextInt();
    }//askPopSize

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
    }//shuffle

    /*
     * End of utility functions
     */
}//Main
