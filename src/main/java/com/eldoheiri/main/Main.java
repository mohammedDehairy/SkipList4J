package com.eldoheiri.main;

import com.eldoheiri.skiplist4j.SkipList;

public class Main {
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();

        skipList.add(3);
        skipList.add(6);
        skipList.add(7);
        skipList.add(9);
        skipList.add(12);
        skipList.add(19);

        System.out.println("Skip List after insertions:");
        skipList.print();

        System.out.println("\nSearch for 7: " + skipList.contains(7));
        System.out.println("Search for 15: " + skipList.contains(15));

        skipList.delete(6);
        System.out.println("\nSkip List after deletion of 6:");
        skipList.print();
    }
}
