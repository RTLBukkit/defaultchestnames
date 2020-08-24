package com.taamc.DefaultChestNames;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;
import org.bukkit.Nameable;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

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

	@EventHandler(priority = EventPriority.HIGH)
	public void onChestPlace(BlockPlaceEvent e) {
		final BlockState block = e.getBlock().getState();
		final boolean isNameable = block instanceof Nameable;

		if (isNameable) {
			if(!isNamed(block)) {
				nameChest(block, markerColor + e.getPlayer().getName());
			}
		}
	}
}
