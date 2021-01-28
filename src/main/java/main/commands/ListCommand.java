package main.commands;

import main.Helper;
import main.KyleHax;
import main.mod.Mod;

public class ListCommand extends Command {
    public ListCommand(KyleHax kyleHax){
        super(kyleHax, "list", "Lists currently enabled mods", ".list");
    }

    @Override
    public void call(String[] args){
        String temp = "Currently enabled hacks:\n";

        for(Mod mod : kyleHax.modHandler.getMods().values()){
            if(mod.isEnabled()){
                temp += mod.getName() + "\n";
            }
        }

        Helper.outputInfo(temp);
    }
}
