package com.github.treesontop.commands.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CMDBuilder {
    byte counts = 0;
    final List<CommandExecutor> executor = new ArrayList<>();
    final List<List<Supplier<String>>> annotations = new ArrayList<>();
    final List<Argument<?>[]> argList = new ArrayList<>();

    @CanIgnoreReturnValue
    public CMDBuilder implement(
            CommandExecutor exe,
            Consumer<ArgumentAnnotater> annotation,
            Argument<?>... args
    ) {
        counts++;
        executor.add(exe);

        ArgumentAnnotater annotater = new ArgumentAnnotater(args.length);
        if (annotation != null) annotation.accept(annotater);

        annotations.add(annotater.annotations);
        argList.add(args);

        return this;
    }

    @CanIgnoreReturnValue
    public CMDBuilder implement(
            CommandExecutor ctx,
            Argument<?>... args
    ) {
        return implement(ctx, null, args);
    }

    public List<TextComponent> generateArgsDescription(String cmdName, int index) {
        ArrayList<TextComponent> l = new ArrayList<>();
        l.add(Component.text("%s usage:".formatted(cmdName)).color(NamedTextColor.GOLD));
        for (int i = 0; i < argList.getFirst().length; i++) {
            var provided = "None was provided";
            var list = annotations.get(index);
            if (list != null && list.size() > i) {
                String s = list.get(i).get();
                if (s != null) provided = s;
            }

            l.add(Component.text("    <%s>: ".formatted(argList.get(index)[i].getId()), NamedTextColor.GOLD)
                    .append(Component.text(provided, NamedTextColor.GRAY))
            );
        }
        return l;
    }

    public static class ArgumentAnnotater {
        public final List<Supplier<String>> annotations;

        public ArgumentAnnotater(int counts) {
            annotations = new ArrayList<>(counts);
        }

        @CanIgnoreReturnValue
        public ArgumentAnnotater annotate(int argIndex, Supplier<String> annotation) {
            while (annotations.size() <= argIndex) annotations.add(null);
            annotations.set(argIndex, annotation);
            return this;
        }

        @CanIgnoreReturnValue
        public ArgumentAnnotater annotate(int argIndex, String annotation) {
            while (annotations.size() <= argIndex) annotations.add(null);
            annotations.set(argIndex, () -> annotation);
            return this;
        }

        @CanIgnoreReturnValue
        public ArgumentAnnotater skipAnnotation(int argIndex) {
            while (annotations.size() <= argIndex) annotations.add(null);
            annotations.set(argIndex, () -> null);
            return this;
        }
    }
}
