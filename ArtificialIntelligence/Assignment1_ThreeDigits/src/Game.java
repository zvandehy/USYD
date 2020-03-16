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
        System.out.println("INITIALIZE  " + fringe);
        this.expanded = new LinkedList<>();
        expanded.add(root);
        this.solution = new LinkedList<>();
    }

    public void processGame() {
        String ret = "";
        while(expanded.size() < 1000) {
            int step = step();
            //found solution
            if (step == 1) {
                for(int i=0;i<solution.size()-1;i++) {
                    ret += solution.get(i).digits + ",";
                }
                ret += solution.get(solution.size()-1).digits + "\n";
                break;
            }
            //all nodes exhausted without solution
            else if(step == -1) {
                ret = "No Solution Found\n";
                break;
            }
        }
        //Algorithm took too long
        if(ret.equals("")) {
            ret = "No Solution Found\n";
        }
        //Expanded nodes
        for(int i=0;i<expanded.size()-1;i++) {
            ret += expanded.get(i).digits + ",";
        }
        ret += expanded.get(expanded.size()-1).digits;
        System.out.println(ret);
    }

    //return -1 if all nodes are exhausted without finding solution
    //return 0 if step() didn't find solution yet
    //return 1 if solution is found
    public abstract int step();
    public List<Node> findSolutionPath(Node goalFound) {
        LinkedList<Node> ret = new LinkedList<>();
        ret.add(goalFound);
        Node p = goalFound.parent;
        while(p!=null) {
            ret.add(0, p);
            p = p.parent;
        }
        return solution = ret;
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

