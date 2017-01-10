package killtrees.utils

import killtrees.config.isNotMax
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.material.Leaves
import org.bukkit.material.Wood

fun Block.isSameType(block: Block) : Boolean = type == block.type

fun Block.isWood() : Boolean = this.state.data is Wood

fun Block.isLeaves() : Boolean = this.state.data is Leaves

fun Block.isLeavesType() : Boolean = type == Material.LEAVES || type == Material.LEAVES_2

fun Block.isLogType() : Boolean = type == Material.LOG || type == Material.LOG_2

fun Block.getRelatives(range: Int) : List<Block> = mutableListOf<Block>().apply {
    for (x in -range..range) for (y in -range..range)
            for (z in -range..range) add(getRelative(x, y, z))
}

fun Block.getSameRelatives(range: Int) : List<Block> = getRelatives(range).filter {
    when {
        this.isLogType()    -> (this.state.data as Wood).isSameType(it)
        this.isLeavesType() -> (this.state.data as Leaves).isSameType(it)
        else                -> it.isSameType(this)
    }
}

fun Wood.isSameType(block: Block) : Boolean =
        if (block.isWood()) (block.state.data as Wood).species == species else false

fun Leaves.isSameType(block: Block) : Boolean =
        if (block.isLeaves()) (block.state.data as Leaves).species == species else false

fun Block.calcBreakBlocks(range: Int) : List<Block> {
    val unCheckedBlocks = mutableSetOf(this)
    val checkedBlocks = mutableSetOf<Block>()

    while (unCheckedBlocks.isNotEmpty() && checkedBlocks.isNotMax()) {
        val block = unCheckedBlocks.first().apply {
            unCheckedBlocks.remove(this)
            checkedBlocks.add(this)
        }
        unCheckedBlocks.addAll(block.getSameRelatives(range)
                .filterNot { checkedBlocks.contains(it) })
    }
    return checkedBlocks.toList()
}