package de.neo.remotedebugger.packets

import java.io.BufferedReader
import java.io.OutputStreamWriter

interface PacketHandler {

    fun handle(input: BufferedReader, output: OutputStreamWriter): Boolean

    fun writeDot(output: OutputStreamWriter) {
        output.write(". ")
        output.flush()
    }

}