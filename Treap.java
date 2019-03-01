import java.util.Random;

/*
 * I realized that there is much more work to do because of the parent pointer.
 * Supported operations:
 *  - insert(key)
 *  - delete(key)
 *  - search(key)
 *  - join(Treap1, Treap2) for numbers
 *  TODO:
 *  - split(key)
 *
 */

public class Treap<T extends Comparable> {

    TreapNode<T> root;

    public Treap(){
        root = null;
    }

    private void fixUp(TreapNode<T> cur) {
        while ( cur.parent != null && cur.parent.priority > cur.priority) {
            TreapNode<T> p = cur.parent;
            if (cur == p.right) { // It is the right child
                p.right = cur.left;
                if (cur.left != null) {cur.left.parent = p;}
                if (p.parent != null) { p.parent.right = cur ; }
                cur.left = p;
                cur.parent = p.parent;
                p.parent = cur;
            } else { // It is the left child
                p.left = cur.right;
                if (cur.right != null) {cur.right.parent = p;}
                if (p.parent != null) {p.parent.left = cur;}
                cur.right = p;
                cur.parent = p.parent;
                p.parent = cur;
            }
        }
    }

    private void fixDown(TreapNode<T> cur) {
        TreapNode<T> temp;
        while (!cur.isLeaf()) {
            temp = findMinPrio(cur);
            if (cur.priority > temp.priority){
                if (temp == cur.right) {
                    fixUp(cur.right);
                } else {
                    fixUp(cur.left);
                }
            }
        }
    }

    // A helper method to find the child with the minimum priority.
    private TreapNode<T> findMinPrio (TreapNode<T> cur) {
        TreapNode<T> temp;
        if (cur.right != null && cur.left != null) {
            if (cur.right.priority < cur.left.priority) {
                temp = cur.right;
            } else {
                temp = cur.left;
            }
        } else if (cur.left == null) {
            temp = cur.right;
        } else {
            temp = cur.left;
        }
        return temp;
    }

    // Returns a pointer to the node with key = val
    TreapNode<T> search (T val) {
        TreapNode<T> cur = this.root;
        while (cur != null) {
            if (val.compareTo(cur.key) == 0) {
                return cur;
            }
            if (val.compareTo(cur.key) > 0) { // val > key, go to right subtree
                cur = cur.right;
            } else { // val < key
                cur = cur.left;
            }
        }
        return null;
    }

    TreapNode<T> findMax () {
        TreapNode temp = root;
        while (!temp.isLeaf()) {
            temp = temp.right;
        }
        return temp;
    }

    TreapNode<T> findMin () {
        TreapNode temp = root;
        while (!temp.isLeaf()) {
            temp = temp.left;
        }
        return temp;
    }
    // Insert a new key into this tree.
    private TreapNode<T> insertRec (TreapNode<T> rNode, T val) {
        if (rNode == null) {
            return new TreapNode<T>(val);
        } else {
            if (val.compareTo(rNode.key) > 0) {
                rNode.right = insertRec(rNode.right, val);
                rNode.right.parent = rNode;
                if (rNode.right.priority < rNode.priority) {
                    fixUp(rNode.right);
                }
                if (rNode == root) {
                    return rNode.parent;
                }
            } else { // val < rNode.key
                rNode.left = insertRec(rNode.left, val);
                rNode.parent = rNode;
                if (rNode.left.priority < rNode.priority) {
                    fixUp(rNode.left);
                }
                if (rNode == root) {
                    return rNode.parent;
                }
            }
            return rNode;
        }
    }

    void insert (T val) {
        this.root = insertRec(root, val);
    }

    /* Suppose all keys in t1 are less than those in t2.
    *  Create a node, k, with MAX(t1) < k.key < MAX(t2) and k.priority = MIN_INT.
    *  Let k.left = t1, k.right = t2. Then delete k, and the tree will fix itself.
    *  But I didn't figure out how to generate a random object within the range besides numbers and characters.

    Treap<T> join (Treap<T> t1, Treap<T> t2) {
        Treap<T> result = new Treap<>();
        T max = t1.findMax().key;
        T min = t2.findMin().key;
        Random rand = new Random();
        T k = min + rand.nextInt(max - min+ 1); // Problem here
        TreapNode<T> temp = new TreapNode(k, Integer.MIN_VALUE);
        result.root = temp;
        temp.left = t1.root;
        temp.right = t2.root;
        temp.priority = Integer.MAX_VALUE;
        result.delete(temp.key);
        return result;
    }
    */



    /*
    * Delete the key = val in the tree.
    * If it is a leaf, just remove it.
    * Else, make it a leaf.
    * First search for the key, then change the priority to MAX_INT and rotate it down to the bottom.
    * Remove it as a leaf.
    */

    // A helper method to delete a leaf
    private void deleteNode (TreapNode<T> cur){
        if (cur.parent.right == cur) {
            cur.parent.right = null;
        } else {
            cur.parent.left = null;
        }
    }

    // Return the deleted node.

    TreapNode<T> delete (T val) {
        TreapNode<T> result = search(val);
        if (result == root) { root = findMinPrio(root); }
        if (result != null) {
            if (result.isLeaf()) {
                deleteNode(result);
            } else {
                result.priority = Integer.MAX_VALUE;
                fixDown(result);
                deleteNode(result);
            }
        }
        return result;
    }

    /*
    private TreapNode<T> insertNodeRec (TreapNode<T> rNode, TreapNode<T> node) {
        if (rNode == null) {
            return node;
        } else {
            if (node.key.compareTo(rNode.key) > 0) {
                rNode.right = insertNodeRec(rNode.right, node);
                rNode.right.parent = rNode;
                if (rNode.right.priority < rNode.priority) {
                    fixUp(rNode.right);
                    if (rNode == root) {
                        return rNode.parent;
                    }
                }
            } else { // node.key < rNode.key
                rNode.left = insertNodeRec(rNode.left, node);
                rNode.left.parent = rNode;
                if (rNode.left.priority < rNode.priority) {
                    fixUp(rNode.left);
                    if (rNode == root) {
                        return rNode.parent;
                    }
                }
            }
            return rNode;
        }
    }

    void insertNode (TreapNode<T> node) {
        this.root = insertNodeRec(root, node);
    }
    */
}

