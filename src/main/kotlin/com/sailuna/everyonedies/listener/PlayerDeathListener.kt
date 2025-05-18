package com.sailuna.everyonedies.listener

import com.sailuna.everyonedies.EveryoneDies
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener: Listener {

	@EventHandler
	fun onPlayerDeath(event: PlayerDeathEvent) {
		if (!EveryoneDies.isEnabled()) return

		val deathPlayer = event.player

		Bukkit.getOnlinePlayers()
			.filter { player ->
				// 死んだプレイヤーを除くゲームモードがサバイバルのプレイヤーをフィルタリング
				player != deathPlayer && player.gameMode == GameMode.SURVIVAL
			}
			.forEach { player ->
				// プレイヤーを死亡させる
				player.health = 0.0
			}
	}
}
