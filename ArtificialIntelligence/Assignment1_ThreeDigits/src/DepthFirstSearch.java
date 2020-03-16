import java.util.List;

public class DepthFirstSearch extends Game {
    public DepthFirstSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
    }

    @Override
    public int step() {
        Node node;
        try {
            //get first node in fringe
            node = fringe.remove(0);
            //if node has same digits and if node has same lastMove (and thus has same children)
            //todo: It may not be enough to only check lastMove because of root,
            //may need to check if children of each node in E contain all children in node
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
        System.out.println("-------");
        System.out.println(fringe);
        fringe.addAll(0, node.filteredChildren(forbidden));
        System.out.println(fringe);
        System.out.println("++++++++=");
        return 0;
    }
}
