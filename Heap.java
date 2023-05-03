/*
 * Name: Michael Tenkorang
 * Class Purpose: Searching a grid with heaps
 */

import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class Heap<T> implements PriorityQueue<T> {
    private static class Node<T> {
        Node<T> left, right, parent;
        T data;

        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }

    private int size;
    private Node<T> root;
    private Node<T> last;
    Comparator<T> comparator;
    HashSet<Node<T>> nodeMap;

    public Heap() {
        this(null, false);
    }

    public Heap(boolean maxHeap) {
        this(null, maxHeap);
    }

    /**
     * Constructor
     * 
     * @param comparator
     * @param maxHeap
     */
    public Heap(Comparator<T> comparator, boolean maxHeap) {
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            this.comparator = new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable<T>) o1).compareTo(o2);
                }

            };
        }

        if (maxHeap) {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }

        size = 0;
        root = null;
        last = null;
    }

    /**
     * Add an item to the heap
     * 
     * @param item
     */
    @Override
    public void offer(T item) {
        if (size == 0) {
            root = new Node<>(item, null);
            last = root;
            size++;
            return;
        }

        if (size % 2 == 0) {
            last.parent.right = new Node<>(item, last.parent);
            last = last.parent.right;
        } else {
            Node<T> curNode = last;
            while (curNode != root && curNode == curNode.parent.right) {
                curNode = curNode.parent;
            }
            if (curNode != root) {
                curNode = curNode.parent.right;
            }
            while (curNode.left != null) {
                curNode = curNode.left;
            }

            curNode.left = new Node<>(item, curNode);
            last = curNode.left;

        }

        bubbleUp(last);
        size++;

    }

    /**
     * Swap the data of two nodes
     * 
     * @param node1
     * @param node2
     */
    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    /**
     * Bubble up with curNode and its parent
     * 
     * @param curNode
     */
    private void bubbleUp(Node<T> curNode) {
        if (curNode == root) {
            return;
        }

        T myself = curNode.data;
        T myParent = curNode.parent.data;

        if (comparator.compare(myself, myParent) < 0) {
            swap(curNode, curNode.parent);
            bubbleUp(curNode.parent);
        }
    }

    /**
     * Bubble down with the children of curNode
     * 
     * @param curNode
     */
    private void bubbleDown(Node<T> curNode) {
        if (curNode.left == null)
            return;
        else if (curNode.right == null) {
            if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        } else {
            if (comparator.compare(curNode.left.data, curNode.right.data) < 0) {
                if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            } else {
                if (comparator.compare(curNode.data, curNode.right.data) > 0) {
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    @Override
    /**
     * Get the size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Get the data in the root of the heap
     */
    @Override
    public T peek() {
        return root.data;
    }

    /**
     * Removes and returns the root of the heap
     */
    @Override
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T output;

        if (size == 1) {
            output = last.data;
            root = null;
            last = null;
            size--;
        } else {
            output = root.data;
            root.data = last.data; // setting root data to the last item's data and bubble down

            if (size % 2 == 0) {
                Node<T> curNode = last;
                while (curNode != root && curNode == curNode.parent.left) {
                    curNode = curNode.parent;
                }
                if (curNode != root) {
                    curNode = curNode.parent.left;
                }

                while (curNode.right != null) {
                    curNode = curNode.right;
                }

                if (last.parent.left == last) {
                    last.parent.left = null;
                }

                last = curNode;
                bubbleDown(root);
            } else {
                if (last.parent.right == last) {
                    last.parent.right = null;
                }
                last = last.parent.left;
                bubbleDown(root);
            }
            size--;
        }
        return output;
    }

    /**
     * Update the priority of an item
     */
    @Override
    public void updatePriority(T item) {
        Node<T> node = find(root, item);
        if (node == null) {
            return;
        }
        node.data = item;
        bubbleUp(node);
        bubbleDown(node);
    }

    /**
     * Returns the node with data equal to item
     * 
     * @param curNode
     * @param item
     * @return
     */
    private Node<T> find(Node<T> curNode, T item) {
        if (curNode == null) {
            return null;
        }
        if (curNode.data.equals(item)) {
            return curNode;
        }
        Node<T> leftResult = find(curNode.left, item);
        if (leftResult != null) {
            return leftResult;
        }
        Node<T> rightResult = find(curNode.right, item);
        if (rightResult != null) {
            return rightResult;
        }
        return null;
    }

}