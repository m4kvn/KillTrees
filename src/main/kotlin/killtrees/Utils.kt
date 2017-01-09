package killtrees

import killtrees.config.configs
import killtrees.config.isNotMax
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

fun Player.haveAxe(): Boolean {
    when (itemInMainHand().type) {
        Material.DIAMOND_AXE -> return true
        Material.GOLD_AXE    -> return true
        Material.IRON_AXE    -> return true
        Material.STONE_AXE   -> return true
        Material.WOOD_AXE    -> return true
        else                 -> return false
    }
}

fun Block.isLog(): Boolean {
    when (type) {
        Material.LOG   -> return true
        Material.LOG_2 -> return true
        else           -> return false
    }
}

fun Block.getDamage() : Short = state.data.toItemStack().durability

fun Block.isSameType(block: Block) : Boolean = type == block.type

fun Block.isSameDamage(block: Block) : Boolean = getDamage() == block.getDamage()

fun Listener.register(plugin: JavaPlugin) = plugin.server.pluginManager.registerEvents(this, plugin)

fun <T : Event> T.call(plugin: JavaPlugin) : T = this.apply { plugin.server.pluginManager.callEvent(this) }

fun ItemStack.isBreak(blocks: List<Block>) : Boolean = durability + blocks.size >= type.maxDurability

fun ItemStack.getRemainingDurability() : Int = type.maxDurability - durability

fun Player.isCreativeMode() : Boolean = gameMode == GameMode.CREATIVE

fun Player.itemInMainHand() : ItemStack = inventory.itemInMainHand

fun Block.getSameRelativeBlocks() : List<Block> {
    val a = configs.rangeBreakBlock * -1
    val b = configs.rangeBreakBlock
    return mutableListOf<Block>()
            .apply { for (x in a..b) for (y in a..b) for (z in a..b) add(getRelative(x, y, z)) }
            .filter { isSameType(it) }
            .filter { isSameDamage(it) }
            .toList()
}

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
