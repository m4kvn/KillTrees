package killtrees.utils

import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun Listener.register(plugin: JavaPlugin) = plugin.server.pluginManager.registerEvents(this, plugin)

fun <T : Event> T.call(plugin: JavaPlugin) : T = this.apply { plugin.server.pluginManager.callEvent(this) }
