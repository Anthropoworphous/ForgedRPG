package com.github.treesontop.events;

import net.minestom.server.event.Event;
import net.minestom.server.event.GlobalEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.*;

class EventBaseTest {
    private EventBase<Event> eventBase;
    private GlobalEventHandler eventHandler;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        eventBase = (EventBase<Event>) mock(EventBase.class, CALLS_REAL_METHODS);
        eventHandler = mock(GlobalEventHandler.class);
    }

    @Test
    void testRun() {
        Event event = mock(Event.class);
        when(eventBase.execute(event)).thenReturn(EventBase.Result.SUCCESS);

        EventBase.Result result = eventBase.run(event);

        assertEquals(EventBase.Result.SUCCESS, result);
        verify(eventBase).execute(event);
    }

    @Test
    void testRegister() {
        eventBase.register(eventHandler);

        verify(eventHandler).addListener(eventBase);
    }
}
