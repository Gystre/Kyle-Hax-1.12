package main.settings;

public class SettingFloat extends Setting {
    private final float defaultValue;
    private float value;

    public SettingFloat(String name, String description, float value){
        super(name, description, Type.FLOAT);
        this.defaultValue = value;
        this.value = value;
    }

    public float getValue(){
        return value;
    }

    public void setValue(float value){
        this.value = value;
    }

    public float getDefaultValue(){
        return defaultValue;
    }
}
