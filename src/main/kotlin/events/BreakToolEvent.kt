package events

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemStack

/**
 * Created by masahiro on 2017/01/08.
 */
class BreakToolEvent(event: TreeKillEvent) : BaseCancellableEvent() {

    val isToolBreak = event.isToolBreak
    val player = event.player

    var sound = Sound.ITEM_SHIELD_BREAK
    var soundVolume = 1f
    var soundSpeed = 1f
    var changeItem = ItemStack(Material.AIR)
}