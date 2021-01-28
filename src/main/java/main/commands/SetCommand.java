package main.commands;

import main.Helper;
import main.KyleHax;
import main.settings.Setting;
import main.settings.SettingBoolean;
import main.settings.SettingFloat;

public class SetCommand extends Command {
    public SetCommand(KyleHax kyleHax){
        super(kyleHax, "set","Sets the setting of a hack", ".set <hack> <setting> <value>");
    }

    @Override
    public void call(String[] args){
        //TODO: swap out these if statements trees for proper command exceptions
        if(args.length != 4) {
            Helper.outputError(getSyntax()[0]);
            return;
        }

        //Bug: put wrong type in for setting and it crashes, don't do that
        if(kyleHax.modHandler.getMods().containsKey(args[1])){
            if(kyleHax.modHandler.getMods().get(args[1]).getSettings().containsKey(args[2])){
                Setting setting = kyleHax.modHandler.getMods().get(args[1]).getSettings().get(args[2]);

                if((args[3].equals("true") || args[3].equals("false")) && setting.getType() == Setting.Type.BOOLEAN){
                    SettingBoolean settingBoolean = (SettingBoolean)setting;
                    settingBoolean.setValue(Boolean.parseBoolean(args[3]));
                    Helper.outputInfo("Set " + args[2] + " to " + args[3] + " for " + args[1]);
                }else{
                    SettingFloat settingFloat = (SettingFloat)setting;
                    settingFloat.setValue(Float.parseFloat(args[3]));
                    Helper.outputInfo("Set " + args[2] + " to " + args[3] + " for " + args[1]);
                }
            }else{
                Helper.outputError("Cannot find setting " + args[2]);
            }
        }else {
            Helper.outputError("Cannot find mod " + args[1]);
        }

    }
}
