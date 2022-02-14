package de.neo.remotedebugger.packets.handler

import de.neo.remotedebugger.Launcher
import de.neo.remotedebugger.packets.PacketHandler
import de.neo.remotedebugger.vars.ObjectStore
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.lang.reflect.Modifier

class SetFieldHandler : PacketHandler {

    override fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean {
        val clazz = Launcher.classLoader.loadClass(input.readLine())
        val field = clazz.getDeclaredField(input.readLine())
        val value = when (input.readLine()) {
            "obj" -> {
                if(ObjectStore.contains(input.readLine())) {
                    ObjectStore.get(input.readLine())
                } else {
                    output.write("obj not found\n")
                    output.flush()
                    return false
                }
            }

            "string" -> {
                input.readLine()
            }

            else -> {
                output.write("unknown type\n")
                output.flush()
                return false
            }
        }
        val instance = run {
            if(Modifier.isStatic(field.modifiers)) {
                null
            } else {
                val instanceName = input.readLine()
                if(ObjectStore.contains(instanceName)) {
                    ObjectStore.get(instanceName)
                } else {
                    output.write("instance not found\n")
                    output.flush()
                    return false
                }
            }
        }
        val oldAccessible = field.canAccess(instance)
        field.isAccessible = true
        val oldValue = field.get(instance)
        field.set(instance, value)
        field.isAccessible = oldAccessible
        output.write("set $field from $oldValue to $value\n")
        output.flush()
        return false
    }

}