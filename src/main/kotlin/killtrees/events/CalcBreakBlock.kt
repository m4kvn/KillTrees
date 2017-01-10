package killtrees.events

class CalcBreakBlock(val event: TreeKillEvent) : BaseCancellableEvent() {

    val block = event.block
    val player = event.player
}