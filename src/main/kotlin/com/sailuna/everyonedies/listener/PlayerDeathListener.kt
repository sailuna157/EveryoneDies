package com.sailuna.everyonedies.listener

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener: Listener {
	@EventHandler
	fun onPlayerDeath(event: PlayerDeathEvent) {
		val deathPlayer = event.player

		val alivePlayers = Bukkit.getOnlinePlayers().filter { player ->
			// 死んだプレイヤーを除くゲームモードがサバイバルのプレイヤーをフィルタリング
			player != deathPlayer && player.gameMode == GameMode.SURVIVAL
		}

		if (alivePlayers.none()) return

		alivePlayers.forEach { player ->
			// プレイヤーを死亡させる
			player.health = 0.0
		}
	}
}
