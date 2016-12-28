import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by masahiro on 2016/12/28.
 */

class KillTrees : JavaPlugin() {

    override fun onEnable() {
        logger.info("KillTrees onEnable!!")
    }

    override fun onDisable() {
        logger.info("KillTrees onDisable!!")
    }
}