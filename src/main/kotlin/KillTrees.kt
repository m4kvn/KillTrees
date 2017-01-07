import config.configs
import config.loadConfigsFromJson
import config.plugin
import listener.BlockBreakEventListener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by masahiro on 2016/12/28.
 */

class KillTrees : JavaPlugin() {

    override fun onEnable() {
        logger.info("KillTrees onEnable!!")

        plugin = this

        server.pluginManager.registerEvents(BlockBreakEventListener(), this)

        loadConfigsFromJson(File(dataFolder, "config.json"))
    }

    override fun onDisable() {
        logger.info("KillTrees onDisable!!")
    }
}