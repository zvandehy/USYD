public class MyDependencyImpl implements MyDependency {
    private final String someString;

    public MyDependencyImpl(String someString) {
        this.someString = someString;
    }

    public String getString() {
        return someString;
    }

    @Override
    public String getString(boolean bool) {
        return null;
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public void doThis() {

    }
}
