import java.util.LinkedList;
import java.util.List;

public class IterativeDeepeningSearch extends Game {
    int maxDepth;
    public IterativeDeepeningSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
        maxDepth = 0;
        fringe.clear();
    }

    @Override
    public int step() {
        //if fringe is empty then we need to expand the depth
        if (fringe.isEmpty()) {
            maxDepth += 1;
            fringe.add(root);
        }
        //get the first node in the fringe
        Node node;
        node = fringe.remove(0);
        try {
            //if the node would create a cycle
            while (expanded.contains(node)) {
                //move on to next node in fringe
                node = fringe.remove(0);

            }
        } catch(IndexOutOfBoundsException e) {
            //the only reason the fringe should be exhausted is if the tree was exhausted
            return -1;
        }
        //node does not create a cycle
        //add to temp, which will be added to expanded once the depth is exhausted
        //we use temp because checking for cycles on expanded would be redundant
        //otherwise of course there would be cycles! We literally iterate over the same nodes!
        expanded.add(node);

        //if we found the goal then return
        if (node.digits.equals(goal)) {
            solution = findSolutionPath(node);
            return 1;
        }
        //if depth has not reached the iterative depth limit
        if (node.depth < maxDepth) {
            //generate the children and add them to the fringe
            fringe.addAll(0, node.filteredChildren(forbidden));
        }

        //if depth has reached its limit, then we have not yet found a solution
        //we will step() through again, when we will increase the depth
        return 0;
    }
}
