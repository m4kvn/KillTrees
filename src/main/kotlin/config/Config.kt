package config

import org.yaml.snakeyaml.Yaml
import java.io.File

data class Configs(
        var reduce_durability_in_case_of_creative: Boolean = false,
        var display_a_message_when_the_tool_is_broken: Boolean = true,
        var display_a_message_when_the_durability_value_of_the_tool_decreases: Boolean = true
)

fun loadConfigs(file: File) : Configs {

    if (file.parentFile.exists().not()) {
        file.parentFile.mkdirs()
    }

    if (file.exists().not()) {
        val conf = Configs()
        file.createNewFile()
        file.writeText(Yaml().dumpAsMap(conf))
        return conf
    } else {
        return Yaml().loadAs(file.readText(), Configs::class.java)
    }
}

var configs = Configs()

fun onReduceCreativeDurability() = configs.reduce_durability_in_case_of_creative
fun onMessageToolBroken() = configs.display_a_message_when_the_tool_is_broken
fun onMessageToolDurability() = configs.display_a_message_when_the_durability_value_of_the_tool_decreases

fun main(args: Array<String>) {
    println(loadConfigs(File("config.yml")))
}