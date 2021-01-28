package main.commands;

import main.Helper;
import main.KyleHax;
import main.settings.Setting;
import main.settings.SettingBoolean;
import main.settings.SettingFloat;

import java.util.Map;

public class HelpCommand extends Command {
    public HelpCommand(KyleHax kyleHax){
        super(kyleHax,"help", "Displays basic command list in the chat", ".help", ".help <hack>");
    }

    @Override
    public void call(String[] args){
        if(args.length == 1){
            Helper.outputInfo("KyleHax, a hack client in a forge mod \n .help <hack> \n .toggle <hack> \n .set <hack> <setting-name> <value> \n .list");
        }else if(args.length == 2){
            if(kyleHax.modHandler.getMods().containsKey(args[1])){
                Map<String, Setting> settings = kyleHax.modHandler.getMods().get(args[1]).getSettings();
                if(!settings.isEmpty()){
                    String temp = "Settings for " + args[1] + ":\n";
                    for(Setting setting : settings.values()){
                        if(setting.getType() == Setting.Type.BOOLEAN){
                            SettingBoolean bSetting = (SettingBoolean)setting;
                            temp += bSetting.getName() + " = " + bSetting.getValue() + " (" + bSetting.getDescription() + ")\n";
                        }else if(setting.getType() == Setting.Type.FLOAT){
                            SettingFloat fSetting = (SettingFloat)setting;
                            temp += fSetting.getName() + " = " + fSetting.getValue() + " (" + fSetting.getDescription() + ")\n";
                        }
                    }
                    Helper.outputInfo(temp);
                }else{
                    Helper.outputInfo("There are no settings for " + args[1]);
                }
            }else{
                Helper.outputInfo("Cannot find mod " + args[1]);
            }
        }else{
            Helper.outputError(getSyntax()[0] + " " + getSyntax()[1]);
        }
    }
}
