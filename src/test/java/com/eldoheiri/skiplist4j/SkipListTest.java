package com.eldoheiri.skiplist4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SkipListTest {

    @Test
    void testAdditionAndDeletion() {
        SkipList<Integer> sut = new SkipList<>();
        HashSet<Integer> set = new HashSet<>();

        Random random = new Random();

        for (int i = 0; i < 1000_000; i++) {
            Integer newInteger = random.nextInt(1000_000);
            sut.add(newInteger);
            set.add(newInteger);
            assertEquals(sut.size(), set.size());
        }
        for (Integer i : set) {
            assertTrue(sut.contains(i));
            sut.delete(i);
            assertFalse(sut.contains(i));
        }
        assertEquals(sut.size(), 0);
    }

    @Test
    void testTimeComplexity() {
        SkipList<Integer> sut = new SkipList<>();
        final int iterations = 1000_000;
        Random random = new Random();
        Set<Integer> allIntegers = new HashSet<>();
        assertAddtionAndContainsTimeComplexity(iterations, random, sut, allIntegers);
        Assertions.assertEquals(allIntegers.size(), sut.size());
        assertDeletionTimeComplexity(iterations, random, sut, allIntegers);
    }

    private void assertAddtionAndContainsTimeComplexity(int iterations, Random random, SkipList<Integer> sut, Set<Integer> allIntegers) {
        List<Double> constants = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            Integer newInteger = random.nextInt(iterations);

            // Addition Complexity
            sut.add(newInteger);
            final int additionComplexity = sut.getComplexity();
            final double expectedComplexity = Math.log(i + 1) / Math.log(2);
            final double constantFactorForAddition = (double) additionComplexity / (double) expectedComplexity;
            if (!Double.isNaN(constantFactorForAddition)) {
                constants.add(constantFactorForAddition);
            }

            allIntegers.add(newInteger);

            // Membership Check Complexity
            Assertions.assertTrue(sut.contains(newInteger));
            final int containsComplexity = sut.getComplexity();
            final double constantFactorForContains = (double) containsComplexity / (double) expectedComplexity;
            if (!Double.isNaN(constantFactorForContains)) {
                constants.add(constantFactorForContains);
            }
        }
        double standardDeviation = standardDeviation(constants);
        System.out.println("standard deviation: " + standardDeviation + "");
        Assertions.assertTrue(standardDeviation < 1.0);
    }

    private void assertDeletionTimeComplexity(int iterations, Random random, SkipList<Integer> sut, Set<Integer> allNumbers) {
        List<Double> constants = new ArrayList<>();
        for (Integer integer: allNumbers) {
            final double expectedComplexity = Math.log(sut.size()) / Math.log(2);
            // Deletion Complexity
            sut.delete(integer);
            final int deletionComplexity = sut.getComplexity();
            final double constantFactorForDeletion = (double) deletionComplexity / (double) expectedComplexity;
            if (!Double.isNaN(constantFactorForDeletion)) {
                constants.add(constantFactorForDeletion);
            }
        }
        double standardDeviation = standardDeviation(constants);
        System.out.println("standard deviation: " + standardDeviation + "");
        Assertions.assertTrue(standardDeviation < 1.0);
    }

    private static double standardDeviation(List<Double> values) {
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double sumOfSquaredError = values.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum();
        double variance = sumOfSquaredError / ((double)values.size() - (double)1);
        return Math.sqrt(variance);
    }
}
