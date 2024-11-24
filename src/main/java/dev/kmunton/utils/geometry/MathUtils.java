package dev.kmunton.utils.geometry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {

  /**
   * Extended Euclidean Algorithm.
   * Computes the GCD of two numbers and returns the coefficients (x, y) for:
   * ax + by = gcd(a, b)
   *
   * @param a First integer
   * @param b Second integer
   * @return An array of three values: {gcd, x, y}
   */
  public static long[] extendedGcd(long a, long b) {
    long x0 = 1;
    long x1 = 0;
    long y0 = 0;
    long y1 = 1;
    while (b != 0) {
      long q = a / b;
      long temp = a;
      a = b;
      b = temp % b;

      // Update coefficients
      long newX = x0 - q * x1;
      long newY = y0 - q * y1;

      x0 = x1; x1 = newX;
      y0 = y1; y1 = newY;
    }
    return new long[]{a, x0, y0};
  }

  /**
   * Computes the modular inverse of `a` modulo `m` using the Extended Euclidean Algorithm.
   *
   * @param a The number to find the modular inverse of.
   * @param m The modulus.
   * @return The modular inverse of `a` modulo `m`, or -1 if no inverse exists.
   */
  public static long modularInverse(long a, long m) {
    long[] gcdExt = extendedGcd(a, m);
    long gcd = gcdExt[0];
    long x = gcdExt[1];

    if (gcd != 1) {
      return -1; // No modular inverse exists
    }

    // x may be negative, so make it positive
    return (x % m + m) % m;
  }

  /**
   * Applies the Chinese Remainder Theorem (CRT) to solve simultaneous congruences.
   * Optimized for modular arithmetic with large numbers.
   *
   * @param remainders A list of remainders.
   * @param moduli     A list of moduli.
   * @return The smallest non-negative solution to the system of congruences, or -1 if no solution exists.
   */
  public static long chineseRemainder(List<Long> remainders, List<Long> moduli) {
    Objects.requireNonNull(remainders, "Remainders cannot be null");
    Objects.requireNonNull(moduli, "Moduli cannot be null");

    if (remainders.size() != moduli.size()) {
      throw new IllegalArgumentException("Remainders and moduli must have the same size.");
    }

    long product = 1;
    for (long mod : moduli) {
      product *= mod;
    }

    long result = 0;

    for (int i = 0; i < remainders.size(); i++) {
      long partialProduct = product / moduli.get(i);
      long inverse = modularInverse(partialProduct, moduli.get(i));
      if (inverse == -1) {
        return -1;
      }
      result = (result + remainders.get(i) * partialProduct * inverse) % product;
    }

    return result >= 0 ? result : result + product; // Ensure non-negative result
  }

  /**
   * Computes the Greatest Common Divisor (GCD) of two integers using the Euclidean Algorithm.
   *
   * @param a First integer.
   * @param b Second integer.
   * @return The GCD of a and b.
   */
  public static long gcd(long a, long b) {
    while (b != 0) {
      long temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  /**
   * Computes the Least Common Multiple (LCM) of two integers.
   *
   * @param a First integer.
   * @param b Second integer.
   * @return The LCM of a and b.
   */
  public static long lcm(long a, long b) {
    return Math.abs(a / gcd(a, b) * b); // Avoid overflow by dividing first
  }

  /**
   * Modular Exponentiation: Computes (base^exp) % mod efficiently.
   *
   * @param base The base.
   * @param exp  The exponent.
   * @param mod  The modulus.
   * @return The result of (base^exp) % mod.
   */
  public static long modularExponentiation(long base, long exp, long mod) {
    if (mod <= 0) {
      throw new IllegalArgumentException("Modulus must be positive");
    }
    if (mod == 1) return 0;

    // Adjust base to be positive modulo mod
    base = ((base % mod) + mod) % mod;

    long result = 1;

    while (exp > 0) {
      if ((exp & 1) == 1) { // If exp is odd
        result = (result * base) % mod;
      }
      base = (base * base) % mod;
      exp >>= 1; // Divide exp by 2
    }
    return result;
  }

  // Function to find area of polygon using shoelace formula which takes a list of (x, y) vertices and total number of vertices (n)
  public static double polygonArea(List<Double> x, List<Double> y, int n) {
    if (n < 3 || x.size() != n || y.size() != n) {
      throw new IllegalArgumentException("Invalid input for polygon vertices.");
    }

    double area = 0.0;
    int prevIndex = n - 1;

    for (int i = 0; i < n; i++) {
      area += (x.get(prevIndex) + x.get(i)) * (y.get(prevIndex) - y.get(i));
      prevIndex = i;
    }

    return Math.abs(area / 2.0);
  }


  public static Optional<DoublePoint> calculateIntersectionPoint(double m1, double b1, double m2, double b2) {
    final double EPSILON = 1e-10;

    if (Math.abs(m1 - m2) < EPSILON) {
      return Optional.empty(); // Lines are parallel
    }

    double x = (b2 - b1) / (m1 - m2);
    double y = m1 * x + b1;

    return Optional.of(new DoublePoint(x, y));
  }

  public static boolean isPositive(double number) {
    return number > 0;
  }

  /**
   * Finds all factors of a given number.
   *
   * @param n The number.
   * @return A list of factors of n.
   */
  public static List<Integer> factors(int n) {
    List<Integer> result = new ArrayList<>();
    for (int i = 1; i <= Math.sqrt(n); i++) {
      if (n % i == 0) {
        result.add(i);
        if (i != n / i) {
          result.add(n / i);
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  /**
   * Generates the nth Fibonacci number.
   *
   * @param n The position in the Fibonacci sequence.
   * @return The nth Fibonacci number.
   */
  public static long fibonacci(int n) {
    if (n <= 1) return n;
    long a = 0;
    long b = 1;
    for (int i = 2; i <= n; i++) {
      long temp = a + b;
      a = b;
      b = temp;
    }
    return b;
  }

  /**
   * Checks if a given number is a prime number.
   *
   * @param n The number to check.
   * @return true if n is a prime number, false otherwise.
   */
  public static boolean isPrime(int n) {
    if (n <= 1) {
      return false; // Numbers less than 2 are not prime
    }
    if (n <= 3) {
      return true; // 2 and 3 are prime numbers
    }
    if (n % 2 == 0 || n % 3 == 0) {
      return false; // Exclude multiples of 2 and 3
    }
    for (int i = 5; i <= n / i; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) {
        return false; // Check divisibility by numbers of form 6k ± 1
      }
    }
    return true; // Number is prime
  }

  public static boolean isPrimeBigInteger(int n) {
    BigInteger bigInt = BigInteger.valueOf(n);
    return bigInt.isProbablePrime(10); // 10 is the certainty level
  }
}

