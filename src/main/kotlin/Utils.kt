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

/**
 * 自分を含めた3x3x3空間の隣接した27ブロックを取得する
 */
fun getRelativeBlocks(block: Block, type: Material) : List<Block> {
    return mutableListOf<Block>()
            .apply { for (x in -1..1) for (y in -1..1) for (z in -1..1) add(block.getRelative(x, y, z)) }
            .filter { it.type == type }
            .toList()
}

fun info(message: String)  = plugin?.run { this.logger.info(message) }