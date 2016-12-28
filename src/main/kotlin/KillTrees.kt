import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by masahiro on 2016/12/28.
 */

class KillTrees : JavaPlugin() {

    override fun onEnable() {
        this.logger.severe("KillTrees onEnable!!")
    }

    override fun onDisable() {
        this.logger.severe("KillTrees onDisable!!")
    }
}