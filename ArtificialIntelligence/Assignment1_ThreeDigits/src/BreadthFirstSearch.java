import java.util.LinkedList;
import java.util.List;

public class BreadthFirstSearch extends Game {
    public BreadthFirstSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
    }

    @Override
    public int step() {
        if(fringe.size() == 0) {
            return -1;
        }
        //get first node in fringe
        Node node = fringe.remove(0);
        //if node has same digits and if node has same lastMove (and thus has same children)
        //todo: It may not be enough to only check lastMove because of root,
        //may need to check if children of each node in E contain all children in node
        while(expanded.contains(node)) {
            //move on to next node in fringe
            node = fringe.remove(0);
        }
        //now node is valid
        expanded.add(node);
        //stop if node is a goal
        if(node.digits.equals(goal)) {
            solution = findSolutionPath(node);
            return 1;
        }
        //add successors to end of fringe to be searched after all nodes in this node's level
        fringe.addAll(node.filteredChildren(forbidden));
        return 0;
    }
}
