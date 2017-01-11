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

fun Block.isValidType() : Boolean = when {
    isWood() -> configs.breakWoodTypes.contains((state.data as Wood).species.name)
    else     -> configs.breakBlockTypes.contains(type.name)
}

fun Block.isInValidType() : Boolean = !isValidType()

fun ItemStack.isValidType() : Boolean = when {
    configs.validToolTypes.contains(type.name)      -> true
    configs.validOtherToolTypes.contains(type.name) -> true
    else -> false
}

fun ItemStack.isInValidType() : Boolean = !isValidType()