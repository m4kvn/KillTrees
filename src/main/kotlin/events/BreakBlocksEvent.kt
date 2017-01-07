package events

import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

/**
 * Created by masahiro on 2017/01/08.
 */
class BreakBlocksEvent(

        val blocks: List<Block>,
        val tool: ItemStack

) : BaseCancellableEvent() {

    fun breakBlocks() : BreakBlocksEvent {
        return this.apply {
            if (isNotCancelled) {
                blocks.forEach { it.breakNaturally(tool) }
            }
        }
    }
}