package de.neo.remotedebugger.io

import de.neo.remotedebugger.packets.PacketRegistry
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class ConnectionHandler(private val client: Socket) {

    fun handle() {
        val input = client.getInputStream()
        val output = client.getOutputStream()
        val reader = BufferedReader(InputStreamReader(input))
        val writer = OutputStreamWriter(output)

        while(true) {
            output.write("> ".toByteArray())
            output.flush()
            val id = reader.readLine()
            val handler = PacketRegistry.getHandler(id) ?: throw IllegalArgumentException("No handler for packet id $id")
            val handleResult = handler.handle(reader, writer)
            output.flush()
            if(handleResult) {
                break
            }
        }
        client.close()
    }

}