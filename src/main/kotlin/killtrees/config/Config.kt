package killtrees.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import killtrees.utils.isCreativeMode
import killtrees.utils.isWood
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Wood
import java.io.File

var configs: Configs = Configs()

fun File.loadConfigs() : Configs {
    if (!parentFile.exists()) { parentFile.mkdirs() }
    if (!exists()) { return createNewConfigFile() }
    else return Gson().fromJson(readText(), Configs().javaClass).write(this)
}

fun File.createNewConfigFile() : Configs = Configs().apply { createNewFile() }.write(this)

fun Configs.write(file: File) : Configs = this.apply {
    file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(this))
}

fun Collection<Block>.isNotMax() : Boolean = size < configs.maxBlockAmount

fun Player.canReduceDurability() : Boolean = !isCreativeMode() || configs.onCreativeDurabilityReduce

fun Block.getRelativeBlocks() : List<Block> = mutableListOf<Block>().apply {
    val a = configs.rangeBreakBlock * -1
    val b = configs.rangeBreakBlock
    for (x in a..b) for (y in a..b) for (z in a..b) add(getRelative(x, y, z))
}

fun Block.isValidType() : Boolean {
    if (isWood()) return configs.breakWoodTypes.contains((state.data as Wood).species.name)
    else          return configs.breakOtherTypes.contains(type.name)
}

fun Block.isInValidType() : Boolean = !isValidType()

fun ItemStack.isValidType() : Boolean =
        configs.validToolTypes.contains(type.name)
                || configs.validOtherToolTypes.contains(type.name)

fun ItemStack.isInValidType() : Boolean = !isValidType()