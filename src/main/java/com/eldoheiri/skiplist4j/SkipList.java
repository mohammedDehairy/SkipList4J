package com.eldoheiri.skiplist4j;

import java.util.Objects;

public final class SkipList<T extends Comparable<? super T>> {
    private static final int MAX_LEVEL = 16;
    private static final double P = 0.5;

    private int complexity = 0;

    private int currentMaxLevel = 1;
    private final SkipListNode<T> head = new SkipListNode<T>(null, MAX_LEVEL);

    private int size = 0;

    /**
     * Generates a random maximum level for a certain node. 
     * The probability for a node to occupy a certain level n is (0.5)^n. 
     * I.e the probability of a node to occupy level 0 is 100%,
     * and the probability it occupies level 1 is 50%,
     * and then the probablity it occupies level n is half the probability it occupies level n+1.
     * And thats by design to maintain access time complexity of O(log2(n)), where n is the number of elements in the list.
     * @return a random int between 1 and MAX_LEVEL exclusive.
     */
    private int randomLevel() {
        int level = 1;

        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public int getComplexity() {
        return complexity;
    }

    public boolean contains(T value) {
        Objects.requireNonNull(value);
        complexity = 0;
        SkipListNode<T> current = head;
        for (int i = currentMaxLevel - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].getValue().compareTo(value) < 0) {
                complexity++;
                current = current.next[i];
            }
        }
        current = current.next[0];
        return current != null && current.getValue().compareTo(value) == 0;
    }

    public void add(T value) {
        Objects.requireNonNull(value);
        complexity = 0;
        @SuppressWarnings("unchecked")
        SkipListNode<T>[] update = new SkipListNode[MAX_LEVEL];

        SkipListNode<T> current = head;

        for (int i = currentMaxLevel - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].getValue().compareTo(value) < 0) {
                complexity++;
                current = current.next[i];              
            }
            update[i] = current;
        }

        current = current.next[0];

        if (current != null && current.getValue() != null && current.getValue().compareTo(value) == 0) {
            return;
        }

        int newLevel = randomLevel();

        if (newLevel > currentMaxLevel) {
            for (int i = currentMaxLevel; i < newLevel; i++) {
                update[i] = head;
            }
            currentMaxLevel = newLevel;
        }
        SkipListNode<T> newNode = new SkipListNode<T>(value, newLevel);
        for (int i = 0; i < newLevel; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
        size++;
    }

    public void delete(T value) {
        Objects.requireNonNull(value);
        complexity = 0;
        @SuppressWarnings("unchecked")
        SkipListNode<T>[] update = new SkipListNode[MAX_LEVEL];
        SkipListNode<T> current = head;

        for (int i = currentMaxLevel - 1; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].getValue().compareTo(value) < 0) {
                complexity++;
                current = current.next[i];              
            }
            update[i] = current;
        }
        current = current.next[0];

        if (current == null || current.getValue().compareTo(value) != 0) {
            return;
        }

        for (int i = 0; i < currentMaxLevel; i++) {
            if (update[i].next[i] != current) {
                break;
            }
            update[i].next[i] = current.next[i];
        }
        size--;
        while(currentMaxLevel > 1 && head.next[currentMaxLevel - 1] == null) {
            currentMaxLevel--;
        }
    }

    public int size() {
        return size;
    }

    public void print() {
        System.out.println("-------------------");
        for(int i = currentMaxLevel - 1; i >= 0; i--) {
            SkipListNode<T> node = head.next[i];
            System.out.print("Level " + i + ": ");
            while(node != null) {
                System.out.print(node.getValue() + " ");
                node = node.next[i];
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }
}
