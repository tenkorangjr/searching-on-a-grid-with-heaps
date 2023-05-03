/*
 * Name: Michael Tenkorang
 * Class Purpose: Searching a grid with heaps
 */

public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    private Queue<Cell> queue;

    public MazeBreadthFirstSearch(Maze maze) {
        super(maze);
        this.queue = new LinkedList<>();
    }

    /**
     * Add cell to the queue
     */
    public void addCell(Cell next) {
        queue.offer(next);
    }

    /**
     * Find next cell to explore
     */
    public Cell findNextCell() {
        return queue.poll();
    }

    /**
     * Get the remaining number of cells to explore
     */
    public int numRemainingCells() {
        return queue.size();
    }

}
