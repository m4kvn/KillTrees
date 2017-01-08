package events

/**
 * Created by masahiro on 2017/01/08.
 */
class ChangeToolDurabilityEvent(event: TreeKillEvent) : BaseCancellableEvent() {

    val tool = event.tool
    val player = event.player

    var damage = event.blocks.size
}