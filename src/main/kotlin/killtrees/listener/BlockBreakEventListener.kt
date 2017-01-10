package killtrees.listener

import killtrees.*
import killtrees.config.isInValidType
import killtrees.events.TreeKillEvent
import killtrees.utils.call
import killtrees.utils.itemInMainHand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class BlockBreakEventListener(val plugin: JavaPlugin) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {

        when {
            event.block.isInValidType()                   -> return
            event.player.itemInMainHand().isInValidType() -> return
        }

        TreeKillEvent(event, plugin).call(plugin)
    }
}