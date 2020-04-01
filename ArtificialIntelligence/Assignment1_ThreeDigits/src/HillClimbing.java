import java.util.Comparator;
import java.util.List;

public class HillClimbing extends Game {
    Node currentNode;
    public HillClimbing(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
        currentNode = root;
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
            //since it is sorted by h(n) = manhattan distance
            //node will be the "best" child
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
        if(h(node) < h(currentNode)) {
            expanded.add(node);
            currentNode = node;
            fringe.addAll(0,currentNode.filteredChildren(forbidden));
            //stop if node is a goal
            if(node.digits.equals(goal)) {
                solution = findSolutionPath(node);
                return 1;
            }
            return 0;
        }
        return -1;
    }
}
