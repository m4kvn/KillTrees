package killtrees.events

class BreakBlocksEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val blocks = event.blocks
    val tool = event.tool
}