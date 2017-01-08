package listener

import calcBreakBlocks
import config.canReduceDurability
import events.*
import getRemainingDurability
import info
import isBreak
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

/**
 * Created by masahiro on 2017/01/08.
 */
class TreeKillEventLisntener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onTreeKillEvent(event: TreeKillEvent) {
        if (event.isCancelled) return

        event.callTreeKillEvent(CalcBreakBlock(event)).apply {
            if (isNotCancelled) event.blocks = block.calcBreakBlocks(player)
            else event.cancel()
        }

        event.callTreeKillEvent(CheckToolBreakEvent(event)).apply {
            if (isNotCancelled && player.canReduceDurability())
                event.isToolBreak = tool.isBreak(blocks)
        }

        event.callTreeKillEvent(ReduceBreakBlockEvent(event)).apply {
            if (isNotCancelled && isToolBreak){
                event.blocks = blocks.take(tool.getRemainingDurability())
            }
        }

        event.callTreeKillEvent(BreakBlocksEvent(event)).apply {
            if (isNotCancelled) blocks.forEach { it.breakNaturally(tool) }
            else event.cancel()
        }

        event.callTreeKillEvent(ChangeToolDurabilityEvent(event)).apply {
            if (isNotCancelled && player.canReduceDurability())
                tool.durability = tool.durability.plus(damage).toShort()
        }

        event.callTreeKillEvent(BreakToolEvent(event)).apply {
            if(isNotCancelled && isToolBreak) {
                player.world.playSound(player.location, sound, soundVolume, soundSpeed)
                player.inventory.itemInMainHand = changeItem
            }
        }

        event.callTreeKillEvent(TreeKillMessageEvent(event)).apply {
            if (isNotCancelled) player.sendMessage(message)
        }
    }
}