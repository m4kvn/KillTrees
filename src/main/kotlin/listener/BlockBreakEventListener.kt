package listener

import blockFaces
import callEvent
import config.configs
import events.*
import haveAxe
import isLog
import org.bukkit.block.Block
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
            !isLog(event.block)    -> return
            !haveAxe(event.player) -> return
        }

        val blocks = mutableListOf<Block>().apply {
            val unCheckedBlocks = mutableListOf(event.block)
            val checkedBlocks = this

            while (unCheckedBlocks.isNotEmpty()) {
                val block = unCheckedBlocks.removeAt(0).apply { checkedBlocks.add(this) }

                blockFaces.forEach {
                    val relativeBlock = block.getRelative(it)

                    blockFaces
                            .filterNot { checkedBlocks.contains(relativeBlock.getRelative(it)) }
                            .filterNot { unCheckedBlocks.contains(relativeBlock.getRelative(it)) }
                            .filter { isLog(relativeBlock.getRelative(it)) }
                            .forEach { unCheckedBlocks.add(relativeBlock.getRelative(it)) }
                }
            }
        }

        val tool = event.player.inventory.itemInMainHand

        val isToolBreak = tool.durability + blocks.size >= tool.type.maxDurability

        val overBlockAmount = if (isToolBreak) tool.durability + blocks.size - tool.type.maxDurability else 0

        val finalBlocks = blocks.toMutableList().apply {
            if (isToolBreak) kotlin.repeat(overBlockAmount) { removeAt(lastIndex) }
        }

        callEvent(BreakBlocksEvent(finalBlocks, tool)).breakBlocks()

        var message = ""

        if (isToolBreak) {
            callEvent(BreakToolEvent(event.player)).breakTool()
            if (configs.onToolBrokenMsg) message = "道具が壊れました, ブロック数: ${finalBlocks.size}"
        } else {
            callEvent(ChangeToolDurabilityEvent(tool, finalBlocks.size)).changeToolDurability()
            if (configs.onToolDurabilityMsg) {
                message = "耐久値: ${tool.type.maxDurability - tool.durability}, " +
                        "ブロック数: ${finalBlocks.size}"
            }
        }

        callEvent(TreeKillMessageEvent(message, event.player)).sendMessage()
    }
}