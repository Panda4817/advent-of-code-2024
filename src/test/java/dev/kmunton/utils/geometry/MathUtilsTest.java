package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

class MathUtilsTest {

  @Test
  void extendedGcd_coprimeNumbers_returnsGcdAndCoefficients() {
    long a = 240;
    long b = 46;
    long[] result = MathUtils.extendedGcd(a, b);
    long gcd = result[0];
    long x = result[1];
    long y = result[2];

    // Verify GCD
    assertEquals(2, gcd);

    // Verify the coefficients satisfy the equation
    assertEquals(gcd, a * x + b * y);
  }


  @Test
  void extendedGcd_nonCoprimeNumbers_returnsGcdAndCoefficients() {
    long a = 48;
    long b = 18;
    long[] result = MathUtils.extendedGcd(a, b);
    long gcd = result[0];
    long x = result[1];
    long y = result[2];

    // Verify GCD
    assertEquals(6, gcd);

    // Verify the coefficients satisfy the equation
    assertEquals(gcd, a * x + b * y);
  }

  @Test
  void modularInverse_numberWithInverse_returnsInverse() {
    long a = 3;
    long m = 11;
    long inverse = MathUtils.modularInverse(a, m);
    assertEquals(4, inverse);
    assertEquals(1, (a * inverse) % m);
  }

  @Test
  void modularInverse_numberWithoutInverse_returnsMinusOne() {
    long a = 6;
    long m = 9;
    long inverse = MathUtils.modularInverse(a, m);
    assertEquals(-1, inverse);
  }

  @Test
  void chineseRemainder_validInput_returnsCorrectResult() {
    List<Long> remainders = Arrays.asList(2L, 3L, 2L);
    List<Long> moduli = Arrays.asList(3L, 5L, 7L);
    long result = MathUtils.chineseRemainder(remainders, moduli);
    assertEquals(23, result);
  }

  @Test
  void chineseRemainder_invalidInput_returnsMinusOne() {
    // Moduli are not co-prime
    List<Long> remainders = Arrays.asList(1L, 1L);
    List<Long> moduli = Arrays.asList(2L, 4L);
    long result = MathUtils.chineseRemainder(remainders, moduli);
    assertEquals(-1, result);
  }

  @Test
  void gcd_positiveNumbers_returnsCorrectGcd() {
    assertEquals(14, MathUtils.gcd(42, 56));
  }

  @Test
  void gcd_oneNumberIsZero_returnsOtherNumber() {
    assertEquals(42, MathUtils.gcd(42, 0));
    assertEquals(42, MathUtils.gcd(0, 42));
  }

  @Test
  void lcm_positiveNumbers_returnsCorrectLcm() {
    assertEquals(168, MathUtils.lcm(42, 56));
  }

  @Test
  void lcm_oneNumberIsZero_returnsZero() {
    assertEquals(0, MathUtils.lcm(0, 56));
    assertEquals(0, MathUtils.lcm(42, 0));
  }

  @Test
  void modularExponentiation_validInput_returnsCorrectResult() {
    assertEquals(3, MathUtils.modularExponentiation(3, 13, 7));
  }

  @Test
  void modularExponentiation_modulusOne_returnsZero() {
    assertEquals(0, MathUtils.modularExponentiation(10, 10, 1));
  }

  @Test
  void modularExponentiation_negativeBase_returnsCorrectResult() {
    assertEquals(1, MathUtils.modularExponentiation(-2, 3, 9));
  }

  @Test
  void modularExponentiation_zeroExponent_returnsOne() {
    assertEquals(1, MathUtils.modularExponentiation(5, 0, 13));
  }

  @Test
  void polygonArea_validPolygon_returnsCorrectArea() {
    List<Double> x = Arrays.asList(0.0, 4.0, 4.0, 0.0);
    List<Double> y = Arrays.asList(0.0, 0.0, 3.0, 3.0);
    double area = MathUtils.polygonArea(x, y, 4);
    assertEquals(12.0, area, 0.0001);
  }

  @Test
  void polygonArea_invalidInput_throwsException() {
    List<Double> x = Arrays.asList(0.0, 4.0);
    List<Double> y = Arrays.asList(0.0, 0.0);
    assertThrows(IllegalArgumentException.class, () -> {
      MathUtils.polygonArea(x, y, 2);
    });
  }

  @Test
  void calculateIntersectionPoint_linesIntersect_returnsPoint() {
    Optional<DoublePoint> intersection = MathUtils.calculateIntersectionPoint(1.0, 0.0, -1.0, 2.0);
    assertTrue(intersection.isPresent());
    DoublePoint point = intersection.get();
    assertEquals(1.0, point.x(), 0.0001);
    assertEquals(1.0, point.y(), 0.0001);
  }

