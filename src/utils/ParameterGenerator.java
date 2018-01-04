package utils;

import java.util.Random;

public class ParameterGenerator {
    public static Random rnd = new Random();

    public static int getInt(int min, int max) {
        return min + rnd.nextInt(max - min + 1);
    }
}
