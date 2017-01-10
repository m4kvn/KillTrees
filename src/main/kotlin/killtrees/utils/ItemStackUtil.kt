package killtrees.utils

import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

fun ItemStack.isBreak(blocks: List<Block>) : Boolean = durability + blocks.size >= type.maxDurability

fun ItemStack.getRemainingDurability() : Int = type.maxDurability - durability
