package mypackage;

public class MyDependencyImpl {
    public String getSomeResult(String param, int paramInt) {
        return "the contents of this string are irrelevant, but if it's null MyClass.doSomething will fail. Was sent: '" + param + "', " + paramInt;
    }
}
