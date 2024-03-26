package com.example.bsttree;

import java.util.Iterator;
import java.util.Stack;

public class BSTReverseIterator<K extends Comparable<K>, V> implements Iterator<K> {
    private Stack<Node<K, V>> stack;

    public BSTReverseIterator(Node<K, V> root, boolean forward) {
        stack = new Stack<>();
        Node<K, V> current = root;
        while (current != null) {
            stack.push(current);
            current = forward ? current.right : current.left;
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
        Node<K, V> current = node.left;
        while (current != null) {
            stack.push(current);
            current = current.right;
        }
        return key;
    }
}
