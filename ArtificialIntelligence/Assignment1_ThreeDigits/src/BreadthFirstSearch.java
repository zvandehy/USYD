import java.util.LinkedList;
import java.util.List;

public class BreadthFirstSearch extends Game {
    public BreadthFirstSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
    }

    @Override
    public int step() {
        Node node;
        try {
            //get first node in fringe
            node = fringe.remove(0);
            //if node has same digits and if node has same lastMove (and thus has same children)

            while(expanded.contains(node)) {
                //move on to next node in fringe
                node = fringe.remove(0);
            }
        } catch(IndexOutOfBoundsException e) {
            return -1;
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
