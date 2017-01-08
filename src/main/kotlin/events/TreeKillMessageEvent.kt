package events

import config.configs
import getRemainingDurability

/**
 * Created by masahiro on 2017/01/08.
 */
class TreeKillMessageEvent(event: TreeKillEvent) : BaseCancellableEvent() {

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