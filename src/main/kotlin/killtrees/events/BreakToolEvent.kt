package killtrees.events

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemStack

class BreakToolEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val isToolBreak = event.isToolBreak
    val player = event.player

    var sound = Sound.ITEM_SHIELD_BREAK
    var soundVolume = 1f
    var soundSpeed = 1f
    var changeItem = ItemStack(Material.AIR)
}