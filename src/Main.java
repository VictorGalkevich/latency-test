import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

    public static int[] remove(int[] array, int index) {
        int[] result = new int[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        return result;
    }

    public static int[] randomCyclicPermutation(int length) {
        int[] result = new int[length];
        int[] unusedIndexes = new int[length - 1];
        for (int i = 0; i < length - 1; i++) {
            unusedIndexes[i] = i + 1;
        }
        int currentIndex = 0;
        Random rand = new Random();
        for (int i = 0; i < length - 1; i++) {
            int r = rand.nextInt(unusedIndexes.length);
            int nextInd = unusedIndexes[r];
            unusedIndexes = remove(unusedIndexes, r);
            result[currentIndex] = nextInd;
            currentIndex = nextInd;
        }
        return result;
    }

    public static double benchmarkLatency(int sizeBytes, int iterations) {
        int[] array = randomCyclicPermutation(sizeBytes / 4);
        int pointer = 0;
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            pointer = array[pointer];
        }
        return (double) (System.nanoTime() - start) / iterations;
    }

    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder builder = new StringBuilder();
        for (double i = 5000; i <= 20000000; i *= 1.2) {
            builder.append((int) i).append(" ").append(benchmarkLatency((int) i, 100000000)).append("\n");
            System.out.println(i);
        }
        write(builder.toString(), "output.txt");
    }

    private static void write(String string, String path) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(path)) {
            pw.write(string);
            pw.flush();
        }
    }
}