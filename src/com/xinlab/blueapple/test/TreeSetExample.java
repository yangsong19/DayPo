package com.xinlab.blueapple.test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetExample {

    /*
     * It is generally faster to add elements to the HashSet and then convert
     * the collection to a TreeSet for a duplicate-free sorted Traversal.
     * 
     * really? O(Hash + tree set) > O(tree set) ?? Really???? Why?
     */

    public static void main(String args[]) {

        int size = 80000;
        useHashThenTreeSet(size);
        useTreeSetOnly(size);
        
        TreeSet<String> ts =new TreeSet<String>();
        ts.add(null); //throw null pointer exception

        HashSet<String> hs =new HashSet<String>();
        hs.add(null) ;//runs fine

    }

    /**
     * @param size
     */
    private static void useTreeSetOnly(int size) {

        System.out.println("useTreeSetOnly: ");
        long start = System.currentTimeMillis();
        Set<String> sortedSet = new TreeSet<String>();

        for (int i = 0; i < size; i++) {
            sortedSet.add(i + "");
        }

        // System.out.println(sortedSet);
        long end = System.currentTimeMillis();

        System.out.println("useTreeSetOnly: " + (end - start));
    }

    private static void useHashThenTreeSet(int size) {

        System.out.println("useHashThenTreeSet: ");
        long start = System.currentTimeMillis();
        Set<String> set = new HashSet<String>();

        for (int i = 0; i < size; i++) {
            set.add(i + "");
        }

        Set<String> sortedSet = new TreeSet<String>(set);
        // System.out.println(sortedSet);
        long end = System.currentTimeMillis();

        System.out.println("useHashThenTreeSet: " + (end - start));
    }
}