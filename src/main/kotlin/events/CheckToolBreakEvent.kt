package events

/**
 * Created by masahiro on 2017/01/08.
 */
class CheckToolBreakEvent(event: TreeKillEvent) : BaseCancellableEvent() {

    val tool = event.tool
    val blocks = event.blocks
    val player = event.player
}