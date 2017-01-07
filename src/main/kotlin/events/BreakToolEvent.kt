package events

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Created by masahiro on 2017/01/08.
 */
class BreakToolEvent(

        val player: Player

        ) : BaseCancellableEvent() {

    var sound = Sound.ITEM_SHIELD_BREAK
    var soundVolume = 1f
    var soundSpeed = 1f

    var changeItem = ItemStack(Material.AIR)

    fun breakTool() : BreakToolEvent {
        return this.apply {
            if (isNotCancelled) {
                player.world.playSound(player.location, sound, soundVolume, soundSpeed)
                player.inventory.itemInMainHand = changeItem
            }
        }
    }
}