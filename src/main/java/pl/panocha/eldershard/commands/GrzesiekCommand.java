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

        // SKUP - gracz dostaje za to emeraldy
        MerchantRecipe trade1 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade1.addIngredient(new ItemStack(Material.COAL, 48)); //to dajesz wiesniakowi
        recipes.add(trade1);
        MerchantRecipe trade2 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade2.addIngredient(new ItemStack(Material.IRON_INGOT, 12)); //to dajesz wiesniakowi
        recipes.add(trade2);
        MerchantRecipe trade4 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade4.addIngredient(new ItemStack(Material.GOLD_INGOT, 24)); //to dajesz wiesniakowi
        recipes.add(trade4);
        MerchantRecipe trade3 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade3.addIngredient(new ItemStack(Material.GLASS, 16)); //to dajesz wiesniakowi
        recipes.add(trade3);
        MerchantRecipe trade5 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade5.addIngredient(new ItemStack(Material.WHITE_WOOL, 24)); //to dajesz wiesniakowi
        recipes.add(trade5);

        //SPRZEDAZ - wiesniak sprzedaje to graczom
        MerchantRecipe trade6 = new MerchantRecipe(new ItemStack(Material.BRICK, 64), 32); //to zyskujesz
        trade6.addIngredient(new ItemStack(Material.EMERALD, 2)); //to dajesz wiesniakowi
        recipes.add(trade6);
        MerchantRecipe trade7 = new MerchantRecipe(new ItemStack(Material.GLASS, 8), 32); //to zyskujesz
        trade7.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade7);
        MerchantRecipe trade8 = new MerchantRecipe(new ItemStack(Material.LANTERN, 2), 32); //to zyskujesz
        trade8.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade8);
        MerchantRecipe trade9 = new MerchantRecipe(new ItemStack(Material.CAMPFIRE, 2), 32); //to zyskujesz
        trade9.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade9);
        MerchantRecipe trade10 = new MerchantRecipe(new ItemStack(Material.EXPERIENCE_BOTTLE, 8), 32); //to zyskujesz
        trade10.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade10);

        //NOWY SET SKUP
        MerchantRecipe trade11 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade11.addIngredient(new ItemStack(Material.COBBLESTONE, 64)); //to dajesz wiesniakowi
        recipes.add(trade11);
        MerchantRecipe trade12 = new MerchantRecipe(new ItemStack(Material.EMERALD), 4); //to zyskujesz
        trade12.addIngredient(new ItemStack(Material.CLOCK, 1)); //to dajesz wiesniakowi
        recipes.add(trade12);

        //NOWY SET SPRZEDAŻ
        MerchantRecipe trade13 = new MerchantRecipe(new ItemStack(Material.STONE, 48), 32); //to zyskujesz
        trade13.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade13);
        MerchantRecipe trade14 = new MerchantRecipe(new ItemStack(Material.TERRACOTTA, 32), 32); //to zyskujesz
        trade14.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade14);

        //NOWY SET SKUP 2
        MerchantRecipe trade15 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade15.addIngredient(new ItemStack(Material.DIRT, 48)); //to dajesz wiesniakowi
        recipes.add(trade15);
        MerchantRecipe trade16 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade16.addIngredient(new ItemStack(Material.DEEPSLATE, 48)); //to dajesz wiesniakowi
        recipes.add(trade16);
        MerchantRecipe trade19 = new MerchantRecipe(new ItemStack(Material.EMERALD), 32); //to zyskujesz
        trade19.addIngredient(new ItemStack(Material.COBBLED_DEEPSLATE, 48)); //to dajesz wiesniakowi
        recipes.add(trade19);

        //NOWY SET SPRZEDAŻ 2
        MerchantRecipe trade17 = new MerchantRecipe(new ItemStack(Material.PRISMARINE_SHARD, 48), 32); //to zyskujesz
        trade17.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade17);
        MerchantRecipe trade18 = new MerchantRecipe(new ItemStack(Material.SPONGE, 1), 12); //to zyskujesz
        trade18.addIngredient(new ItemStack(Material.EMERALD, 1)); //to dajesz wiesniakowi
        recipes.add(trade18);

        merchant.setRecipes(recipes);

        return merchant;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (args.length == 0) return true;

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) return true;

        if (!sender.hasPermission("eldershard.dev")) return true;

        player.openMerchant(Grzesiek(), false);

        return true;
    }
}