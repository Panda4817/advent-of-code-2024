package dev.kmunton.utils.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;

class RangeTest {

  private Range range1;
  private Range range2;
  private Range range3;
  private Range range4;

  @BeforeEach
  void setUp() {
    range1 = new Range(5, 15);
    range2 = new Range(10, 20);
    range3 = new Range(16, 25);
    range4 = new Range(17, 25);
  }

  @Test
  void constructor_validRange_createsRange() {
    Range range = new Range(10, 20);
    assertEquals(10, range.min());
    assertEquals(20, range.max());
  }

  @Test
  void constructor_minGreaterThanMax_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> new Range(20, 10));
  }

  @Test
  void contains_valueWithinRange_returnsTrue() {
    assertTrue(range1.contains(10));
  }

  @Test
  void contains_valueOutsideRange_returnsFalse() {
    assertFalse(range1.contains(20));
  }

  @Test
  void overlaps_overlappingRanges_returnsTrue() {
    assertTrue(range1.overlaps(range2));
  }

  @Test
  void overlaps_nonOverlappingRanges_returnsFalse() {
    assertFalse(range1.overlaps(range3));
  }

  @Test
  void isEmpty_emptyRange_returnsTrue() {
    Range emptyRange = new Range(10, 10);
    assertTrue(emptyRange.isEmpty());
  }

  @Test
  void isEmpty_nonEmptyRange_returnsFalse() {
    assertFalse(range1.isEmpty());
  }

  @Test
  void length_validRange_returnsCorrectLength() {
    assertEquals(10, range1.length());
  }

  @Test
  void length_emptyRange_returnsZero() {
    Range emptyRange = new Range(10, 10);
    assertEquals(0, emptyRange.length());
  }

  @Test
  void intersect_overlappingRanges_returnsIntersection() {
    Optional<Range> intersection = range1.intersect(range2);
    assertTrue(intersection.isPresent());
    assertEquals(new Range(10, 15), intersection.get());
  }

  @Test
  void intersect_nonOverlappingRanges_returnsEmptyOptional() {
    Optional<Range> intersection = range1.intersect(range3);
    assertFalse(intersection.isPresent());
  }

  @Test
  void union_anyRanges_returnsUnionRange() {
    Range unionRange = range1.union(range2);
    assertEquals(new Range(5, 20), unionRange);
  }

  @Test
  void shift_positiveAmount_shiftsRange() {
    Range shiftedRange = range1.shift(5);
    assertEquals(new Range(10, 20), shiftedRange);
  }

  @Test
  void shift_negativeAmount_shiftsRange() {
    Range shiftedRange = range1.shift(-5);
    assertEquals(new Range(0, 10), shiftedRange);
  }

  @Test
  void expand_positiveAmount_expandsRange() {
    Range expandedRange = range1.expand(2);
    assertEquals(new Range(3, 17), expandedRange);
  }

  @Test
  void expand_negativeAmount_contractsRange() {
    Range contractedRange = range1.expand(-2);
    assertEquals(new Range(7, 13), contractedRange);
  }

  @Test
  void expand_amountExceedsRange_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> range1.expand(-10));
  }

  @Test
  void clamp_valueWithinRange_returnsValue() {
    long clampedValue = range1.clamp(10);
    assertEquals(10, clampedValue);
  }

  @Test
  void clamp_valueBelowRange_returnsMin() {
    long clampedValue = range1.clamp(0);
    assertEquals(5, clampedValue);
  }

  @Test
  void clamp_valueAboveRange_returnsMax() {
    long clampedValue = range1.clamp(20);
    assertEquals(15, clampedValue);
  }

  @Test
  void isSubRangeOf_subRange_returnsTrue() {
    Range subRange = new Range(7, 13);
    assertTrue(subRange.isSubRangeOf(range1));
  }

  @Test
  void isSubRangeOf_notSubRange_returnsFalse() {
    assertFalse(range1.isSubRangeOf(range2));
  }

  @Test
  void compareTo_smallerRange_returnsNegative() {
    int comparison = range1.compareTo(range2);
    assertTrue(comparison < 0);
  }

  @Test
  void compareTo_equalRanges_returnsZero() {
    Range identicalRange = new Range(5, 15);
    int comparison = range1.compareTo(identicalRange);
    assertEquals(0, comparison);
  }

  @Test
  void compareTo_largerRange_returnsPositive() {
    int comparison = range2.compareTo(range1);
    assertTrue(comparison > 0);
  }

  @Test
  void split_pointWithinRange_returnsTwoRanges() {
    List<Range> splits = range1.split(10);
    assertEquals(2, splits.size());
    assertEquals(new Range(5, 10), splits.get(0));
    assertEquals(new Range(10, 15), splits.get(1));
  }

  @Test
  void split_pointAtBoundary_returnsOriginalRange() {
    List<Range> splits = range1.split(5);
    assertEquals(1, splits.size());
    assertEquals(range1, splits.getFirst());
  }

  @Test
  void merge_overlappingRanges_returnsMergedRange() {
    Optional<Range> mergedRange = range1.merge(range2);
    assertTrue(mergedRange.isPresent());
    assertEquals(new Range(5, 20), mergedRange.get());
  }

  @Test
  void merge_adjacentRanges_returnsMergedRange() {
    Optional<Range> mergedRange = range1.merge(range3);
    assertTrue(mergedRange.isPresent());
    assertEquals(new Range(5, 25), mergedRange.get());
  }

  @Test
  void merge_nonAdjacentRanges_returnsMergedRange() {
    Optional<Range> mergedRange = range1.merge(range4);
    assertFalse(mergedRange.isPresent());
  }

  @Test
  void gap_nonOverlappingRanges_returnsGapRange() {
    Range nonOverlappingRange = new Range(20, 25);
    Optional<Range> gapRange = range1.gap(nonOverlappingRange);
    assertTrue(gapRange.isPresent());
    assertEquals(new Range(15, 20), gapRange.get());
  }

  @Test
  void gap_overlappingRanges_returnsEmptyOptional() {
    Optional<Range> gapRange = range1.gap(range2);
    assertFalse(gapRange.isPresent());
  }

  @Test
  void midpoint_validRange_returnsCorrectMidpoint() {
    double midpoint = range1.midpoint();
    assertEquals(10.0, midpoint);
  }

  @Test
  void scale_positiveFactor_scalesRange() {
    Range scaledRange = range1.scale(2.0);
    assertEquals(new Range(0, 20), scaledRange);
  }

  @Test
  void scale_factorOne_returnsSameRange() {
    Range scaledRange = range1.scale(1.0);
    assertEquals(range1, scaledRange);
  }

  @Test
  void format_validPattern_returnsFormattedString() {
    String formatted = range1.format("Range starts at %d and ends at %d");
    assertEquals("Range starts at 5 and ends at 15", formatted);
  }

  @Test
  void toString_returnsCorrectRepresentation() {
    assertEquals("Range[5, 15]", range1.toString());
  }

  @Test
  void equals_sameRange_returnsTrue() {
    Range identicalRange = new Range(5, 15);
    assertEquals(range1, identicalRange);
  }

  @Test
  void equals_differentRange_returnsFalse() {
    assertNotEquals(range1, range2);
  }

  @Test
  void hashCode_sameRange_sameHashCode() {
    Range identicalRange = new Range(5, 15);
    assertEquals(range1.hashCode(), identicalRange.hashCode());
  }
}

