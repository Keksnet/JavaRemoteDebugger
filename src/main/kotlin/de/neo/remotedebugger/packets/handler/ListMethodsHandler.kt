package de.neo.remotedebugger.packets.handler

import de.neo.remotedebugger.Launcher
import de.neo.remotedebugger.packets.PacketHandler
import java.io.BufferedReader
import java.io.OutputStreamWriter

class ListMethodsHandler : PacketHandler {

    override fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean {
        writeDot(output)
        val className = input.readLine()
        val clazz = Launcher.classLoader.loadClass(className)
        for(method in clazz.methods) {
            output.write(method.toString())
            output.write("\n")
        }
        output.flush()
        return false
    }

}