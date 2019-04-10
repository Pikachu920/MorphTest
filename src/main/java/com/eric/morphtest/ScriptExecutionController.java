package com.eric.morphtest;

import ch.njol.skript.lang.TriggerItem;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.Descriptor;
import org.bukkit.event.Event;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public final class ScriptExecutionController {

    private static Instrumentation instrumentation;
    private static boolean executionEnabled = true;

    private ScriptExecutionController() {
        throw new IllegalStateException(getClass().getSimpleName() + " may not be instantiated");
    }

    private static void checkForAgent() {
        if (instrumentation == null) {
            throw new IllegalStateException("Instrumentation instance is null");
        }
    }

    public static void setup() {
        checkForAgent();

        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass triggerItemClass = pool.get(TriggerItem.class.getName());
            CtMethod walkMethod = triggerItemClass.getMethod(
                    "walk",
                    Descriptor.ofMethod(
                            CtClass.booleanType,
                            new CtClass[] {
                                    pool.get(TriggerItem.class.getName()),
                                    pool.get(Event.class.getName())
                            }
                    )
            );
            walkMethod.insertBefore("{if (!" + ScriptExecutionController.class.getName() + ".isExecutionEnabled()) return false;}");
            instrumentation.redefineClasses(new ClassDefinition(TriggerItem.class, triggerItemClass.toBytecode()));
        } catch (Throwable e) {
            throw new IllegalStateException("Failed to modify TriggerItem#walk", e);
        }

    }

    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    public static void setExecutionEnabled(boolean enabled) {
        checkForAgent();
        executionEnabled = enabled;
    }

    public static boolean isExecutionEnabled() {
        checkForAgent();
        return executionEnabled;
    }

}
