package composite;

//Composite
public class Subtract implements Component {
    private Component left, right;

    public Subtract(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate() {
        return left.evaluate() - right.evaluate();
    }


}
