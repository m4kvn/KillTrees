package killtrees.listener

import killtrees.call
import killtrees.events.TreeKillEvent
import killtrees.haveAxe
import killtrees.isLog
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class BlockBreakEventListener(val plugin: JavaPlugin) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {

        when {
            !event.block.isLog()    -> return
            !event.player.haveAxe() -> return
        }

        TreeKillEvent(event, plugin).call(plugin)
    }
}