import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> queue;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = new Deque<Item>();
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    // return the number of items on the queue
    public int size() {
        return queue.size();
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (StdRandom.bernoulli()) queue.addFirst(item); 
        else queue.addLast(item);
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item;
        if (StdRandom.bernoulli()) item = queue.removeFirst();
        else item = queue.removeLast();
        return item;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item;
        if (StdRandom.bernoulli()) item = queue.removeFirst();
        else item = queue.removeLast();
        enqueue(item);
        return item;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private class Node {
            private Node next;
            private Item value;
        }
        private Node current;
        
        RandomIterator() {
            for (Item item : queue) {
                Node oldCurrent = current;
                current = new Node();
                current.value = item;
                current.next = oldCurrent;
            }
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
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();
        rQueue.enqueue(1);
        rQueue.enqueue(2);
        rQueue.enqueue(3);
        rQueue.enqueue(4);
        rQueue.enqueue(5);
        rQueue.enqueue(6);
        for (int i : rQueue)
            System.out.println(i);
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.dequeue());
    }

}
