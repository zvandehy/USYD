package command;

public class SwitchOn implements Command {
    private final Heater heater;

    public SwitchOn(Object heater) {
        this.heater = (Heater) heater;
    }
    public void execute() {
        heater.turnOn();
    }
}
