import java.util.LinkedList;
import java.util.List;

public class AStar extends Game {
    public AStar(String root, String goal, List<String> forbidden) {
        super(root, goal, forbidden);
    }

    @Override
    public int step() {
        return -1;
    }

    @Override
    public List<Node> findSolutionPath(Node foundGoal) {
        return null;
    }

    @Override
    public String toString() {
        return "$$$ ASTAR $$$\n" + super.toString();
    }
}
