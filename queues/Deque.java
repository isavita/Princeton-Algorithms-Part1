import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Node next;
        private Node previous;
        private Item value;
    }
    
    private Node head;
    private Node tail;
    private int size;
    
    // construct an empty deque
    public Deque() {
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node oldHead = head;
        head = new Node();
        head.value = item; 
        if (isEmpty()) {
            tail = head;
            head.next = null;
        } else {
            head.next = oldHead;
            oldHead.previous = head;
        }
        head.previous = null;
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node oldTail = tail;
        tail = new Node();
        tail.value = item;
        if (isEmpty()) {
            head = tail;
            tail.previous = null;
        } else {
            oldTail.next = tail;
            tail.previous = oldTail;            
        }
        tail.next = null;
        size++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = head.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        --size;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = tail.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }
        --size;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();  
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current;
        
        ListIterator() {
            current = head;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.value;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new  java.lang.UnsupportedOperationException();
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(5);
        d.addFirst(4);
        d.addLast(9);
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        System.out.println(d.isEmpty());
        System.out.println(d.removeLast());
        System.out.println(d.isEmpty());
    }
}
