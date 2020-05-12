package composite;

//Composite
public class Divide implements Component {
    private Component left, right;

    public Divide(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate() {
        return left.evaluate() / right.evaluate();
    }


}