  @Test
  void calculateIntersectionPoint_linesParallel_returnsEmpty() {
    Optional<DoublePoint> intersection = MathUtils.calculateIntersectionPoint(1.0, 0.0, 1.0, 2.0);
    assertFalse(intersection.isPresent());
  }

  @Test
  void isPositive_positiveNumber_returnsTrue() {
    assertTrue(MathUtils.isPositive(5.0));
  }

  @Test
  void isPositive_negativeNumber_returnsFalse() {
    assertFalse(MathUtils.isPositive(-3.0));
  }

  @Test
  void isPositive_zero_returnsFalse() {
    assertFalse(MathUtils.isPositive(0.0));
  }

  @Test
  void factors_positiveNumber_returnsCorrectFactors() {
    List<Integer> expected = Arrays.asList(1, 2, 3, 4, 6, 12);
    List<Integer> actual = MathUtils.factors(12);
    assertEquals(expected, actual);
  }

  @Test
  void factors_primeNumber_returnsOneAndItself() {
    List<Integer> expected = Arrays.asList(1, 13);
    List<Integer> actual = MathUtils.factors(13);
    assertEquals(expected, actual);
  }

  @Test
  void fibonacci_zero_returnsZero() {
    assertEquals(0, MathUtils.fibonacci(0));
  }

  @Test
  void fibonacci_positiveN_returnsNthFibonacci() {
    assertEquals(0, MathUtils.fibonacci(0));
    assertEquals(1, MathUtils.fibonacci(1));
    assertEquals(1, MathUtils.fibonacci(2));
    assertEquals(2, MathUtils.fibonacci(3));
    assertEquals(3, MathUtils.fibonacci(4));
    assertEquals(5, MathUtils.fibonacci(5));
    assertEquals(8, MathUtils.fibonacci(6));
    assertEquals(13, MathUtils.fibonacci(7));
  }

  @Test
  void fibonacci_largeN_returnsNthFibonacci() {
    assertEquals(832040, MathUtils.fibonacci(30));
  }

  @Test
  void isPrime_negativeNumber_returnsFalse() {
    assertFalse(MathUtils.isPrime(-10), "-10 should not be prime");
    assertFalse(MathUtils.isPrime(-1), "-1 should not be prime");
  }

  @Test
  void isPrime_zero_returnsFalse() {
    assertFalse(MathUtils.isPrime(0), "0 should not be prime");
  }

  @Test
  void isPrime_one_returnsFalse() {
    assertFalse(MathUtils.isPrime(1), "1 should not be prime");
  }

  @Test
  void isPrime_twoAndThree_returnsTrue() {
    assertTrue(MathUtils.isPrime(2), "2 should be prime");
    assertTrue(MathUtils.isPrime(3), "3 should be prime");
  }

  @Test
  void isPrime_smallPrimes_returnsTrue() {
    int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    for (int prime : primes) {
      assertTrue(MathUtils.isPrime(prime), prime + " should be prime");
    }
  }

