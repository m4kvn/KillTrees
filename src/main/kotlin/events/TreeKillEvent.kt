package events

import call
import itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent

/**
 * Created by masahiro on 2017/01/08.
 */

class TreeKillEvent(event: BlockBreakEvent) : BaseCancellableEvent() {

    val player = event.player
    val block = event.block
    val tool = player.itemInMainHand()

    var blocks = listOf<Block>()
    var isToolBreak = false

    fun <T : BaseCancellableEvent> callTreeKillEvent(event: T) : T =
        if (isNotCancelled) event.call() else event
}