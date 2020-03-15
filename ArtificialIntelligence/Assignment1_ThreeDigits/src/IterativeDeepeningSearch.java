import java.util.List;

public class IterativeDeepeningSearch extends Game {
    public IterativeDeepeningSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
    }

    @Override
    public int step() {
        return -1;
    }

    @Override
    public List<Node> findSolutionPath(Node foundGoal) {
        return null;
    }
}
