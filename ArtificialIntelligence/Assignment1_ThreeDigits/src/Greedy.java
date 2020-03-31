import java.util.Comparator;
import java.util.List;

public class Greedy extends Game {
    public Greedy(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
    }

    @Override
    public int step() {
        fringe.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                int ret = h(n1) - h(n2);
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
