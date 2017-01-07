package listener

import config.configs
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

/**
 * Created by masahiro on 2016/12/29.
 */

class BlockBreakEventListener : Listener {
    var blockFaces = listOf<BlockFace>(
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.EAST
    )

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        when {
            !isLog(event.block)                             -> return
            !haveAxe(event.player.inventory.itemInMainHand) -> return
        }

        var player = event.player
        var itemStack = player.inventory.itemInMainHand
        var unCheckedBlocks = mutableListOf(event.block)
        var checkedBlocks = arrayListOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {

            var block = unCheckedBlocks.removeAt(0)

            checkedBlocks.add(block)

            blockFaces.forEach {
                var relativeBlock = block.getRelative(it)

                blockFaces.filterNot { checkedBlocks.contains(relativeBlock.getRelative(it)) }
                        .filterNot { unCheckedBlocks.contains(relativeBlock.getRelative(it)) }
                        .filter { isLog(relativeBlock.getRelative(it)) }
                        .forEach { unCheckedBlocks.add(relativeBlock.getRelative(it)) }
            }
        }

        var durability = itemStack.durability
        var maxDurability = itemStack.type.maxDurability
        var expectedDurability = durability + checkedBlocks.size
        var itemBreakingFrag = false

        if (expectedDurability >= maxDurability) {
            itemBreakingFrag = true
            kotlin.repeat(expectedDurability - maxDurability, {
                checkedBlocks.removeAt(checkedBlocks.lastIndex)
            })
        }

        checkedBlocks.forEach { it.breakNaturally(itemStack) }

        itemStack.durability = itemStack.durability.plus(checkedBlocks.size).toShort()

        if (itemBreakingFrag) {
            player.world.playSound(player.location, Sound.ITEM_SHIELD_BREAK, 1f, 1f)
            player.inventory.itemInMainHand = ItemStack(Material.AIR)
        }

        player.sendMessage(buildString {
            val oldDura = maxDurability - durability
            val newDura = maxDurability - itemStack.durability
            if (configs.onToolBrokenMsg.and(itemBreakingFrag))appendln("アイテムが壊れた")
            if (configs.onToolDurabilityMsg)
                append("耐久値: $oldDura -> $newDura, ブロック数: ${checkedBlocks.size}")
        })
    }

    fun isLog(block: Block): Boolean {
        when (block.type) {
            Material.LOG   -> return true
            Material.LOG_2 -> return true
            else           -> return false
        }
    }

    fun haveAxe(itemStack: ItemStack): Boolean {
        when (itemStack.type) {
            Material.DIAMOND_AXE -> return true
            Material.GOLD_AXE    -> return true
            Material.IRON_AXE    -> return true
            Material.STONE_AXE   -> return true
            Material.WOOD_AXE    -> return true
            else                 -> return false
        }
    }
}