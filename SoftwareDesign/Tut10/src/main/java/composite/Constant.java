package composite;

public class Constant implements Component {
    private int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public int evaluate() {
        return value;
    }
}
