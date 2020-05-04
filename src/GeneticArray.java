import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GeneticArray extends ArrayList<Integer> {

    private ArrayList<Integer> content;

    public GeneticArray(int initialCapacity) {
        super(initialCapacity);
    }

    public GeneticArray() {
        this.content = new ArrayList<>();
    }

    public GeneticArray(Collection<? extends Integer> c) {
        super(c);
    }

    public void crossOverWith (GeneticArray ga) throws IndexOutOfBoundsException {
        if (!this.content.isEmpty()) {
            Random rn = new Random();
            int cuttingPoint = rn.nextInt(ga.size() - 2);

            for (int i =  0; i < cuttingPoint; ++i) {
                Integer save = this.content.get(i);
                this.content.set(i, ga.get(i));
                ga.set(i, save);
            }
        }
        else {
            throw new IndexOutOfBoundsException("crossOverWith: Empty Array");
        }
    }//crossOver

    public void mutate () throws IndexOutOfBoundsException {
        if (!this.content.isEmpty()) {
            Random rn = new Random();
            int m1 = rn.nextInt(this.content.size() - 1);
            int m2 = rn.nextInt(this.content.size() - 1);

            Integer save = this.content.get(m1);
            this.content.set(m1, this.content.get(m2));
            this.content.set(m2, save);
        }
        else {
            throw new IndexOutOfBoundsException("mutate: Empty Array");
        }
    }//mutate

    public static GeneticArray fromArrayList (ArrayList<Integer> ar) {
        return new GeneticArray(ar);
    }
}
