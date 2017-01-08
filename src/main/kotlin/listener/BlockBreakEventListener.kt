package listener

import call
import events.TreeKillEvent
import haveAxe
import isLog
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * Created by masahiro on 2016/12/29.
 */

class BlockBreakEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {

        when {
            !event.block.isLog()    -> return
            !event.player.haveAxe() -> return
        }

        TreeKillEvent(event).call()
    }
}