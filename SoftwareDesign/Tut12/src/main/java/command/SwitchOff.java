package command;

public class SwitchOff implements Command{
    private final Heater heater;

    public SwitchOff(Object heater) {
        this.heater = (Heater) heater;
    }
    public void execute() {
        heater.turnOff();
    }
}
