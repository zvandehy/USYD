package command;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private Heater heater;
    private Map<String, Command> commands;

    public Invoker(Heater heater) {
        this.heater = heater;
        commands = new HashMap<>();
        commands.put("SwitchOn", new SwitchOn(heater));
        commands.put("SwitchOff", new SwitchOff(heater));
    }

    //leaving data in here as an example of how we would
    //pass data to a command (pass data as an argument to execute)
    public void run(String commandName, Object data) {
        commands.get(commandName).execute();
    }

}
