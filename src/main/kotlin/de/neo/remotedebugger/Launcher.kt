package de.neo.remotedebugger

import de.neo.remotedebugger.io.DebugListener
import de.neo.remotedebugger.packets.PacketRegistry
import de.neo.remotedebugger.packets.handler.*
import de.neo.remotedebugger.vars.ObjectStore
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class Launcher(private val mainClass: String) {

    var debugger: DebugListener? = null

    fun registerDebugListener() {
        PacketRegistry.registerPacket("list_methods", ListMethodsHandler())
        PacketRegistry.registerPacket("exit", ExitHandler())
        PacketRegistry.registerPacket("obj_store", ObjectStoreHandler())
        PacketRegistry.registerPacket("print_vars", PrintFieldsHandler())
        PacketRegistry.registerPacket("set_field", SetFieldHandler())
    }

    fun launch(args: Array<String>) {
        debugger = DebugListener(3031)
        debugger!!.start()
        logger("Debugger started on port 3031")
        val mainClass = classLoader.loadClass(mainClass)
        val mainMethod = mainClass.getMethod("main", Array<String>::class.java)
        mainMethod.invoke(null, args)
    }

    companion object {
        val classLoader = run {
            val path = Paths.get(".").toAbsolutePath()
            val files = Files.list(path).toArray()
            val urls = run {
                val list = arrayListOf<URL>()
                for (file in files) {
                    if(file !is Path) continue
                    if (file.toString().endsWith(".jar")) {
                        list.add(file.toUri().toURL())
                        println("[Debugger - ${Thread.currentThread().name}] Added ${file.toUri().toURL()}")
                    }
                }
                list.toTypedArray()
            }
            println("[Debugger - ${Thread.currentThread().name}] Loading classes from ${urls.contentToString()}")
            URLClassLoader(urls, ClassLoader.getSystemClassLoader())
        }

        val logger: (String) -> Unit = {
            println("[Debugger - ${Thread.currentThread().name}] $it")
        }
    }

}

fun main(args: Array<String>) {
    ObjectStore.put("null", null)
    val launcher = Launcher(args[0])
    launcher.registerDebugListener()
    launcher.launch(args.copyOfRange(1, args.size))
    System.`in`.read()
    launcher.debugger!!.stop()
}