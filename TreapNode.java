/*
* TreapNode with a parent pointer.
 */

import java.util.Random;

class TreapNode<T extends Comparable> {
    T key;
    int priority;

    // Three pointers to its left child, right child, and parent
    TreapNode<T> left;
    TreapNode<T> right;
    TreapNode<T> parent;

    TreapNode(){
        left = null;
        right = null;
        parent = null;
    }

    TreapNode(T val){
        key = val;
        // Initialize a new node with a large priority
        Random rand = new Random();
        priority = rand.nextInt();
        left = null;
        right = null;
        parent = null;
    }

    TreapNode(T value, int prio){
        key = value;
        priority = prio;
        left = null;
        right = null;
        parent = null;
    }

    boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    @Override
    public String toString() {
        return "(" + this.key + "," + this.priority + ")";
    }
}