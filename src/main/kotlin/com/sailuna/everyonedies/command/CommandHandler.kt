package com.sailuna.everyonedies.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.tree.LiteralCommandNode
import com.sailuna.everyonedies.EveryoneDies
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UnstableApiUsage")
object CommandHandler {
	fun createCommand(plugin: JavaPlugin): LiteralCommandNode<CommandSourceStack> {
		val command = Commands.literal(plugin.name).requires { source -> source.sender.isOp }

		// true: 有効, false: 無効 に変更します
		command.then(Commands.argument("enabled", BoolArgumentType.bool())
			.executes(::handleToggleFeature)
		)

		return command.build()
	}

	private fun handleToggleFeature(context: CommandContext<CommandSourceStack>): Int {
		val enabled = BoolArgumentType.getBool(context, "enabled")
		val sender = context.source.sender

		// すでに有効/無効になっている場合
		if (EveryoneDies.isEnabled() == enabled) {
			val message = Component.text("すでに", NamedTextColor.WHITE).appendSpace()
				.append(if (enabled) Component.text("有効", NamedTextColor.GREEN) else Component.text("無効", NamedTextColor.RED))
				.append(Component.text("になっています", NamedTextColor.WHITE))

			sender.sendMessage(message)
			throw SimpleCommandExceptionType(MessageComponentSerializer.message().serialize(message)).create()
		}

		// 設定を変更
		EveryoneDies.setEnabled(enabled)
		sender.sendMessage {
			(if (enabled) Component.text("有効", NamedTextColor.GREEN) else Component.text("無効", NamedTextColor.RED)).appendSpace().append(Component.text("に変更しました", NamedTextColor.WHITE))
		}
		return Command.SINGLE_SUCCESS
	}
}
