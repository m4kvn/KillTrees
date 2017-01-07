package config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by masahiro on 2017/01/07.
 */
var plugin: JavaPlugin? = null
var configs: Configs = Configs()

fun loadConfigsFromJson(file: File) {

    if (!file.parentFile.exists()) {
        file.parentFile.mkdirs()
    }

    if (!file.exists()) {
        file.createNewFile()
        file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(configs))
    } else {
        configs = Gson().fromJson(file.readText(), configs.javaClass)
        file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(configs))
    }
}

fun main(args: Array<String>) {
}