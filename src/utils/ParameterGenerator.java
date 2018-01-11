package utils;

import java.util.Random;

public class ParameterGenerator {
    public static Random rnd = new Random();

    /**
     * Returns a random int thats within the passed boundaries.
     * @param min Lower boundary
     * @param max Upper boundary
     * @return Random integer
     */
    public static int getInt(int min, int max) {
        return min + rnd.nextInt(max - min + 1);
    }
}
