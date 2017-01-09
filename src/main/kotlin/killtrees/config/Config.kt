package killtrees.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import killtrees.KillTrees
import killtrees.isCreativeMode
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
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