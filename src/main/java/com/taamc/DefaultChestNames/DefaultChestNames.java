package com.taamc.DefaultChestNames;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;
import org.bukkit.Nameable;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.naming.Name;

public class DefaultChestNames extends JavaPlugin implements Listener {

	final ChatColor markerColor = ChatColor.DARK_GREEN;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void nameChest(BlockState nameableState, String name) {
		final Nameable nameable = (Nameable) nameableState;
		nameable.setCustomName(name);
		nameableState.update();
	}

	public boolean isNamed(BlockState nameableState) {
		final Nameable nameable = (Nameable) nameableState;
		String customName = nameable.getCustomName();
		if(customName == null) customName = "";
		if(customName.contains(markerColor.toString())) return false;
		return !Strings.isNullOrEmpty(customName);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChestPlace(BlockPlaceEvent e) {
		final BlockState block = e.getBlock().getState();
		if (isNameable(e)) {
			if(!isNamed(block)) {
				nameChest(block, markerColor + e.getPlayer().getName());
			}
		}
	}

	boolean isNameable(BlockEvent e) {
		final BlockState block = e.getBlock().getState();
		return block instanceof Nameable;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChestDrop(BlockDropItemEvent e) {
		if(!(e.getBlockState() instanceof Nameable)) return;
		for (Item item : e.getItems()) {
			final ItemStack stack = item.getItemStack();
			final ItemMeta itemMeta = stack.getItemMeta();
			final String name = itemMeta != null ? itemMeta.getDisplayName() : "";
			if(!name.contains(markerColor.toString())) return;
			assert itemMeta != null;
			itemMeta.setDisplayName(null);
			stack.setItemMeta(itemMeta);
			item.setItemStack(stack);
		}
	}
}
