import config.configs
import config.loadConfigs
import listener.BlockBreakEventListener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by masahiro on 2016/12/28.
 */

class KillTrees : JavaPlugin() {

    override fun onEnable() {
        logger.info("KillTrees onEnable!!")

        server.pluginManager.registerEvents(BlockBreakEventListener(this), this)
        configs = loadConfigs(File(dataFolder, "config.yml"))
    }

    override fun onDisable() {
        logger.info("KillTrees onDisable!!")
    }
}