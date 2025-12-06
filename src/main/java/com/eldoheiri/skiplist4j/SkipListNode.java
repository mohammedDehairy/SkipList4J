package com.eldoheiri.skiplist4j;

final class SkipListNode<T extends Comparable<? super T>> {
    private final T value;
    /**
     * Since the same node instance can occupy multiple levels simultaniously, 
     * it has to carry its next node pointer for every level it occupy.
     * Thats why it has an array of node references. 
     * A node occupy all levels starting at a certain level all the way to the bottom level.
     */
    SkipListNode<T>[] next;

    /**
     * Initializes an node instance, given its value and the maximum level it occupies.
     * Since the same node instance can occupy multiple levels simultaniously, 
     * it has to carry its next node pointer for every level it occupy.
     * Thats why it has an array of node references. 
     * A node occupy all levels starting at a certain level all the way to the bottom level.
     * @param value The value that this node holds.
     * @param level The maximum level the node occupy.
     */
    @SuppressWarnings("unchecked")
    SkipListNode(T value, int level) {
        this.value = value;
        this.next = new SkipListNode[level];
    }

    /**
     * Returns the value this node holds
     * @return
     */
    T getValue() {
        return value;
    }
}
