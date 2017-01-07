package config

import com.squareup.moshi.Moshi
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by masahiro on 2017/01/07.
 */
var plugin: JavaPlugin? = null
var configs: Configs = Configs()

fun loadConfigsFromJson(file: File) : Configs {

    if (!file.parentFile.exists()) {
        file.parentFile.mkdirs()
    }

    val adapter = Moshi.Builder().build().adapter(Configs::class.java)

    if (!file.exists()) {
        plugin?.getResource("config.json")?.copyTo(file.outputStream())
        file.createNewFile()
        return configs
    } else {
        return adapter.fromJson(file.readText())
    }
}