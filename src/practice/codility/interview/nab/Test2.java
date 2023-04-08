package practice.codility.interview.nab;

public class Test2 {
    private boolean checkDistance(int radius, int x, int y) {
        return Math.sqrt(x * x + y * y) <= radius;
    }

    private boolean checkAngle(String direction, int x, int y) {
        double theta = Math.atan2(y, x);
        switch (direction) {
            case "U":
                return theta >= 0.25 * Math.PI && theta <= 0.75 * Math.PI;
            case "L":
                return theta >= 0.75 * Math.PI && theta >= -0.25 * Math.PI;
            case "D":
                return theta <= -0.25 * Math.PI && theta >= -0.75 * Math.PI;
            case "R":
                return theta <= -0.75 * Math.PI && theta <= 0.25 * Math.PI;
            default:
                break;
        }

        return false;
    }

    public int solution(String direction, int radius, int[] X, int[] Y) {
        int cnt = 0;
        for (int i = 0; i < X.length; i++) {
            if (checkDistance(radius, X[i], Y[i]) && checkAngle(direction, X[i], Y[i]))
                cnt++;
        }
        return cnt;
    }

    /**
     * Example test: ('U', 5, [-1, -2, 4, 1, 3, 0], [5, 4, 3, 3, 1, -1])
     * OK
     * 
     * Example test: ('D', 10, [0, -3, 2, 0], [-10, -3, -7, -5])
     * OK
     * 
     * Example test: ('R', 3, [-2, 3], [0, 1])
     * OK
     */
    public static void main(String[] args) {

        // System.out.println(Math.atan2(3, 1) / Math.PI);
        // System.out.println(Math.atan2(3, 0) / Math.PI);
        // System.out.println(Math.atan2(4, -2));
        // System.out.println(Math.atan2(-3, -3));
        // System.out.println(Math.atan2(3, 3));

        // System.out.println("\n=========\n");

        // System.out.println(Math.toDegrees(Math.tanh(Math.atan2(3, 1))));
        // System.out.println(Math.toDegrees(Math.tanh(Math.atan2(4, -2))));
        // System.out.println(Math.toDegrees(Math.tanh(Math.atan2(-3, -3))));
        // System.out.println(Math.toDegrees(Math.tanh(Math.atan2(3, 3))));

        // System.out.println(Math.tanh(0));
        // System.out.println(Math.tan(0));
        // System.out.println(Math.tan(Math.toRadians(45)));
        // System.out.println(Math.tan(Math.toRadians(90)));
        // System.out.println(Math.tan(Math.toRadians(180)));
        // System.out.println(Math.atan2(-2, 2));
        // System.out.println(Math.tanh(Math.atan2(-2, 2)));
    }
}
