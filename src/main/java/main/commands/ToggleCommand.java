package main.commands;

import main.Helper;
import main.KyleHax;
import main.mod.Mod;

public class ToggleCommand extends Command {
    public ToggleCommand(KyleHax kyleHax){
        super(kyleHax, "toggle", "Toggles a hack, turning it on or off", ".toggle <hack>");
    }

    @Override
    public void call(String[] args){
        if(args.length != 2) {
            Helper.outputError(getSyntax()[0]);
            return;
        }

        if(!kyleHax.modHandler.getMods().containsKey(args[1])){
            Helper.outputError("Cannot find mod " + args[1]);
        }else{
            Mod mod = kyleHax.modHandler.getMods().get(args[1]);
            mod.setEnabled(!mod.isEnabled());

            Helper.outputInfo(mod.getName() + " is now " + (mod.isEnabled() ? "on" : "off"));

            if(!mod.isEnabled()){
                mod.onDisable();
            }
        }
    }
}
