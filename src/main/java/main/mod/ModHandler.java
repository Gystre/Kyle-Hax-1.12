package main.mod;

import main.KyleHax;
import main.handler.Handler;
import main.mod.hacks.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModHandler implements Handler {
    private LinkedHashMap<String, Mod> mods = new LinkedHashMap<>();

    @Override
    public void init(KyleHax kyleHax){
        //register mods here
        registerMod(new Fullbright(kyleHax));
        registerMod(new Nofall(kyleHax));
        registerMod(new Speed(kyleHax));
        registerMod(new AutoMine(kyleHax));
        registerMod(new BunnyHop(kyleHax));
        registerMod(new Step(kyleHax));
        registerMod(new KillAura(kyleHax));
    }

    private void registerMod(Mod mod){

        this.mods.put(mod.getName(), mod);
    }

    public Map<String, Mod> getMods(){
        return this.mods;
    }
}
