import java.util.LinkedList;
import java.util.List;

public class Node {
    String digits;
    Node parent;
    List<Node> children;
    int lastMove;

    public Node(String digits, Node parent, int lastMove) {
        this.digits = digits;
        this.parent = parent;
        this.lastMove = lastMove;
        children = new LinkedList<>();
    }

    //these methods add the 2 children for adjusting each position of digits
    //but only add 1 child for 0 or 9
    private void adjustDigit1() {
        int digit1 = Integer.parseInt(digits.substring(0,1));
        int digit2 = Integer.parseInt(digits.substring(1,2));
        int digit3 = Integer.parseInt(digits.substring(2,3));
        if(digit1 == 0) {
            children.add(new Node("" + (digit1+1) + digit2 + digit3, this, 100));
        } else if (digit1 == 9) {
            children.add(new Node("" + (digit1-1) + digit2 + digit3, this, -100));
        }else {
            children.add(new Node("" + (digit1-1) + digit2 + digit3, this, -100));
            children.add(new Node("" + (digit1+1) + digit2 + digit3, this, 100));
        }

    }
    private void adjustDigit2() {
        int digit1 = Integer.parseInt(digits.substring(0,1));
        int digit2 = Integer.parseInt(digits.substring(1,2));
        int digit3 = Integer.parseInt(digits.substring(2,3));
        if(digit2 == 0) {
            children.add(new Node("" + digit1 + (digit2+1) + digit3, this, 10));
        } else if (digit2 == 9) {
            children.add(new Node("" + digit1 + (digit2-1) + digit3, this, -10));
        }else {
            children.add(new Node("" + digit1 + (digit2-1) + digit3, this, -10));
            children.add(new Node("" + digit1 + (digit2+1) + digit3, this, 10));
        }
    }
    private void adjustDigit3() {
        int digit1 = Integer.parseInt(digits.substring(0,1));
        int digit2 = Integer.parseInt(digits.substring(1,2));
        int digit3 = Integer.parseInt(digits.substring(2,3));
        if(digit3 == 0) {
            children.add(new Node("" + digit1 + digit2 + (digit3+1), this, 1));
        } else if (digit3 == 9) {
            children.add(new Node("" + digit1 + digit2 + (digit3-1), this, -1));
        }else {
            children.add(new Node("" + digit1 + digit2 + (digit3-1), this, -1));
            children.add(new Node("" + digit1 + digit2 + (digit3+1), this, 1));
        }
    }

    //mutate children into valid children and return children
    public LinkedList<Node> filteredChildren(List<String> forbidden) {
        switch (lastMove) {
            case -100:
            case 100:
                adjustDigit2();
                adjustDigit3();
                break;
            case -10:
            case 10:
                adjustDigit1();
                adjustDigit3();
                break;
            case -1:
            case 1:
                adjustDigit1();
                adjustDigit2();
                break;
            default:
                adjustDigit1();
                adjustDigit2();
                adjustDigit3();
        }
        children.removeIf(child -> forbidden.contains(child.digits));
        return (LinkedList<Node>) children;

    }

    @Override
    public boolean equals(Object other) {
        //todo: Make sure this checks for cycles correctly
        Node o = (Node) other;
        return o.digits.equals(digits) && Math.abs(o.lastMove) == Math.abs(lastMove);
    }

    public String toString() {
        return "" + digits;
    }
}
