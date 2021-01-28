package main.settings;

public class SettingBoolean extends Setting{
    private final boolean defaultValue;
    private boolean value;

    public SettingBoolean(String name, String description, boolean value){
        super(name, description, Type.BOOLEAN);
        this.defaultValue = value;
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

    public void setValue(boolean value){
        this.value = value;
    }

    public boolean getDefaultValue(){
        return defaultValue;
    }
}
