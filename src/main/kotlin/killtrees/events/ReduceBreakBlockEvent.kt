package killtrees.events

class ReduceBreakBlockEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val blocks = event.blocks
    val tool = event.tool
    val isToolBreak = event.isToolBreak
}