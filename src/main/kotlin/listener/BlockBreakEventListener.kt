package listener

import com.sun.org.apache.xpath.internal.operations.Bool
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * Created by masahiro on 2016/12/29.
 */

class BlockBreakEventListener(plugin: JavaPlugin) : Listener {
    var plugin = plugin
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
        if (!isLog(event.block)) return
        if (!haveAxe(event.player.itemInHand)) return

        var player = event.player
        var itemStack = player.itemInHand
        var unCheckedBlocks = mutableListOf(event.block)
        var checkedBlocks = arrayListOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {

            var block = unCheckedBlocks.removeAt(0)

            checkedBlocks.add(block)

            blockFaces.forEach {
                var relativeBlock = block.getRelative(it)

                blockFaces.forEach {
                    var nextRelativeBlock = relativeBlock.getRelative(it)

                    if (!checkedBlocks.contains(nextRelativeBlock)) {
                        if (!unCheckedBlocks.contains(nextRelativeBlock)) {
                            if (isLog(nextRelativeBlock)) {
                                unCheckedBlocks.add(nextRelativeBlock)
                            }
                        }
                    }
                }
            }
        }

//        checkedBlocks.remove(event.block)

        var durability = itemStack.durability
        var maxDurability = itemStack.type.maxDurability
        var expectedDurability = itemStack.durability + checkedBlocks.size
        var itemBreakingFrag = false

        if (expectedDurability >= maxDurability) {
            itemBreakingFrag = true
            kotlin.repeat(expectedDurability - maxDurability, {
                checkedBlocks.removeAt(checkedBlocks.lastIndex)
            })
        }

        checkedBlocks.forEach { it.breakNaturally(itemStack) }

        itemStack.durability = durability.plus(checkedBlocks.size).toShort()

        if (itemBreakingFrag) {
            player.world.playSound(player.location, Sound.ITEM_SHIELD_BREAK, 1f, 1f)
            player.itemInHand = ItemStack(Material.AIR)
            player.sendMessage("アイテムが壊れた")
        } else {
            player.sendMessage("アイテムの耐久値: ${maxDurability - durability}" +
                    " -> ${maxDurability - itemStack.durability}")
        }

        player.sendMessage("壊したブロック数: ${checkedBlocks.size}")
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