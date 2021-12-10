package com.example.realestatesearchapp.tree;

import java.util.List;

public interface SearchTree<K extends Comparable<K>,V> {
    void insert(K key, V val) throws NullKeyException;
    V delete(K key) throws NullKeyException;
    V search(K key) throws NullKeyException;
    List<V> largerThan(K key) throws NullKeyException;
    List<V> lessThan(K key) throws NullKeyException;
    List<V> between(K keyL, K keyR) throws NullKeyException;
}
