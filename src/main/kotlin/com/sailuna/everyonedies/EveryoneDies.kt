package com.sailuna.everyonedies

import com.sailuna.everyonedies.listener.PlayerDeathListener
import org.bukkit.plugin.java.JavaPlugin

class EveryoneDies: JavaPlugin() {
	override fun onEnable() {
		server.pluginManager.registerEvents(PlayerDeathListener, this)
	}
}
