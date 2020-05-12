package composite;

//Composite
public class Add implements Component {
    private Component left, right;

    public Add(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate() {
        return left.evaluate() + right.evaluate();
    }


}
