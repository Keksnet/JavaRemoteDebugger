package de.neo.remotedebugger.packets

object PacketRegistry {

    private val handlerMap = HashMap<String, PacketHandler>()

    fun registerPacket(id: String, handler: PacketHandler) {
        handlerMap[id] = handler
    }

    fun getHandler(id: String): PacketHandler? {
        return handlerMap[id]
    }
}