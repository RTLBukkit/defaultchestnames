package com.taamc.DefaultChestNames;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultChestNames extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	final static List<Material> types = Arrays.asList(Material.CHEST, Material.DISPENSER, Material.TRAPPED_CHEST, Material.FURNACE,
			Material.DROPPER, Material.ENCHANTMENT_TABLE, Material.HOPPER, Material.BREWING_STAND);

	@EventHandler(priority = EventPriority.HIGH)
	public void onChestPlace(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;
		final Block block = e.getBlock();
		final Material mat = block.getType();
		final boolean isNameable = types.contains(mat);
		final Player player = e.getPlayer();
		if (isNameable) {
			final ItemStack item = e.getItemInHand();
			final ItemMeta meta;
			meta = item.getItemMeta();
			if (!meta.hasDisplayName()) {
				final String oldName = meta.getDisplayName();
				meta.setDisplayName(e.getPlayer().getName());
				item.setItemMeta(meta);
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						final ItemStack item2 = player.getItemInHand();
						final ItemMeta meta2 = item2.getItemMeta();
						if(meta2!=null){
							meta2.setDisplayName(oldName);
							item2.setItemMeta(meta2);
						}
					}
				}, 1);
			}

		}
	}
}
