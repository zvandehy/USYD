import java.util.LinkedList;
import java.util.List;

public class IterativeDeepeningSearch extends Game {
    int maxDepth;
    List<Node> temp;
    public IterativeDeepeningSearch(String start, String goal, List<String> forbiddenList) {
        super(start, goal, forbiddenList);
        maxDepth = 0;
        fringe.clear();
        temp = new LinkedList<>();
    }

    @Override
    public int step() {
        if (fringe.isEmpty()) {
            maxDepth += 1;
            fringe.add(root);
            //todo: This means that expanded could exceed 1000 states...
            expanded.addAll(temp);
            temp = new LinkedList<>();
        }
        Node node;
        node = fringe.remove(0);
        try {
            //check for cycle
            while (temp.contains(node)) {
                //move on to next node in fringe
                node = fringe.remove(0);
            }
        } catch(IndexOutOfBoundsException e) {
            System.out.println("####ERROR####");
            System.out.println("expand: " + expanded);
            System.out.println("temp  : " + temp);
            System.out.println("fringe: " + fringe);
//            System.out.println("node depth: " + node.depth);
            System.out.println("max depth : " + maxDepth);
            System.out.println("#############");
            return -1;
        }
        temp.add(node);

        if (node.digits.equals(goal)) {
            solution = findSolutionPath(node);
            return 1;
        }
        if (node.depth < maxDepth) {
            System.out.println("Why is the fringe doubled? ");
            System.out.println("%%%%%%%%%%% BEFORE %%%%%%%%%%%%%%%");
            System.out.println("expand: " + expanded + temp);
            System.out.println("fringe: " + fringe);
            System.out.println("node depth: " + node.depth);
            System.out.println("max depth : " + maxDepth);
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            //Todo: for some reason filteredChildren() is adding children into the fringe
            System.out.println("f" + fringe + System.identityHashCode(fringe));
            System.out.println("c" + node.children + System.identityHashCode(node.children));
            List<Node> filtered = node.filteredChildren(forbidden);
            System.out.println("f" + fringe + System.identityHashCode(fringe));
            System.out.println("c" + node.children + + System.identityHashCode(node.children));
            fringe.addAll(0, filtered);
            System.out.println("f" + fringe + System.identityHashCode(fringe));
            System.out.println("c" + node.children + System.identityHashCode(node.children));
            System.out.println("%%%%%%%%%%% AFTER %%%%%%%%%%%%%%%%");
            System.out.println("expand: " + expanded + temp);
            System.out.println("fringe: " + fringe);
            System.out.println("node depth: " + node.depth);
            System.out.println("max depth : " + maxDepth);
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        System.out.println("---DEBUGGING---");
        System.out.println("expand: " + expanded + temp);
        System.out.println("fringe: " + fringe);
        System.out.println("node depth: " + node.depth);
        System.out.println("max depth : " + maxDepth);
        System.out.println("---------------");
        return 0;
    }
}
