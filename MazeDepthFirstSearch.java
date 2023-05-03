/*
 * Name: Michael Tenkorang
 * Class Purpose: Searching a grid with heaps
 */

public class MazeDepthFirstSearch extends AbstractMazeSearch {

    private Stack<Cell> stack;

    public MazeDepthFirstSearch(Maze maze) {
        super(maze);
        this.stack = new LinkedList<>();
    }

    /**
     * Add cell to the stack
     */
    public void addCell(Cell next) {
        stack.push(next);
    }

    /**
     * Find Next Cell to explore
     */
    public Cell findNextCell() {
        return stack.pop();
    }

    /**
     * Get the total number of remaining cells
     */
    public int numRemainingCells() {
        return stack.size();
    }

}