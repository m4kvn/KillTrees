package killtrees.events

import killtrees.config.configs
import killtrees.utils.getRemainingDurability

class TreeKillMessageEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val player = event.player
    val tool = event.tool
    val blocks = event.blocks

    var message = buildString {
        if (configs.onToolDurabilityMsg) {
            append("耐久値: ${tool.getRemainingDurability()}, ")
            append("ブロック数: ${blocks.size}")
        }
    }
}