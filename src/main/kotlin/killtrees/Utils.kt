package killtrees

import killtrees.config.getRelativeBlocks
import killtrees.config.isNotMax
import killtrees.utils.isSameDamage
import killtrees.utils.isSameType
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun Listener.register(plugin: JavaPlugin) = plugin.server.pluginManager.registerEvents(this, plugin)

fun <T : Event> T.call(plugin: JavaPlugin) : T = this.apply { plugin.server.pluginManager.callEvent(this) }

fun Block.getSameRelativeBlocks() : List<Block> =
        this.getRelativeBlocks()
                .filter { isSameType(it) }
                .filter { isSameDamage(it) }

fun Block.getRangeTo(player: Player) : Double =
        Math.sqrt(((x - player.location.x) * (x - player.location.x))
                + ((y - player.location.y) * (y - player.location.y))
                + ((z - player.location.z) * (z - player.location.z)))

fun Block.calcBreakBlocks(player: Player) : List<Block> {
    val unCheckedBlocks = mutableSetOf(this)
    val checkedBlocks = mutableSetOf<Block>()

    while (unCheckedBlocks.isNotEmpty() && checkedBlocks.isNotMax()) {
        val block = unCheckedBlocks.first().apply {
            unCheckedBlocks.remove(this)
            checkedBlocks.add(this)
        }
        unCheckedBlocks.addAll(block.getSameRelativeBlocks().filterNot { checkedBlocks.contains(it) })
    }
    return checkedBlocks.toList().sortedBy { it.getRangeTo(player) }
}