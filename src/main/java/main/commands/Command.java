package main.commands;

import main.KyleHax;

public abstract class Command {
    protected KyleHax kyleHax;
    private final String name;
    private final String description;
    private final String[] syntax;

    public Command(KyleHax kyleHax, String name, String description, String... syntax){
        this.kyleHax = kyleHax;
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getSyntax() {
        return syntax;
    }

    //NOTE: First arg will always be command, [0] = .help
    public abstract void call(String[] args);
}
