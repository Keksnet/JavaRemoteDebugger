package de.neo.remotedebugger.packets.handler

import de.neo.remotedebugger.Launcher
import de.neo.remotedebugger.packets.PacketHandler
import de.neo.remotedebugger.vars.ObjectStore
import java.io.BufferedReader
import java.io.OutputStreamWriter

class ObjectStoreHandler : PacketHandler {

    override fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean {
        when(input.readLine()) {
            "get" -> {
                val name = input.readLine()
                if(ObjectStore.contains(name)) {
                    val value = ObjectStore.get(name)!!
                    output.write("$name = $value\n")
                } else {
                    output.write("$name not found\n")
                }
            }

            "set_string" -> {
                val name = input.readLine()
                val value = input.readLine()
                ObjectStore.put(name, value)
                output.write("set $name = $value\n")
            }

            "set_method" -> {
                val name = input.readLine()
                val clazz = Launcher.classLoader.loadClass(input.readLine())
                val method = input.readLine()
                val parameterCount = input.readLine().toInt()
                val parameters = ArrayList<Class<*>>()
                for(i in 0 until parameterCount) {
                    parameters.add(Launcher.classLoader.loadClass(input.readLine()))
                }
                val instance = ObjectStore.get(input.readLine())
                val value = clazz.getMethod(method, *parameters.toTypedArray()).invoke(instance)
                ObjectStore.put(name, value)
                output.write("set $name = $value\n")
                output.flush()
            }

            else -> {
                output.write("unknown command\n")
                output.flush()
            }
        }
        output.flush()
        return false
    }

}