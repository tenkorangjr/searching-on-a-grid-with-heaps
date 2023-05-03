/*
 * Name: Michael Tenkorang
 * Class Purpose: Searching a grid with heaps
 */

import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractMazeSearch {

    public Maze maze;
    protected Cell start;
    protected Cell target;
    private Cell cur;
    public MazeSearchDisplay mazedisplay;

    public AbstractMazeSearch(Maze maze) {
        this.maze = maze;
        this.cur = null;
        this.start = null;
        this.target = null;
    }

    /**
     * Returns the next cell to explore
     * 
     * @return
     */
    public abstract Cell findNextCell();

    /**
     * Adds the given Cell to whatever structure is storing the future Cells to
     * explore.
     * 
     * @param next
     */
    public abstract void addCell(Cell next);

    /**
     * Return the number of future cells to explore
     * 
     * @return
     */
    public abstract int numRemainingCells();

    /**
     * Returns the maze
     * 
     * @return
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Get the target of the search
     * 
     * @return
     */
    public Cell getTarget() {
        return target;
    }

    /**
     * Set the target for the search
     * 
     * @param target
     */
    public void setTarget(Cell target) {
        this.target = target;
    }

    /**
     * Set the given cell to be the current location of the search
     * 
     * @param cell
     */
    public void setCur(Cell cell) {
        this.cur = cell;
    }

    /**
     * Get the current cell location of the search
     * 
     * @return
     */
    public Cell getCur() {
        return cur;
    }

    /**
     * Set the starting position of the search
     * 
     * @param start
     */
    public void setStart(Cell start) {
        this.start = start;
        start.setPrev(start);
    }

    /**
     * Get the start of the search
     * 
     * @return
     */
    public Cell getStart() {
        return start;
    }

    /**
     * Resets the search
     */
    public void reset() {
        this.cur = null;
        this.start = null;
        this.target = null;
    }

    /**
     * Finds cell from the start to a specified cell
     * 
     * @param cell
     * @return
     */
    public LinkedList<Cell> traceback(Cell cell) {
        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<>();

        while (curCell != null) {
            path.addFirst(curCell);
            if (curCell.getRow() == start.getRow() && curCell.getCol() == start.getCol()) {
                return path;
            }
            curCell = curCell.getPrev();
        }
        return null;
    }

    /**
     * Returns the path the search algorithm finds
     * 
     * @param start
     * @param target
     * @param display
     * @param delay
     * @return
     */
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {

        setStart(start);
        setTarget(target);
        setCur(start);

        addCell(start);

        if (display == true) {
            mazedisplay = new MazeSearchDisplay(this, 30);
        }

        while (numRemainingCells() != 0) {

            if (display == true) {
                try {
                    Thread.sleep(delay);
                    mazedisplay.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            setCur(findNextCell());

            for (Cell neighbor : maze.getNeighbors(cur)) {
                if (neighbor.getPrev() == null) {
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    if (neighbor.getRow() == target.getRow() && neighbor.getCol() == target.getCol()) {
                        target.setPrev(cur);
                        return traceback(neighbor);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Draw search on Java graphics screen
     * 
     * @param g
     * @param scale
     */
    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze
        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);

        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }

}