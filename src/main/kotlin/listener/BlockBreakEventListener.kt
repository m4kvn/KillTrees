package listener

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
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
        if (!isLog(event.block.type)) return

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
                            if (isLog(nextRelativeBlock.type)) {
                                unCheckedBlocks.add(nextRelativeBlock)
                            }
                        }
                    }
                }
            }
        }

        checkedBlocks.forEach {
            it.breakNaturally()
        }
    }

    fun isLog(type: Material): Boolean {
        when (type) {
            Material.LOG   -> return true
            Material.LOG_2 -> return true
            else -> return false
        }
    }
}