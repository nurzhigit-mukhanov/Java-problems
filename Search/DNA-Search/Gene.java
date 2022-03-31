import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Genes {
    public enum Nucleotide {
        A, C, G, T
    }

    public static class Codon implements Comparable<Codon> {
        public final Nucleotide first, second, third;
        private final Comparator<Codon> comparator = 
            Comparator.comparing((Codon c) -> c.first)
            .thenComparing((Codon c) -> c.second)
            .thenComparing((Codon c) -> c.third);

        public Codon(String codoStr) {
            first = Nucleotide.valueOf(codonStr.substring(0, 1));
            second = Nucleotide.valueOf(codonStr.substring(1, 2));
            third = Nucleotide.valueOf(codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon other) {
            // initially, first object will be compared, then second, etc
            // in other words, first object is prioritized over the second
            // similarly, the second over the third
            return comparator.compare(this, other);
        }
    }

    private ArrayList<Codon> codons = new ArrayList<>();

    public Genes(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i += 3) {
            // codon will be formed from three symbols
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }
    
    public boolean linearContains(Codon key) {
        for (Codon codon : codons) {
            if (codon.compareTo(key) == 0) {
                return true; //search for the match
            }
        }
        return false;
    }

    public boolean binaryContains(Codon key) {
        // it will work only with sorted collections
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        int low = 0;
        int high = sortedCodons.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = codons.get(middle).compareTo(key);
            if (comparison < 0) {  //avg codon less than x
                low = middle + 1;
            } else if (comparison > 0) { //avg codon greater than x
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String geneStr = "ACGTGGCTCTCTAACGACGTA";

        Genes myGene = new Genes(geneStr);
        Codon acg = new Codon("ACG");
        Codon gat = new Codon("GAT");
        System.out.println(myGene.linearContains(acg)); //true
        System.out.println(myGene.linearContains(gat)); //false
        System.out.println(myGene.binaryContains(acg)); //true
        System.out.println(myGene.binaryContains(gat)); //false
    }
}