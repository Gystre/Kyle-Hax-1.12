package main.settings;

import java.util.Objects;

public abstract class Setting {
    public enum Type{
        FLOAT,
        BOOLEAN
    }

    private final Type type;
    private final String name;
    private final String description;

    public Setting(String name, String description, Type type){
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.type = type;
    }

    public final String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Type getType() {
        return type;
    }
}
