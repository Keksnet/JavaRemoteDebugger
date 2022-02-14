package de.neo.remotedebugger.io

import de.neo.remotedebugger.Launcher
import java.net.InetAddress
import java.net.ServerSocket

class DebugListener(val port: Int) {

    private var server: ServerSocket? = null

    fun start() {
        Thread {
            server = ServerSocket(port, 0, InetAddress.getByName("0.0.0.0"))

            while (true) {
                val socket = server!!.accept()
                Launcher.logger("New connection from ${socket.inetAddress}")
                val debugConnection = ConnectionHandler(socket)
                Launcher.logger("Starting connection handler")
                try {
                    debugConnection.handle()
                }catch (e: IllegalArgumentException) {
                    socket.getOutputStream().write("IllegalArgumentException: ${e.message}\n".toByteArray())
                    socket.close()
                    e.printStackTrace()
                }
            }
        }.start()
    }

    fun stop() {
        server!!.close()
    }

}