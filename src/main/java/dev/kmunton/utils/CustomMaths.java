package dev.kmunton.utils;

import java.util.List;
import java.util.Optional;

public class CustomMaths {

    // Greatest Common Factor
    public static long gcf(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (gcf(b, a % b));
        }
    }

    // Lowest Common Multiple
    public static long lcm(long a, long b) {
        return (a * b) / gcf(a, b);
    }

    // Function to find area of polygon using shoelace formula which takes a list of (x, y) vertices and total number of vertices (n)
    public static double polygonArea(List<Double> X, List<Double> Y,
                                     int n)
    {
        // Initialize area
        double area = 0.0;

        // Calculate value of shoelace formula
        int j = n - 1;
        for (int i = 0; i < n; i++)
        {
            area += (X.get(j) + X.get(i)) * (Y.get(j) - Y.get(i));

            // j is previous vertex to i
            j = i;
        }

        // Return absolute value
        return Math.abs(area / 2.0);
    }


    public static Optional<DoublePoint> calculateIntersectionPoint(
        double m1,
        double b1,
        double m2,
        double b2) {

        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        DoublePoint point = new DoublePoint(x, y);
        return Optional.of(point);
    }

    public static boolean isPositive(double number) {
        return number > 0;
    }
}
