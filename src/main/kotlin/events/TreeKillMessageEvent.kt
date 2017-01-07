package events

import org.bukkit.entity.Player

/**
 * Created by masahiro on 2017/01/08.
 */
class TreeKillMessageEvent(

        val message: String,
        val player: Player

) : BaseCancellableEvent() {

    fun sendMessage() : TreeKillMessageEvent {
        return this.apply {
            if (isNotCancelled.and(message.isNotBlank())) {
                player.sendMessage(message)
            }
        }
    }
}