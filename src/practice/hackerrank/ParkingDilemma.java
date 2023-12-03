package practice.hackerrank;

import java.util.Comparator;
import java.util.List;

/**
 * Positive Thinking Company interview test
 */
public class ParkingDilemma {

    /*
     * Complete the 'carParkingRoof' function below.
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     * 1. LONG_INTEGER_ARRAY cars
     * 2. INTEGER k
     */
    public static long carParkingRoof(List<Long> cars, int k) {
        cars.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("cars = " + cars);
        System.out.println("k = " + k);
        int n = cars.size();
        long minRoofLen = Long.MAX_VALUE;
        long currRoofLen;
        for (int i = 0; i < n - k + 1; i++) {
            System.out.printf("i = %d, left car = %d, right car = %d%n", i, cars.get(i), cars.get(i + k - 1));
            currRoofLen = cars.get(i + k - 1) - cars.get(i) + 1;
            System.out.println("currRoofLen = " + currRoofLen);
            minRoofLen = Math.min(minRoofLen, currRoofLen);
        }

        return minRoofLen;
    }

}
