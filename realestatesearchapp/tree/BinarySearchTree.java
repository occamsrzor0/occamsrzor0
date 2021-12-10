package com.example.realestatesearchapp.tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, V> implements SearchTree<K, V> {

    private K key;
    private V val;
    private BinarySearchTree<K, V> left, right, parent;

    public BinarySearchTree(K key, V val, BinarySearchTree parent) {
        this.key = key;
        this.val = val;
        this.parent = parent;
    }

    public BinarySearchTree() {
        this.key = null;
        this.val = null;
        this.parent = null;
    }


    private void checkKey(K key) throws NullKeyException {
        if (key == null) {
            throw new NullKeyException();
        }
    }

    @Override
    public void insert(K key, V val) throws NullKeyException {
        checkKey(key);
        if (this.key == null) {
            this.key = key;
            this.val = val;
        } else {
            if (key.compareTo(this.key) == 0) {
                this.val = val;
            } else if (key.compareTo(this.key) > 0) {
                if (this.right != null) {
                    this.right.insert(key, val);
                } else {
                    this.right = new BinarySearchTree(key, val, this);
                }
            } else {
                if (this.left != null) {
                    this.left.insert(key, val);
                } else {
                    this.left = new BinarySearchTree(key, val, this);
                }
            }
        }
    }

    private BinarySearchTree<K, V> minimal() {
        if (this.left != null) {
            return this.left.minimal();
        }
        return this;
    }

    private void updateRightParent() {
        if (this.right != null)
            this.right.parent = this;
    }

    private void updateLeftParent() {
        if (this.left != null)
            this.left.parent = this;
    }

    private void replaceSubtree(BinarySearchTree fromChild, BinarySearchTree replacement) {
        if (fromChild.equals(this.left)) {
            this.left = replacement;
            updateLeftParent();
        } else {
            this.right = replacement;
            updateRightParent();
        }
    }

    @Override
    public V delete(K key) throws NullKeyException {
        checkKey(key);
        if (key.compareTo(this.key) == 0) {
            if (this.right == null) {
                this.parent.replaceSubtree(this, this.left);
            } else if (this.left == null) {
                this.parent.replaceSubtree(this, this.right);
            } else {
                BinarySearchTree<K, V> min = this.right.minimal();
                this.key = min.key;
                this.val = min.val;
                this.right.delete(min.key);
            }
            return val;
        } else if (key.compareTo(this.key) > 0) {
            if (this.right == null) {
                return null;
            } else {
                return this.right.delete(key);
            }
        } else {
            if (this.left == null) {
                return null;
            } else {
                return this.left.delete(key);
            }
        }
    }

    @Override
    public V search(K key) throws NullKeyException {
        checkKey(key);
        if(this.key==null){
            return null;
        }
        if (key.compareTo(this.key) == 0) {
            return val;
        } else if (key.compareTo(this.key) > 0) {
            if (this.right == null) {
                return null;
            } else {
                return this.right.search(key);
            }
        } else {
            if (this.left == null) {
                return null;
            } else {
                return this.left.search(key);
            }
        }
    }

    private void largerThan(K key, List<V> list) {
        if (this.key.compareTo(key) > 0) {
            list.add(this.val);
            if (this.right != null) {
                this.right.largerThan(key, list);
            }
            if (this.left != null) {
                this.left.largerThan(key, list);
            }
        } else {
            if (this.right != null) {
                this.right.largerThan(key, list);
            }
        }
    }

    private void lessThan(K key, List<V> list) {
        if (this.key.compareTo(key) < 0) {
            list.add(this.val);
            if (this.right != null) {
                this.right.lessThan(key, list);
            }
            if (this.left != null) {
                this.left.lessThan(key, list);
            }
        } else {
            if (this.left != null) {
                this.left.lessThan(key, list);
            }
        }
    }

    private void between(K keyL, K keyR, List<V> list) {
        if (this.key.compareTo(keyR) >= 0) {
            if (this.left != null)
                this.left.between(keyL, keyR, list);
        } else if (this.key.compareTo(keyL) <= 0) {
            if (this.right != null)
                this.right.between(keyL, keyR, list);
        } else {
            list.add(val);
            if (this.left != null)
                this.left.between(keyL, keyR, list);
            if (this.right != null)
                this.right.between(keyL, keyR, list);
        }
    }

    @Override
    public List<V> largerThan(K key) throws NullKeyException {
        checkKey(key);
        List<V> list = new ArrayList<>();
        this.largerThan(key, list);
        return list;
    }

    @Override
    public List<V> lessThan(K key) throws NullKeyException {
        checkKey(key);
        List<V> list = new ArrayList<>();
        this.lessThan(key, list);
        return list;
    }

    @Override
    public List<V> between(K keyL, K keyR) throws NullKeyException {
        checkKey(key);
        List<V> list = new ArrayList<>();
        this.between(keyL, keyR, list);
        return list;
    }
}
