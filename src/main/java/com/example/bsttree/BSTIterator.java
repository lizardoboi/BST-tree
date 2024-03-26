package com.example.bsttree;

import java.util.Iterator;
import java.util.Stack;

public class BSTIterator<K extends Comparable<K>, V> implements Iterator<K> {
    private Stack<Node<K, V>> stack;

    public BSTIterator(Node<K, V> root, boolean forward) {
        stack = new Stack<>();
        Node<K, V> current = root;
        while (current != null) {
            stack.push(current);
            current = forward ? current.left : current.right;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public K next() {
        Node<K, V> node = stack.pop();
        K key = node.key;
        Node<K, V> current = node.right;
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        return key;
    }
}
