package com.github.treesontop.events;

import com.github.treesontop.Main;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;


@SuppressWarnings("unchecked")
public abstract class EventBase<E extends Event> implements EventListener<E> {


    /**
     * Executes the event.
     *
     * @param event the event to execute
     * @return the result of the event execution
     */
    public abstract Result execute(E event);

    @Override
    @NotNull
    public Result run(@NotNull E event) {
        try {
            return execute(event);
        } catch (Exception e) {
            Main.logger.log(Level.SEVERE, "Error executing event: " + event.getClass().getName(), e);
            return Result.EXCEPTION;
        }
    }

    /**
     * Registers the event with the global event handler.
     *
     * @param eventHandler the global event handler
     */
    public void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(this);
    }

    @Override
    public @NotNull Class<E> eventType() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
