/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Tutorial1_JUnitTesting;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
public class ShoppingBasket {
    //Constructor
    public ShoppingBasket(){};

    //Methods
    public void addItem(String item, int count) throws Exception { //what type is ArgumentException and NumberException?

    }

    public boolean removeItem(String item, int count) throws Exception {
        return false;
    }

    public List<Map.Entry<String, Integer>> getItems() {

    }

    public int getValue() {

    }

    public void clear() {

    }
}
