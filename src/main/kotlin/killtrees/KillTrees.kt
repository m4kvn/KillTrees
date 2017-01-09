package killtrees

import killtrees.config.*
import killtrees.listener.BlockBreakEventListener
import killtrees.listener.TreeKillEventListener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class KillTrees : JavaPlugin() {

    override fun onEnable() {
        configs = File(dataFolder, "config.json").loadConfigs()

        BlockBreakEventListener(this).register(this)
        TreeKillEventListener().register(this)
    }
}