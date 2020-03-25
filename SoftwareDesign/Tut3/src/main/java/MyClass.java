public class MyClass {
    private MyDependency myDependency;

    public MyClass(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    public String doSomething(boolean bool) {
        myDependency.getInt();
        myDependency.doThis();
        if (myDependency.getString(bool) == null) {
            return "one thing";
        } else {
            return "another thing";
        }
    }
}