  @Test
  void isPrime_smallNonPrimes_returnsFalse() {
    int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20};
    for (int nonPrime : nonPrimes) {
      assertFalse(MathUtils.isPrime(nonPrime), nonPrime + " should not be prime");
    }
  }

  @Test
  void isPrime_largePrimes_returnsTrue() {
    int[] largePrimes = {101, 103, 107, 109, 113, 127, 131, 137, 139};
    for (int prime : largePrimes) {
      assertTrue(MathUtils.isPrime(prime), prime + " should be prime");
    }
  }

  @Test
  void isPrime_largeNonPrimes_returnsFalse() {
    int[] largeNonPrimes = {100, 102, 104, 105, 110, 111, 112, 114, 115};
    for (int nonPrime : largeNonPrimes) {
      assertFalse(MathUtils.isPrime(nonPrime), nonPrime + " should not be prime");
    }
  }

  @Test
  void isPrime_edgeCases_returnsCorrectResult() {
    assertFalse(MathUtils.isPrime(Integer.MIN_VALUE), "Integer.MIN_VALUE should not be prime");
    assertFalse(MathUtils.isPrime(Integer.MIN_VALUE + 1), (Integer.MIN_VALUE + 1) + " should not be prime");
    assertFalse(MathUtils.isPrime(Integer.MAX_VALUE - 1), (Integer.MAX_VALUE - 1) + " should not be prime");

    assertTrue(MathUtils.isPrime(Integer.MAX_VALUE), Integer.MAX_VALUE + " should be prime");
  }

  @Test
  void isPrime_knownCompositeNumbers_returnsFalse() {
    int[] composites = {561, 1105, 1729, 2465, 2821, 6601}; // Carmichael numbers
    for (int composite : composites) {
      assertFalse(MathUtils.isPrime(composite), composite + " should not be prime");
    }
  }

  @Test
  void isPrime_smallestPrime_returnsTrue() {
    assertTrue(MathUtils.isPrime(2), "2 should be prime");
  }

  @Test
  void isPrime_evenNumbersGreaterThanTwo_returnsFalse() {
    for (int n = 4; n <= 100; n += 2) {
      assertFalse(MathUtils.isPrime(n), n + " should not be prime");
    }
  }

  @Test
  void isPrime_randomNumbers_returnsCorrectResult() {
    int[] numbers = {29, 37, 41, 43, 47, 53, 59, 61, 67, 71};
    for (int number : numbers) {
      assertTrue(MathUtils.isPrime(number), number + " should be prime");
    }

    numbers = new int[]{30, 35, 40, 45, 50, 55, 60, 65, 70, 75};
    for (int number : numbers) {
      assertFalse(MathUtils.isPrime(number), number + " should not be prime");
    }
  }

  @Test
  void isPrimeBigInteger_negativeNumbers_returnsFalse() {
    assertFalse(MathUtils.isPrimeBigInteger(-10), "-10 should not be prime");
    assertFalse(MathUtils.isPrimeBigInteger(-1), "-1 should not be prime");
    assertFalse(MathUtils.isPrimeBigInteger(Integer.MIN_VALUE), "Integer.MIN_VALUE should not be prime");
  }

  @Test
  void isPrimeBigInteger_zeroAndOne_returnsFalse() {
    assertFalse(MathUtils.isPrimeBigInteger(0), "0 should not be prime");
    assertFalse(MathUtils.isPrimeBigInteger(1), "1 should not be prime");
  }

  @Test
  void isPrimeBigInteger_smallPrimes_returnsTrue() {
    int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    for (int prime : primes) {
      assertTrue(MathUtils.isPrimeBigInteger(prime), prime + " should be prime");
    }
  }

  @Test
  void isPrimeBigInteger_smallNonPrimes_returnsFalse() {
    int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20};
    for (int nonPrime : nonPrimes) {
      assertFalse(MathUtils.isPrimeBigInteger(nonPrime), nonPrime + " should not be prime");
    }
  }

  @Test
  void isPrimeBigInteger_largePrimes_returnsTrue() {
    int[] largePrimes = {101, 103, 107, 109, 113, 127, 131, 137, 139};
    for (int prime : largePrimes) {
      assertTrue(MathUtils.isPrimeBigInteger(prime), prime + " should be prime");
    }
  }

  @Test
  void isPrimeBigInteger_largeNonPrimes_returnsFalse() {
    int[] largeNonPrimes = {100, 102, 104, 105, 110, 111, 112, 114, 115};
    for (int nonPrime : largeNonPrimes) {
      assertFalse(MathUtils.isPrimeBigInteger(nonPrime), nonPrime + " should not be prime");
    }
  }

  @Test
  void isPrimeBigInteger_edgeCases_returnsCorrectResult() {
    assertFalse(MathUtils.isPrimeBigInteger(Integer.MIN_VALUE), "Integer.MIN_VALUE should not be prime");
    assertFalse(MathUtils.isPrimeBigInteger(Integer.MAX_VALUE - 1), (Integer.MAX_VALUE - 1) + " should not be prime");
    assertTrue(MathUtils.isPrimeBigInteger(Integer.MAX_VALUE), Integer.MAX_VALUE + " should be prime");
  }

  @Test
  void isPrimeBigInteger_carmichaelNumbers_returnsFalse() {
    int[] carmichaelNumbers = {561, 1105, 1729, 2465, 2821, 6601};
    for (int number : carmichaelNumbers) {
      assertFalse(MathUtils.isPrimeBigInteger(number), number + " should not be prime");
    }
  }

  @Test
  void isPrimeBigInteger_randomNumbers_returnsCorrectResult() {
    int[] primes = {199, 211, 223, 227, 229, 233, 239, 241};
    for (int prime : primes) {
      assertTrue(MathUtils.isPrimeBigInteger(prime), prime + " should be prime");
    }

    int[] nonPrimes = {200, 210, 220, 225, 230, 235, 240, 245};
    for (int nonPrime : nonPrimes) {
      assertFalse(MathUtils.isPrimeBigInteger(nonPrime), nonPrime + " should not be prime");
    }
  }

  @Test
  void isPrimeBigInteger_largeNumbers_returnsCorrectResult() {
    // Note: These are within the int range
    int prime = 2147483647; // Integer.MAX_VALUE, which is a prime number
    assertTrue(MathUtils.isPrimeBigInteger(prime), prime + " should be prime");

    int nonPrime = 2147483646;
    assertFalse(MathUtils.isPrimeBigInteger(nonPrime), nonPrime + " should not be prime");
  }
}

