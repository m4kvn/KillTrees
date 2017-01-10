package killtrees.events

import killtrees.call
import killtrees.utils.itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class TreeKillEvent(val event: BlockBreakEvent, val plugin: JavaPlugin) : BaseCancellableEvent() {

    val block = event.block!!
    val player = event.player!!
    val tool = player.itemInMainHand()

    var blocks = listOf<Block>()
    var isToolBreak = false

    fun <T : BaseCancellableEvent> callTreeKillEvent(event: T) : T =
        if (isNotCancelled) event.call(plugin) else event
}