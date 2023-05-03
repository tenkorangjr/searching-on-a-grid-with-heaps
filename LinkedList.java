/*
 * Name: Michael Tenkorang
 * Class Purpose: Searching a grid with heaps
 */

import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class LinkedList<T> implements Queue<T>, Stack<T> {

    private class LLIterator implements Iterator<T> {
        Node<T> currNode;

        public LLIterator(Node<T> head) {
            currNode = head;
        }

        public boolean hasNext() {
            return currNode != null;
        }

        public T next() {
            T item = currNode.getData();
            currNode = currNode.getNext();

            return item;

        }

        public void remove() {
            return;
        }
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;

        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> newNext) {
            next = newNext;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Add to the first of the linkedlist
     * 
     * @param data
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<T>(data, head, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Get item at an index in the linkedlist
     * 
     * @param index
     * @return
     */
    public T get(int index) {
        Node<T> walker = head;

        for (int i = 0; i < index; i++) {
            walker = walker.getNext();
        }

        return walker.getData();
    }

    /**
     * Add an item at an index in the linkedlist
     * 
     * @param index
     * @param item
     */
    public void addAtIndex(int index, T item) {
        if (index == 0) {
            addFirst(item);
            return;
        }

        Node<T> walker = head;
        for (int i = 0; i < index - 1; i++) {
            walker = walker.getNext();
        }

        Node<T> newNode = new Node<T>(item, walker.getNext(), null);
        walker.setNext(newNode);
        size++;
    }

    /**
     * Add an item to the last of a linkedlist
     * 
     * @param data
     */
    public void addLast(T data) {

        Node<T> newNode = new Node<>(data, null, tail);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;

    }

    /**
     * Remove the first item from a linkedlist
     * 
     * @return
     */
    public T removeFirst() {

        T removedItem = head.getData();

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrev(null);
        }

        size--;

        return removedItem;

    }

    /**
     * Remove item at index
     * 
     * @param index
     * @return
     */
    public T remove(int index) {
        Node<T> walker = head;

        if (index == 0) {
            removeFirst();
            return walker.getData();
        }
        for (int i = 0; i < index - 1; i++) {
            walker = walker.getNext();
        }

        Node<T> removedNode = walker.getNext();
        walker.setNext(removedNode.getNext());

        size--;

        return removedNode.getData();
    }

    /**
     * Remove the last item from a linkedlist
     * 
     * @return
     */
    public T removeLast() {

        T removedItem = tail.getData();

        if (head == tail) {

            head = null;
            tail = null;

        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }

        size--;
        return removedItem;
    }

    /**
     * Return the size of the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Check if the linkedlist is empty
     * 
     * @return
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reset the linkedlist
     */
    public void clear() {
        size = 0;
        head = null;
    }

    /**
     * Check if a linkedlist contains an object
     * 
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        Node<T> walker = head;

        for (int i = 0; i < size; i++) {
            T item = walker.getData();

            if (item.equals(o)) {
                return true;
            }

            walker = walker.getNext();
        }

        return false;
    }

    /**
     * Equals method for the linkedlist
     */
    public boolean equals(Object o) {

        if (!(o instanceof LinkedList)) {
            return false;
        }
        LinkedList<T> oAsALinkedList = (LinkedList<T>) o;

        Node<T> walker = head;
        Node<T> otherWalker = oAsALinkedList.head;

        if (!(size == oAsALinkedList.size)) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            T item = walker.getData();

            if (item.equals(otherWalker.getData())) {
                walker = walker.getNext();
                otherWalker = otherWalker.getNext();
                continue;
            } else {
                return false;
            }

        }

        return true;
    }

    /**
     * Offer method for the linkedlist
     */
    @Override
    public void offer(T item) {
        addLast(item);
    }

    /**
     * Get the first item in the linked list
     */
    @Override
    public T peek() {
        return get(0);
    }

    /**
     * Poll method for the queue
     */
    @Override
    public T poll() {
        return removeFirst();
    }

    /**
     * Remove last added and delete from linkedlist
     */
    @Override
    public T pop() {
        T poppedItem = removeFirst();
        return poppedItem;
    }

    /**
     * Push method for the stack
     */
    @Override
    public void push(T item) {
        addFirst(item);
    }

    /**
     * Iterator for the linkedlist
     */
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    /**
     * Convert linkedlist to array list
     * 
     * @return
     */
    public ArrayList<T> toArrayList() {
        ArrayList<T> arr = new ArrayList<>();
        Node<T> currentNode = head;
        while (currentNode != null) {
            arr.add(currentNode.getData());
            currentNode = currentNode.getNext();
        }
        return arr;
    }

    /**
     * String representation
     */
    public String toString() {
        if (head == null) {
            return "[]";
        }
        String result = "[" + head.data;
        Node<T> current = head.next;
        while (current != null) {
            result += ", " + current.data;
            current = current.next;
        }
        result += "]";
        return result;
    }

}