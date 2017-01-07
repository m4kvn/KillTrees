package events

import org.bukkit.inventory.ItemStack

/**
 * Created by masahiro on 2017/01/08.
 */
class ChangeToolDurabilityEvent(

        val tool: ItemStack,
        val damage: Int

) : BaseCancellableEvent() {

    fun changeToolDurability() : ChangeToolDurabilityEvent {
        return this.apply {
            if (isNotCancelled) {
                tool.durability = tool.durability.plus(damage).toShort()
            }
        }
    }
}