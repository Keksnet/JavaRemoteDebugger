package de.neo.remotedebugger.vars

object ObjectStore {

    private val store = HashMap<String, Any?>()

    fun put(key: String, value: Any?) {
        store[key] = value
    }

    fun get(key: String): Any? {
        return store[key]
    }

    fun contains(key: String): Boolean {
        return store.containsKey(key)
    }

    fun remove(key: String) {
        store.remove(key)
    }

}