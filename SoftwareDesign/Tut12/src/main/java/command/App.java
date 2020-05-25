package command;

public class App {
    public static void main(String[] args) {
        Heater myHeater = new Heater();
        Invoker heaterController = new Invoker(myHeater);
        heaterController.run("SwitchOn", null);
        heaterController.run("SwitchOff", null);
    }
}
