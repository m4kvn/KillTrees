import config.configs
import config.plugin
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event

/**
 * Created by masahiro on 2017/01/08.
 */

fun haveAxe(player: Player): Boolean {
    when (player.inventory.itemInMainHand.type) {
        Material.DIAMOND_AXE -> return true
        Material.GOLD_AXE    -> return true
        Material.IRON_AXE    -> return true
        Material.STONE_AXE   -> return true
        Material.WOOD_AXE    -> return true
        else                 -> return false
    }
}

fun isLog(block: Block): Boolean {
    when (block.type) {
        Material.LOG   -> return true
        Material.LOG_2 -> return true
        else           -> return false
    }
}

fun <T : Event> callEvent(event: T) : T {
    return event.apply { plugin?.server?.pluginManager?.callEvent(event) }
}

fun getRelativeBlocks(block: Block, type: Material) : List<Block> {
    val a = configs.rangeBreakBlock * -1
    val b = configs.rangeBreakBlock
    return mutableListOf<Block>()
            .apply { for (x in a..b) for (y in a..b) for (z in a..b) add(block.getRelative(x, y, z)) }
            .filter { it.type == type }
            .toList()
}

fun info(message: String)  = plugin?.run { this.logger.info(message) }

fun isNotMax(blocks: Collection<Block>) : Boolean = blocks.size < configs.maxBlockAmount