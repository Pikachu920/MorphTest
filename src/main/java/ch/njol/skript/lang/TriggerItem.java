package ch.njol.skript.lang;

import org.bukkit.event.Event;

public class TriggerItem {

    public static boolean walk(TriggerItem to, Event e) {
        System.out.println("Execution was enabled");
        return true;
    }

}
