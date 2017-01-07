import config.plugin
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
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

val blockFaces = listOf<BlockFace>(
        BlockFace.SELF,
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.EAST
)

fun <T : Event> callEvent(event: T) : T {
    return event.apply { plugin?.server?.pluginManager?.callEvent(event) }
}