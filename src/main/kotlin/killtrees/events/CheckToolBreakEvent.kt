package killtrees.events

class CheckToolBreakEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val tool = event.tool
    val blocks = event.blocks
    val player = event.player
}