package composite;

//Composite
public class Multiply implements Component {
    private Component left, right;

    public Multiply(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate() {
        return left.evaluate() * right.evaluate();
    }


}
