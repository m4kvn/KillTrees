package killtrees.events

class ChangeToolDurabilityEvent(val event: TreeKillEvent) : BaseCancellableEvent() {

    val player = event.player
    val tool = event.tool

    var damage = event.blocks.size
}