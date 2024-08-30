package moe.wolfgirl.powerfuljs.utils;

public class MathUtils {
    public static long min(long... numbers) {
        long num = Long.MAX_VALUE;
        for (long number : numbers) {
            if (number < num) num = number;
        }
        return num;
    }

    public static int min(int... numbers) {
        int num = Integer.MAX_VALUE;
        for (int number : numbers) {
            if (number < num) num = number;
        }
        return num;
    }
}
