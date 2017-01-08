package events

/**
 * Created by masahiro on 2017/01/08.
 */
class ReduceBreakBlockEvent(event: TreeKillEvent) : BaseCancellableEvent() {

    val blocks = event.blocks
    val tool = event.tool
    val isToolBreak = event.isToolBreak
}