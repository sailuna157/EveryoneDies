package com.sailuna.everyonedies

import com.sailuna.everyonedies.command.CommandHandler
import com.sailuna.everyonedies.listener.PlayerDeathListener
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class EveryoneDies: JavaPlugin() {
	@Suppress("UnstableApiUsage")
	override fun onEnable() {
		// コマンドを登録
		lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
			commands.registrar().register(CommandHandler.createCommand(this))
		}
		// イベントリスナーを登録
		server.pluginManager.registerEvents(PlayerDeathListener, this)
	}

	companion object {
		private var enabled = true

		fun isEnabled(): Boolean = enabled
		fun setEnabled(enabled: Boolean? = null) {
			this.enabled = enabled?: !isEnabled()
		}
	}
}
