import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AStar extends Game {
    public AStar(String root, String goal, List<String> forbidden) {
        super(root, goal, forbidden);
    }

    private int fCost(Node n) {
        //f(n) = g(n) + h(n)
        //h(n) is manhattan distance to goal node
        //g(n) is cost so far
        //each step is 1, so the solution path to the given node is g(n)
        //todo: Can the solution path be replaced by the depth of the node?
        int g = findSolutionPath(n).size();
        return h(n) + g;
    }

    @Override
    public int step() {
        fringe.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                int ret = fCost(n1) - fCost(n2);
                if(ret == 0) {
                    ret = -1;
                }
                return ret;
            }
        });
        Node node;
        try {
            //get first node in fringe
            node = fringe.remove(0);
            //if node has same lastMove (and thus has same children)
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
        //add successors at front of fringe to be searched before other nodes in this node's level
        fringe.addAll(node.filteredChildren(forbidden));
        return 0;
    }
}
