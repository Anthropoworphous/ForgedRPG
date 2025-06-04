package com.github.treesontop.commands;

import com.github.treesontop.commands.util.UserCMDBase;
import com.github.treesontop.commands.util.UserCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentItemStack;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.item.ItemStack;

@RegisterCommand("give")
public class GiveCommand extends UserCMDBase {
    @Override
    protected void build(UserCMDBuilder builder) {
        ArgumentItemStack itemArg = new ArgumentItemStack("item");
        ArgumentInteger amountArg = new ArgumentInteger("amount");

        builder.implement((p, args) -> p.getInventory().addItemStack(args.get(itemArg)), itemArg)
                .implement((p, args) -> {
                    ItemStack item = args.get(itemArg).withAmount(args.get(amountArg));
                    p.getInventory().addItemStack(item);
                }, itemArg, amountArg);
    }
}
