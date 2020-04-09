import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Game {
    Node root;
    String goal;
    List<Node> expanded;
    List<Node> fringe;
    List<String> forbidden;
    List<Node> solution;

    public Game(String start, String goal, List<String> forbidden) {
        this.root = new Node(start,null, 0);
        this.goal = goal;
        this.forbidden = forbidden;
        this.fringe = root.filteredChildren(forbidden);
        this.expanded = new LinkedList<>();
        this.solution = new LinkedList<>();
    }

    public void processGame() {
        String ret = "";
        if(!forbidden.contains(root.digits)) {
            expanded.add(root);
            if (!root.digits.equals(goal)) {
                while (expanded.size() < 1000) {
                    int step = step();
                    //found solution
                    if (step == 1) {
                        //Set ret to the solution
                        for (int i = 0; i < solution.size() - 1; i++) {
                            ret += solution.get(i).digits + ",";
                        }
                        ret += solution.get(solution.size() - 1).digits + "\n";
                        break;
                    }
                    //all nodes exhausted without solution
                    else if (step == -1) {
                        ret = "No Solution Found\n";
                        break;
                    }
                }
                //Algorithm took too long
                if (ret.equals("")) {
                    ret = "No Solution Found\n";
                }
            } else {
                //root is the goal
                ret += root + "\n";
            }
            //Expanded nodes
            for(int i=0;i<expanded.size()-1;i++) {
                ret += expanded.get(i).digits + ",";
            }
            ret += expanded.get(expanded.size()-1).digits;
            System.out.println(ret);
        } else {
            System.out.println("No Solution Found");
            System.out.println();
        }
    }


    /**
     * This method is how each algorithm traverses through the game tree
     * return -1 if all nodes are exhausted without finding solution
     * return 0 if step() didn't find solution yet
     * return 1 if solution is found
     */
    public abstract int step();

    /**
     * Traceback from the goalFound node to the root node to return the solution path
     * @param goalFound - the goal node that was found by the algorithm
     * @return the path from the goalFound node to the root node
     */
    public List<Node> findSolutionPath(Node goalFound) {
        LinkedList<Node> ret = new LinkedList<>();
        ret.add(goalFound);
        Node p = goalFound.parent;
        while(p!=null) {
            ret.add(0, p);
            p = p.parent;
        }
        return ret;
    }

    /**
     * Calculate the h(n) heuristic cost using the manhattan distance from node to goal
     * @return the manhattan distance from the node to the goal
     */
    public int h(Node n) {
        String node = n.digits;
        int h = 0;
        int nodeDigit1 = Integer.parseInt(node.substring(0,1));
        int goalDigit1 = Integer.parseInt(goal.substring(0,1));
        int nodeDigit2 = Integer.parseInt(node.substring(1,2));
        int goalDigit2 = Integer.parseInt(goal.substring(1,2));
        int nodeDigit3 = Integer.parseInt(node.substring(2,3));
        int goalDigit3 = Integer.parseInt(goal.substring(2,3));
        h+= Math.abs(nodeDigit1 - goalDigit1);
        h+= Math.abs(nodeDigit2 - goalDigit2);
        h+= Math.abs(nodeDigit3 - goalDigit3);
        return h;
    }

    public String toString() {
        String ret = "<==GAME==>\n";
//        ret += "ROOT    : " + root + "\n";
        ret += "EXPANDED: " + expanded + "\n";
        ret += "FRINGE  : " + fringe + "\n";
//        ret += "GOAL    : " + goal + "\n";
        return ret;
    }

}

