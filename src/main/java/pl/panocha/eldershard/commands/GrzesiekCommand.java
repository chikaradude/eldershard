package pl.panocha.eldershard.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GrzesiekCommand implements CommandExecutor {

    private Merchant Grzesiek() {
        Merchant merchant = Bukkit.createMerchant();
        List<MerchantRecipe> recipes = new ArrayList<>();

        // TRADE 1: 10 emeraldów za diament
        MerchantRecipe trade1 = new MerchantRecipe(new ItemStack(Material.DIAMOND), 9999);
        trade1.addIngredient(new ItemStack(Material.EMERALD, 10));
        recipes.add(trade1);

        // TRADE 2: 5 emeraldów za golden apple
        MerchantRecipe trade2 = new MerchantRecipe(new ItemStack(Material.GOLDEN_APPLE), 9999);
        trade2.addIngredient(new ItemStack(Material.EMERALD, 5));
        recipes.add(trade2);

        // TRADE 3: 32 stone za 1 emerald
        MerchantRecipe trade3 = new MerchantRecipe(new ItemStack(Material.EMERALD), 9999);
        trade3.addIngredient(new ItemStack(Material.STONE, 32));
        recipes.add(trade3);

        merchant.setRecipes(recipes);

        return merchant;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (args.length == 0) return true;

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) return true;
        if (!player.hasPermission("eldershard.dev")) return true;

        player.openMerchant(Grzesiek(), false);

        return true;
    }
}