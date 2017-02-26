import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private class Move implements Comparable<Move> {
        private Move previous;
        private Board board;
        private int priority;
        private int movesCount;

        public Move(Board board) {
            this.board = board;
            this.movesCount = 0;
            this.priority = board.manhattan();
        }

        public Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            if (this.previous == null) this.movesCount = 0;
            else this.movesCount = previous.movesCount + 1;
            this.priority = board.manhattan() + this.movesCount;
        }

        public int compareTo(Move move) {
            if (this.priority > move.priority) return 1;
            else if (this.priority < move.priority) return -1;
            else return 0;
        }
    }

    private Move lastMove;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();
        if (initial.isGoal()) {
            this.lastMove = new Move(initial);
        } else {
            MinPQ<Move> moves = new MinPQ<Move>(); 
            moves.insert(new Move(initial));
            
            MinPQ<Move> twinMoves = new MinPQ<Move>();
            twinMoves.insert(new Move(initial.twin()));
            
            while (true) {
                Move twinLastMove = step(twinMoves);
                if (twinLastMove.board.isGoal()) {
                    this.lastMove = null;
                    return;
                }
                
                this.lastMove = step(moves);
                if (lastMove.board.isGoal()) return;
                
            }    
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return lastMove != null;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return lastMove.movesCount;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        
        Stack<Board> solutionSteps = new Stack<Board>();
        Move currentMove = lastMove;
        do {            
            solutionSteps.push(currentMove.board);
            currentMove = currentMove.previous;
        } while (currentMove != null);
        
        return solutionSteps;
    }
    
    private Move step(MinPQ<Move> moves) {
        if (moves.isEmpty()) return null;
        Move bestMove = moves.delMin();
        for (Board newBoard : bestMove.board.neighbors())
            if (bestMove.previous == null || !newBoard.equals(bestMove.previous.board))
                moves.insert(new Move(newBoard, bestMove));
        return bestMove;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In("puzzle3x3-11.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
