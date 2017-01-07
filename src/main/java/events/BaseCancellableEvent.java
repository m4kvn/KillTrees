package events;

import org.bukkit.event.Cancellable;

/**
 * Created by masahiro on 2017/01/08.
 */
public abstract class BaseCancellableEvent extends BaseEvent implements Cancellable {

    private boolean isCancelled = false;

    @Override
    public boolean isCancelled() { return isCancelled; }

    @Override
    public void setCancelled(boolean cancel) { isCancelled = cancel; }

    public boolean isNotCancelled() { return !isCancelled; }
}
