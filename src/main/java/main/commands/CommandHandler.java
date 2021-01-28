package main.commands;

import main.KyleHax;

import main.handler.Handler;

import java.util.LinkedHashMap;

public class CommandHandler implements Handler {
    private LinkedHashMap<String, Command> commands = new LinkedHashMap<>();

    @Override
    public void init(KyleHax kyleHax){
        registerCommand(new HelpCommand(kyleHax));
        registerCommand(new ToggleCommand(kyleHax));
        registerCommand(new SetCommand(kyleHax));
        registerCommand(new ListCommand(kyleHax));
    }

    public void registerCommand(Command command){
        commands.put(command.getName(), command);
    }

    public LinkedHashMap<String, Command> getCommands() {
        return commands;
    }
}
