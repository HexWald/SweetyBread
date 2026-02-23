package hex.wald.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class ComposterListener implements Listener {

    private final JavaPlugin plugin;

    public ComposterListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onComposterTake(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.COMPOSTER) return;

        if (!(block.getBlockData() instanceof Levelled levelled)) return;
        if (levelled.getLevel() != levelled.getMaximumLevel()) return; // не полон

        double chance = plugin.getConfig().getDouble("chance", 0.03);
        if (chance <= 0) return;

        if (ThreadLocalRandom.current().nextDouble() > chance) return;

        ItemStack bread = new ItemStack(Material.BREAD, 1);
        ItemMeta meta = bread.getItemMeta();

        String name = plugin.getConfig().getString("bread-name", "Сладкий хлеб");
        meta.displayName(Component.text(name));

        bread.setItemMeta(meta);

        Location dropLoc = block.getLocation().add(0.5, 1.0, 0.5);
        Item dropped = block.getWorld().dropItemNaturally(dropLoc, bread);

        dropped.setVelocity(dropped.getVelocity().multiply(0.3));
    }


}