package killtrees.events

/**
 * Created by masahiro on 2017/01/08.
 */
class CalcBreakBlock(val event: TreeKillEvent) : BaseCancellableEvent() {

    val block = event.block
    val player = event.player
}