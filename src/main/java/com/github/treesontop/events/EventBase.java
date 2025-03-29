package com.github.treesontop.events;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public abstract class EventBase<E extends Event> implements EventListener<E> {
    public abstract Result execute(E event);

    @Override
    @NotNull
    public Result run(@NotNull E event) {
        return execute(event);
    }

    public void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(this);
    }

    @Override
    public @NotNull Class<E> eventType() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
