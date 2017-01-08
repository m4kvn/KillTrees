package events

/**
 * Created by masahiro on 2017/01/08.
 */
class BreakBlocksEvent(event: TreeKillEvent) : BaseCancellableEvent() {

    val blocks = event.blocks
    val tool = event.tool
    val player = event.player
}