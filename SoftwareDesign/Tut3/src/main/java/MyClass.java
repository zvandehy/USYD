package tut3;

public class MyClass {
    private MyDependency myDependency;

    public MyClass(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    public String doSomething() {
        return myDependency.getString();
    }
}
