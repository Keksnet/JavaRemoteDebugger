package de.neo.remotedebugger.packets.handler

import de.neo.remotedebugger.Launcher
import de.neo.remotedebugger.packets.PacketHandler
import de.neo.remotedebugger.vars.ObjectStore
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.lang.reflect.Modifier

class PrintFieldsHandler : PacketHandler {

    override fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean {
        writeDot(output)
        val className = input.readLine()
        val clazz = Launcher.classLoader.loadClass(className)
        val fields = clazz.declaredFields
        writeDot(output)
        val instance = input.readLine()
        for (field in fields) {
            val obj = run {
                if(Modifier.isStatic(field.modifiers)) {
                    null
                }else {
                    if(ObjectStore.contains(instance))
                        ObjectStore.get(instance)
                    else {
                        output.write("ERROR: Object $instance not found\n")
                        output.flush()
                        return false
                    }
                }
            }
            val oldAccessibility = field.canAccess(obj)
            field.isAccessible = true
            output.write("$field = ${field.get(obj)}\n")
            output.flush()
            field.isAccessible = oldAccessibility
        }
        return false
    }

}