import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String str;
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rQueue.enqueue(str);
        }
        
        for (int i = 0; i < k; i++)
            System.out.println(rQueue.dequeue());
    }
}
