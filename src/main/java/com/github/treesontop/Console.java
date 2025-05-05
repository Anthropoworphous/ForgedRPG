package com.github.treesontop;

import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Console {
    private static final TextTerminal<?> terminal = TextIoFactory.getTextIO().getTextTerminal();

    public static void link() {
        Logger.getGlobal().addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                terminal.printf("[%s - %s]: %s%n",
                    record.getLevel().getName(),
                    Arrays.stream(record.getLoggerName().split("\\.")).toList().getLast(),
                    record.getMessage());

                var err = record.getThrown();

                if (err != null) {
                    terminal.printf(Arrays.stream(err.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("%n")));
                }
            }

            @Override
            public void flush() {}
            @Override
            public void close() throws SecurityException {}
        });
    }
}
