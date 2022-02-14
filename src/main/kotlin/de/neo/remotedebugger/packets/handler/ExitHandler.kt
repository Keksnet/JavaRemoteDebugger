package de.neo.remotedebugger.packets.handler

import de.neo.remotedebugger.packets.PacketHandler
import java.io.BufferedReader
import java.io.OutputStreamWriter

class ExitHandler : PacketHandler {

    override fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean {
        output.write("Bye!\n")
        output.flush()
        return true
    }

}