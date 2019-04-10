package com.eric.morphtest;

import ch.njol.skript.lang.TriggerItem;
import javassist.*;
import javassist.bytecode.Descriptor;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.UnmodifiableClassException;

public class Main {

    public static void main(String[] args) throws NotFoundException, IOException, CannotCompileException, ClassNotFoundException, UnmodifiableClassException {
        ScriptExecutionController.setup();

        System.out.println("Should print...");
        TriggerItem.walk(null, null);

        ScriptExecutionController.setExecutionEnabled(false);
        System.out.println("Shouldn't print...");
        TriggerItem.walk(null, null);

        ScriptExecutionController.setExecutionEnabled(true);
        System.out.println("Should print...");
        TriggerItem.walk(null, null);

    }

}
