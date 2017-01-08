package listener

import callEvent
import config.configs
import events.*
import getRelativeBlocks
import haveAxe
import isLog
import org.bukkit.GameMode
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

        val blocks = mutableSetOf<Block>().apply {
            val unCheckedBlocks = mutableSetOf(event.block)
            val checkedBlocks = this
            val blockType = event.block.type

            while (unCheckedBlocks.isNotEmpty()) {
                val block = unCheckedBlocks.first().apply {
                    unCheckedBlocks.remove(this)
                    checkedBlocks.add(this)
                }

                unCheckedBlocks.addAll(getRelativeBlocks(block, blockType)
                        .filterNot { checkedBlocks.contains(it) })
            }
        }.toList()

        val tool = event.player.inventory.itemInMainHand

        var isToolBreak = tool.durability + blocks.size >= tool.type.maxDurability

        var overBlockAmount = if (isToolBreak) tool.durability + blocks.size - tool.type.maxDurability else 0

        var finalBlockAmount = blocks.size - overBlockAmount

        if (event.player.gameMode == GameMode.CREATIVE) {
            if (!configs.onCreativeDurabilityReduce) {
                isToolBreak = false
                overBlockAmount = 0
                finalBlockAmount = 0
            }
        }

        val finalBlocks = blocks.toMutableList().apply {
            if (isToolBreak) kotlin.repeat(overBlockAmount) { removeAt(lastIndex) }
        }

        callEvent(BreakBlocksEvent(finalBlocks, tool)).breakBlocks()

        var message = ""

        if (isToolBreak) {
            callEvent(BreakToolEvent(event.player)).breakTool()
            if (configs.onToolBrokenMsg) message = "道具が壊れました, ブロック数: ${finalBlocks.size}"
        } else {
            callEvent(ChangeToolDurabilityEvent(tool, finalBlockAmount)).changeToolDurability()
            if (configs.onToolDurabilityMsg) {
                message = "耐久値: ${tool.type.maxDurability - tool.durability}, " +
                        "ブロック数: ${finalBlocks.size}"
            }
        }

        callEvent(TreeKillMessageEvent(message, event.player)).sendMessage()
    }
}